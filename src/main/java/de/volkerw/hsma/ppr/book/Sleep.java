package de.volkerw.hsma.ppr.book;

import java.util.concurrent.TimeUnit;

public class Sleep
{
	public static void sleep(int time, TimeUnit unit)
	{
		try
		{
			unit.sleep(time);
		}
		catch (InterruptedException e)
		{
			return;
		}
	}
}