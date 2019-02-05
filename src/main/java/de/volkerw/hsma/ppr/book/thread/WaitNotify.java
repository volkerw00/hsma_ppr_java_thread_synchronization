package de.volkerw.hsma.ppr.book.thread;

import java.util.concurrent.TimeUnit;

import static de.volkerw.hsma.ppr.book.Sleep.sleep;

class WaitNotify
{
	static Object	lock	= new Object();
	static Object	slot	= null;

	static class Taker implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				synchronized (lock)
				{
					while (slot == null)
						lock.wait();

					Object o = slot;
					slot = null;
					System.out.printf("Taken %s%n", o);

					lock.notifyAll();
				}
			}
			catch (InterruptedException e)
			{
				return;
			}
		}
	}

	static class Puter implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				synchronized (lock)
				{
					while (slot != null)
					  lock.wait();

					slot = new Object();

					lock.notifyAll();
				}
			}
			catch (InterruptedException e)
			{
				return;
			}
		}
	}

	public static void main(String[] args)
	{
		new Thread(new Taker()).start();
		sleep(2, TimeUnit.SECONDS);
		new Thread(new Puter()).start();
	}
}
