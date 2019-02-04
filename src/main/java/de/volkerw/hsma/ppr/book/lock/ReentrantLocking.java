package de.volkerw.hsma.ppr.book.lock;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import static de.volkerw.hsma.ppr.book.Sleep.sleep;

class ReentrantLocking
{
	static ReentrantLock	lock		= new ReentrantLock();
	static int						counter	= 0;

	static void increment()
	{
		lock.lock();
		System.out.printf("Waiting Threads: %s%n", lock.getQueueLength());
		sleep(1000, TimeUnit.MILLISECONDS);
		counter = counter + 1;
		System.out.printf("Incrementing at %s%n", Instant.now());
		lock.unlock();
	}

	public static void main(String[] args)
	{
		Runnable incrementer = () -> increment();
		for (int i = 0; i < 10; i++)
		{
			new Thread(incrementer).start();
		}
	}
}
