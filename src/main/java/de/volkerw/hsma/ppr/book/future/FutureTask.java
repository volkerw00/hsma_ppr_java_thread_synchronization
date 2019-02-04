package de.volkerw.hsma.ppr.book.future;

import static de.volkerw.hsma.ppr.book.Sleep.sleep;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class Future
{
	public static void main(String[] args) 
																throws InterruptedException, 
																			ExecutionException
	{
		FutureTask<?> task = new FutureTask<>(() -> {
			sleep(2, TimeUnit.SECONDS);
			return new Object();
		});

		new Thread(task).start();

		task.get();
	}
}
