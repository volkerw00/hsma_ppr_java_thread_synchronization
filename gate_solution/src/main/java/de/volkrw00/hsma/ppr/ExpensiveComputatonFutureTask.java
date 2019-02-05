package de.volkrw00.hsma.ppr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.IntStream;

class ExpensiveComputationFutureTask
{
    static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    static int compute()
    {
        int[] computationResults = new int[10];
        List<FutureTask<Integer>> tasks = new ArrayList<>();

        IntStream.range(0, 10).forEach(i -> {
            tasks.add(new FutureTask<>(() -> compute(i)));
        });

        tasks.forEach(executor::execute);
        IntStream.range(0, 10).forEach(i -> {
            try
            {
                computationResults[i] = tasks.get(i).get();
            }
            catch (InterruptedException | ExecutionException e)
            {
            }
        });

        return IntStream.of(computationResults).reduce(0, (l, r) -> l + r);
    }

    private static int compute(int i)
    {
        try
        {
            Thread.sleep(new Random().nextInt(5) * 1000);
        }
        catch (InterruptedException e)
        {
            return Integer.MIN_VALUE;
        }
        return i;
    }

    public static void main(String[] args)
    {
        System.out.printf("Computation result is %s. Expected was %s.", compute(), 45);
    }
}
