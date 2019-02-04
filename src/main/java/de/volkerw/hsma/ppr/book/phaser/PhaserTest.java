package de.volkerw.hsma.ppr.book.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class PhaserTest
{
	static Phaser phaser;

	static AtomicInteger aInt = new AtomicInteger();

	static class Waiter implements Runnable
	{
		private int number;

		public Waiter(int number)
		{
			this.number = number;
			phaser.register();
		}

		@Override
		public void run()
		{
			System.out.printf("Waiter %s awaiting advance%n", number);
			aInt.incrementAndGet();
			int phase = phaser.arriveAndAwaitAdvance();
			System.out.printf("Waiter %s advanced and "
			                  + "arriving on phase %s.%n",
			                  number,
			                  phase);
			aInt.incrementAndGet();
			phase = phaser.arriveAndAwaitAdvance();
			System.out.printf("Waiter %s advanced and "
			                  + "arriving on phase %s.%n",
			                  number,
			                  phase);
			phaser.arriveAndDeregister();
			System.out.printf("Waiter %s arrived!%n", number);
		}
	}

	public static void main(String[] args)
	{
		phaser = new Phaser() {
			@Override
			protected boolean onAdvance(int phase, int registeredParties)
			{
				if (registeredParties == 0)
				{
					System.out.printf("Count is %s%n", aInt.get());
					return aInt.get() == 10;
				}
				return false;
			}
		};

		IntStream.of(1, 2, 3, 4, 5).forEach(i -> {
			Thread thread = new Thread(new Waiter(i));
			thread.start();
		});
	}
}
