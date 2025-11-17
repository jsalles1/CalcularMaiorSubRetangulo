public class subret {

    static int bestTop = -1, bestEsq = -1, bestBaixo = -1, bestDir = -1;
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    static int get(int[][] p, int i, int j) {
        if (i < 0 || j < 0) return 0;
        
        return p[i][j];
    }

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
        int maxSum = Integer.MIN_VALUE;
        
        for (int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                if(i-1 < 0 && j-1 < 0) matrix[i][j] = matrix[i][j];
                else if(i-1 < 0) matrix[i][j] = matrix[i][j] + matrix[i][j-1];
                else if(j-1 < 0) matrix[i][j] = matrix[i][j] + matrix[i-1][j];
                else matrix[i][j] = matrix[i][j] + matrix[i-1][j] + matrix[i][j-1] - matrix[i-1][j-1];
            }
        }

        for(int top = 0; top < matrix.length; top++){
            for(int esq = 0; esq < matrix.length; esq++){
                for (int baixo = top; baixo < matrix.length; baixo++) {
                    for (int dir = esq; dir < matrix.length; dir++) {
                        int sum = 0;
                        
                        if(top - 1 < 0 && esq - 1 < 0) sum = matrix[baixo][dir];
                        else if(esq - 1 < 0) sum = matrix[baixo][dir] - matrix[top-1][dir];
                        else if(top - 1 < 0) sum = matrix[baixo][dir] - matrix[baixo][esq-1];
                        else sum = matrix[baixo][dir] - matrix[baixo][esq-1] - matrix[top-1][dir] + matrix[top-1][esq-1];
                        
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

        generateMatrix(3);

        long end = System.nanoTime(); // fim da medição
        double elapsedSeconds = (end - start) / 1_000_000_000.0;
        System.out.println("Tempo de execução: " + elapsedSeconds + " segundos");
    }
}