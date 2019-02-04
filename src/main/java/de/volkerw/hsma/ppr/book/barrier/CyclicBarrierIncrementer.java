package de.volkerw.hsma.ppr.book.barrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class CyclicBarrierIncrementer
{
	static CyclicBarrier barrier;

	static AtomicInteger aInt = new AtomicInteger();

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
			System.out.printf("Worker %s: %s%n", number, 
																					 aInt.incrementAndGet());
			try
			{
				barrier.await();
				System.out.printf(
											"Barrier passed. Worker %s free.%n", number);
			}
			catch (InterruptedException | BrokenBarrierException e)
			{
				return;
			}
		}
	}

	public static void main(String[] args) 
																		throws InterruptedException
	{
		List<Thread> threads = new ArrayList<>();

		barrier = new CyclicBarrier(5, () -> {
			System.out.println(aInt.get());
		});

		IntStream.of(1, 2, 3, 4, 5).forEach(i -> {
			Thread thread = new Thread(new Worker(i));
			threads.add(thread);
			thread.start();
		});

		for (Thread thread : threads)
		{
			thread.join();
		}
	}
}
