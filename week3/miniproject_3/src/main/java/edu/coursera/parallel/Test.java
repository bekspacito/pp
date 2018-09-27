package edu.coursera.parallel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args){

        /*
        List<CustomRecursiveAction2> tasks = Arrays.asList(
                new CustomRecursiveAction2(0),
                new CustomRecursiveAction2(1),
                new CustomRecursiveAction2(2),
                new CustomRecursiveAction2(3)
        );

        ForkJoinTask.invokeAll(tasks);
        */

        IntStream.range(0,4)
                 .parallel()
                 .forEach((i) -> {

                    new CustomRecursiveAction(i).compute();

                 });
    }

}
