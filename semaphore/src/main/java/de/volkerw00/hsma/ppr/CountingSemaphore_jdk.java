package de.volkerw00.hsma.ppr;

import java.time.Instant;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class CountingSemaphore_jdk
{
    static class RestrictedResource
    {
        static AtomicInteger aInt = new AtomicInteger();

        public static void use()
        {
            if (aInt.get() < 2)
            {
                aInt.incrementAndGet();
                System.out.printf("[%s] Thread %s is using resource.%n", Instant.now(), Thread.currentThread().getId());
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e)
                {
                    return;
                }
                aInt.decrementAndGet();
            }
            else
            {
                throw new IllegalAccessError("Access is not allowed!");
            }
        }
    }

    static Semaphore semaphore = new Semaphore(2);

    static void useRestrictedResource()
    {
        try
        {
            semaphore.acquire();
            RestrictedResource.use();
            semaphore.release();
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> useRestrictedResource()).start();
        });
    }
}
