package edu.coursera.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

import static edu.rice.pcdp.PCDP.*;

/**
 * Wrapper class for implementing matrix multiply efficiently in parallel.
 */
public final class MatrixMultiply {

    private static int nCores = 4;

    static {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","4");
    }
    /**
     * Default constructor.
     */
    private MatrixMultiply() {
    }

    /**
     * Perform a two-dimensional matrix multiply (A x B = C) sequentially.
     *
     * @param A An input matrix with dimensions NxN
     * @param B An input matrix with dimensions NxN
     * @param C The output matrix
     * @param N Size of each dimension of the input matrices
     */
    public static void seqMatrixMultiply(final double[][] A, final double[][] B,
            final double[][] C, final int N) {
        forseq2d(0, N - 1, 0, N - 1, (i, j) -> {
            C[i][j] = 0.0;
            for (int k = 0; k < N; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        });
    }

    /**
     * Perform a two-dimensional matrix multiply (A x B = C) in parallel.
     *
     * @param A An input matrix with dimensions NxN
     * @param B An input matrix with dimensions NxN
     * @param C The output matrix
     * @param N Size of each dimension of the input matrices
     */

    //let's assume we have 4 cores
    public static void parMatrixMultiply(final double[][] A,
                                         final double[][] B,
                                         final double[][] C,
                                         final int N)
    {
        /*
         * TODO Parallelize this outermost two-dimension sequential loop to
         * achieve performance improvement.
         */


        int nGroups = getGroupAmount(N);
        /*
        int chunkSize = N*N/nGroups;
        int endInclusive = N*N - 1;

        List<CustomRecursiveAction2> tasks = new ArrayList<>();

        for (int i = 0; i <= endInclusive; i += chunkSize) {
            final int iCopy = i;

                int end = iCopy + chunkSize - 1;
                if (end > endInclusive) {
                    end = endInclusive;
                }

                tasks.add(new CustomRecursiveAction2(iCopy,end,N,A,B,C));

        }

        ForkJoinTask.invokeAll(tasks);
        */


        /*
        forallChunked(0,N*N - 1,N*N/nGroups,(i) -> {

            int row = i/N;
            int col = i%N;

            C[row][col] = 0.0;
            for (int k = 0; k < N; k++)
                C[row][col] += A[row][k] * B[k][col];

        });
        */

        forall2dChunked(0,N - 1,0,N -1,(row,col) -> {

            C[row][col] = 0.0;
            for (int k = 0; k < N; k++)
                C[row][col] += A[row][k] * B[k][col];

        });

        /*
        int nGroups = getGroupAmount(N);
        Group[] groups = new Group[nGroups];

        forall(0,nGroups - 1,(g) -> {

            if (groups[g] != null)
                return;

            if(g == 1)
                System.out.println("hello friend...");

            for (Cell cell = getFirstCell(g, N); cell != null; cell = getNextCell(g, N, cell)) {

                int i = cell.row;
                int j = cell.col;

                C[i][j] = 0.0;
                for (int k = 0; k < N; k++)
                    C[i][j] += A[i][k] * B[k][j];

            }

            groups[g] = new Group();

        });

        */

    }

    private static class CustomRecursiveAction2 extends RecursiveAction{

        private int iCopy;
        private int end;
        private int N;
        private final double[][] A;
        private final double[][] B;
        private final double[][] C;

        public CustomRecursiveAction2(int iCopy, int end, int N, double[][] a, double[][] b, double[][] c) {
            this.iCopy = iCopy;
            this.end = end;
            this.N = N;
            A = a;
            B = b;
            C = c;
        }

        @Override
        protected void compute() {

            for (int innerI = iCopy; innerI <= end; innerI++) {

                int row = innerI / N;
                int col = innerI % N;

                C[row][col] = 0.0;
                for (int k = 0; k < N; k++)
                    C[row][col] += A[row][k] * B[k][col];

            }

        }
    }

    private static class Cell{

        public int row;
        public int col;

        public Cell(int row,int col){
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }

    private static class Group{

    }

    /**N*N/nCores is a chunk size
     * nCores is an amount of cores available and amount of processes
     * cells start from 0 to N*N-1
     */
    private static Cell getFirstCell(int g,int N){

        int cell = N*N/nCores*g;

        if(cell >= N*N) return null;

        return new Cell(cell/N,cell%N);
    }

    private static Cell getNextCell(int g,int N,Cell prevCell){

        int nextCell = N*prevCell.row + prevCell.col + 1;

        if(nextCell >= N*N || nextCell >= N*N/nCores*(g+1))
            return null;

        prevCell.row = nextCell/N;
        prevCell.col = nextCell%N;

        return prevCell;

    }

    private static int getGroupAmount(int N){
        return N*N%nCores != 0 ? nCores + 1 : nCores;
    }

}

class CustomRecursiveAction extends RecursiveAction{

    private int task;

    public CustomRecursiveAction(int task){
        this.task = task;
    }

    @Override
    protected void compute() {

        for (int i = 0;i < 20;i++)
            System.out.println("taks : " + task);

    }
}
