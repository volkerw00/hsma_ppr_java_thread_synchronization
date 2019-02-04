package de.volkerw.hsma.ppr.book.semaphore;

import java.time.Instant;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import static de.volkerw.hsma.ppr.book.Sleep.sleep;

class SemaphoreRateLimiting
{
	static Semaphore semaphore;

	static class Worker implements Runnable
	{
		private int number;

		public Worker(int number)
		{
			this.number = number;
		}

		@Override
		public void run()
		{
			try
			{
				semaphore.acquire();
				System.out.printf("Worker %s aquired semaphore at %s.%n",
				                  number,
				                  Instant.now());
				sleep(1000, TimeUnit.MILLISECONDS);
				semaphore.release();
			}
			catch (InterruptedException e)
			{
				return;
			}
		}
	}

	public static void main(String[] args)
	{
		semaphore = new Semaphore(2);

		IntStream.of(1, 2, 3, 4, 5, 6, 7, 8).forEach(i -> {
			Thread thread = new Thread(new Worker(i));
			thread.start();
		});
	}
}
