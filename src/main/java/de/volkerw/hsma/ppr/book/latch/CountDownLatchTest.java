package de.volkerw.hsma.ppr.book.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class CountDownLatchTest
{
	static CountDownLatch	doneLatch;
	static AtomicInteger	aInt	= new AtomicInteger();

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
				System.out.printf("Worker %s result after increment: %s%n",
				                  number,
				                  aInt.incrementAndGet());
				doneLatch.countDown();
		}
	}

	public static void main(String[] args)
	{
		doneLatch = new CountDownLatch(5);

		IntStream.of(1, 2, 3, 4, 5).forEach(i -> {
			Thread thread = new Thread(new Worker(i));
			thread.start();
		});

		try
		{
			doneLatch.await();
			System.out.printf("Result is %s%n", aInt.get());
		}
		catch (InterruptedException e)
		{
			return;
		}
	}
}
