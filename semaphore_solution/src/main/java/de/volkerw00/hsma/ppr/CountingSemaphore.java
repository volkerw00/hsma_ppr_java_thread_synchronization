package de.volkerw00.hsma.ppr;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class CountingSemaphore
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

    static class Semaphore
    {
        private int maxPermits;
        private int permits = 0;

        public Semaphore(int permits)
        {
            this.maxPermits = permits;
        }

        public synchronized void acquire() throws InterruptedException
        {
            while (this.permits == maxPermits) wait();
            this.permits++;
            this.notify();
        }

        public synchronized void release() throws InterruptedException
        {
            while (this.permits == 0) wait();
            this.permits--;
            this.notify();
        }
    }

    static Semaphore semaphore = new Semaphore(3);

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
