import java.util.Scanner;

public class PDSubRetangulo {

    static int bestTop = -1, bestEsq = -1, bestBaixo = -1, bestDir = -1;
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    public static void generateMatrix(int n){
        int[][] matrix = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrix[i][j] = (int) (Math.random() * 255) - 127;
            }
        }
        maiorSubretangulo(matrix);
    }

    
    public static int maiorSubretangulo(int[][] matrix){
    long start = System.nanoTime(); // início da medição
    int maxSum = Integer.MIN_VALUE;

    int n = matrix.length;

    int[][] original = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            original[i][j] = matrix[i][j];

    for (int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
            if(i-1 < 0 && j-1 < 0) matrix[i][j] = matrix[i][j];
            else if(i-1 < 0) matrix[i][j] = matrix[i][j] + matrix[i][j-1];
            else if(j-1 < 0) matrix[i][j] = matrix[i][j] + matrix[i-1][j];
            else matrix[i][j] = matrix[i][j] + matrix[i-1][j] + matrix[i][j-1] - matrix[i-1][j-1];
        }
    }

    for(int top = 0; top < n; top++){
        for(int esq = 0; esq < n; esq++){
            for (int baixo = top; baixo < n; baixo++) {
                for (int dir = esq; dir < n; dir++) {

                    int sum;

                    if(top - 1 < 0 && esq - 1 < 0)
                        sum = matrix[baixo][dir];
                    else if(esq - 1 < 0)
                        sum = matrix[baixo][dir] - matrix[top-1][dir];
                    else if(top - 1 < 0)
                        sum = matrix[baixo][dir] - matrix[baixo][esq-1];
                    else
                        sum = matrix[baixo][dir] - matrix[baixo][esq-1]
                        - matrix[top-1][dir] + matrix[top-1][esq-1];

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

    printMatrix(original);

    System.out.println("Soma do subretangulo maximo: " + maxSum);

    long end = System.nanoTime(); // fim da medição
    double elapsedSeconds = (end - start) / 1_000_000_000.0;
    System.out.println("Tempo de execução: " + elapsedSeconds + " segundos");

    return maxSum;
}

    
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

        return matrix;
    }
    public static void main(String[] args) {

        menu();
        
    }
}