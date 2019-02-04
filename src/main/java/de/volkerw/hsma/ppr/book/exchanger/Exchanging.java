package de.volkerw.hsma.ppr.book.exchanger;

import static de.volkerw.hsma.ppr.book.Sleep.sleep;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

class Exchanging
{
	static Exchanger<Object> exchanger = new Exchanger<>();

	static Thread producer = new Thread(() -> {
		sleep(5, TimeUnit.SECONDS);
		exchange(new Object());
	});

	static Thread consumer = new Thread(() -> {
		System.out.println(exchange(null));
	});

	private static Object exchange(Object o)
	{
		try
		{
			return exchanger.exchange(o);
		}
		catch (InterruptedException e)
		{
			return null;
		}
	}

	public static void main(String[] args)
	{
		producer.start();
		consumer.start();
	}
}
