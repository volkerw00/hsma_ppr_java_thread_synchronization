package de.volkerw.hsma.ppr.book.lock;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import de.volkerw.hsma.ppr.book.Sleep;

class ConditionWaiting
{
	static ReentrantLock	lock	= new ReentrantLock();
	static Condition			set		= lock.newCondition();
	static Condition			empty	= lock.newCondition();

	static Object slot;

	static void put(Object o) throws InterruptedException
	{
		lock.lock();

		if (slot != null)
		  empty.await();

		slot = o;
		set.signal();

		lock.unlock();
	}

	static Object take() throws InterruptedException
	{
		lock.lock();
		Object result;

		if (slot == null)
		  set.await();
		result = slot;
		slot = null;

		empty.signal();
		lock.unlock();
		return result;
	}

	public static void main(String[] args)
	{
		new Thread(() -> {
			try
			{
				for (int i = 0; i < 10; i++)
				{
					Sleep.sleep(1, TimeUnit.SECONDS);
					put(new Object());
				}
			}
			catch (InterruptedException e)
			{
				return;
			}
		}).start();
		for (int i = 0; i < 10; i++)
		{
			new Thread(() -> {
				try
				{
					System.out.printf("Taking object %s at %s%n",
					                  take(),
					                  Instant.now());
				}
				catch (InterruptedException e)
				{
					return;
				}
			}).start();
		}
	}
}
