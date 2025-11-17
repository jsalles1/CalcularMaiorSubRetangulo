import java.util.Scanner;

public class BruteForceSubRetangulo {

    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    
    //variaveis para salvar coordenadas do melhor subretangulo
    static int bestTop = -1, bestEsq = -1, bestBaixo = -1, bestDir = -1;

    //gera matriz com numeros aleatorios de -127 até 127
    public static void generateMatrix(int n){
        int[][] matrix = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrix[i][j] = (int) (Math.random() * 255) - 127;
            }
        }
        maiorSubretangulo(matrix);
        printMatrix(matrix);
    }
    
    //calcula o maior subretangulo percorrendo todas possibilidades
    public static int maiorSubretangulo(int[][] matrix){
        long start = System.nanoTime(); // início da medição de tempo de execução

        int maxSum = -127;
        
        for(int top = 0; top < matrix.length; top++){
            for(int esq = 0; esq < matrix.length; esq++){
                for (int baixo = top; baixo < matrix.length; baixo++) {
                    for (int dir = esq; dir < matrix.length; dir++) {
                        int sum = 0;
                        
                        for(int i = top; i <= baixo; i++){
                            for(int j = esq; j <= dir; j++){
                                sum += matrix[i][j];
                            }
                        }
                        
                        if(sum > maxSum){
                            maxSum = sum;
                            bestTop = top;
                            bestEsq = esq;
                            bestBaixo = baixo;
                            bestDir = dir;
                        }
                    }
                }
            }
        }
        long end = System.nanoTime(); // fim da medição
        double elapsedSeconds = (end - start) / 1_000_000_000.0;
        System.out.println("Tempo de execução: " + elapsedSeconds + " segundos");

        System.out.println("Soma do subretangulo maximo: " + maxSum);
        return maxSum;
    }
    
    //funcao para exibir matriz
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                boolean inRect =
                    i >= bestTop && i <= bestBaixo &&
                    j >= bestEsq && j <= bestDir;

                if (inRect)
                    System.out.printf(RED + "%5d" + RESET, matrix[i][j]);
                else
                    System.out.printf("%5d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void menu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite '1' para criar matriz, ou '2' para gerar automaticamente:");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.println("Digite o tamanho da matriz: ");
            int n = scanner.nextInt();
            scanner.nextLine();
            criarMatriz(n);
        } else if (choice == 2) {
            System.out.println("Digite o tamanho da matriz: ");
            int n = scanner.nextInt();
            scanner.nextLine();
            generateMatrix(n);
        } else {
            System.out.println("Opção inválida!");
            scanner.close();
            return;
        }
        scanner.close();
    }

    //funcao para criar matriz manualmente
    public static int[][] criarMatriz(int n){
        int matrix[][] = new int[n][n];
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite a matriz linha por linha:");
        System.out.println("Use espaços entre os números. Exemplo:  1 -3  4");

        for(int i = 0; i < n; i++){
            System.out.print("Linha " + (i + 1) + ": ");
            String line = sc.nextLine().trim();

            String[] parts = line.split("\\s+");

            for (int j = 0; j < n; j++) {
                    matrix[i][j] = Integer.parseInt(parts[j]);
                }
        }

        sc.close();
        maiorSubretangulo(matrix);
        printMatrix(matrix);

        return matrix;
    }

    public static void main(String[] args) {

        menu();
    }
    
}
