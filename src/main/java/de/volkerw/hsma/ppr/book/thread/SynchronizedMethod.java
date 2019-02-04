package de.volkerw.hsma.ppr.book.thread;

class SynchronizedMethod
{
	static int		counter	= 0;
	static Object	lock		= new Object();

	static synchronized void increment()
	{
		counter = counter + 1;
		System.out.printf("Counter is %s%n", counter);
	}

	public static void main(String[] args)
	{
		for (int i = 0; i < 10; i++)
		{
			new Thread(() -> increment()).start();
		}
	}
}
