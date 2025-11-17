public class BruteForceSubRetangulo {

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
        printMatrix(matrix);
    }

    
    public static int maiorSubretangulo(int[][] matrix){
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
        System.out.println("Soma do subretangulo maximo: " + maxSum);
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
    public static void main(String[] args) {
        long start = System.nanoTime(); // início da medição

        generateMatrix(10);

        long end = System.nanoTime(); // fim da medição
        double elapsedSeconds = (end - start) / 1_000_000_000.0;
        System.out.println("Tempo de execução: " + elapsedSeconds + " segundos");
    }
    
}
