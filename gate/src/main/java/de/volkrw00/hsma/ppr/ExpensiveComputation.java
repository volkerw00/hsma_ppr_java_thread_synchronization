package de.volkrw00.hsma.ppr;

import java.util.Random;
import java.util.stream.IntStream;

class ExpensiveComputation
{
    static int compute() throws InterruptedException
    {
        int[] computationResults = new int[10];
        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> computationResults[i] = compute(i)).start();
        });
        Thread.sleep(2000);
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

    public static void main(String[] args) throws InterruptedException
    {
        System.out.printf("Computation result is %s. Expected was %s", compute(), 45);
    }
}
