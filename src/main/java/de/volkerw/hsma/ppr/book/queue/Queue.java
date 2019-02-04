package de.volkerw.hsma.ppr.book.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import static de.volkerw.hsma.ppr.book.Sleep.sleep;

class Queue
{
	static BlockingQueue<Object> queue = new SynchronousQueue<>();

	static void put(Object o)
	{
		try
		{
			queue.put(o);
		}
		catch (InterruptedException e)
		{
			return;
		}
	}

	static Object take()
	{
		try
		{
			return queue.take();
		}
		catch (InterruptedException e)
		{
			return null;
		}
	}

	public static void main(String[] args)
	{
		new Thread(() -> {
			System.out.printf("Taken %s", take());
		}).start();

		sleep(5, TimeUnit.SECONDS);

		new Thread(() -> {
			put(new Object());
		}).start();
	}
}
