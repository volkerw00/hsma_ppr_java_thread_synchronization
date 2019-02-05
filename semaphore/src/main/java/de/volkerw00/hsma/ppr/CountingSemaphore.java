package de.volkerw00.hsma.ppr;

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
                try
                {
                    TimeUnit.SECONDS.wait(5);
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
        public Semaphore(int permits)
        {
        }

        public void acquire()
        {
        }

        public void release()
        {
        }
    }

    static Semaphore semaphore = new Semaphore(2);

    static void useRestrictedResource()
    {
        semaphore.acquire();
        RestrictedResource.use();
        semaphore.release();
    }

    public static void main(String[] args)
    {
        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> useRestrictedResource()).start();
        });
    }
}
