import java.util.concurrent.RecursiveTask;


public class ArraySum {

    private static double sum1;
    private static double sum2;

    public static void main(String[] args){

        double[] double1 = {1,2,3,4,5,6,7,8,9,10};
        double[] double2 = {2,2,2,2,2,2,2,2,2,2};

        seqArraySum(double1);
        parArraySum(double1);
        System.out.println();
        seqArraySum(double2);
        parArraySum(double2);

    }

    public static double seqArraySum(double[] arr){

        long startTime = System.nanoTime();
        double sum1  = 0;
        double sum2  = 0;

        for(int i = 0;i < arr.length/2; i++)
            sum1 += arr[i];

        for(int i = arr.length/2;i < arr.length;i++)
            sum2 += arr[i];

        double sum = sum1 + sum2;
        long timeInNanos = System.nanoTime() - startTime;
        printResults("seqArraySum",timeInNanos,sum);
        return sum;

    }


    private static double parArraySum(double[] arr){


        return 0;
    }

    private static void printResults(String name,long timeInNanos,double sum){
        System.out.printf(" %s completed in %8.3f milliseconds, with sum = %8.5f \n",name,timeInNanos/1e6,sum);
    }
}
