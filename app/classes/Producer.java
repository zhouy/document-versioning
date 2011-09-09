//
//  TestThread.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 9/9/11.
//  Copyright 2011 yangzhou1030@gmail.com. All rights reserved.
//

package classes;

public class Producer extends Thread
{	
    public void run()
	{
		int i = 0;
		for (i=0; i<5; i++)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException ex)
			{
				System.out.println("Thread is interrupted");
				return;
			}
			Counter.increment();
			System.out.println("Producer produces");
		}
    }
}
