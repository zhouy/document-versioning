//
//  Consumer.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 9/9/11.
//  Copyright 2011 yangzhou1030@gmail.com. All rights reserved.
//

package classes;

public class Consumer extends Thread
	{	
		public void run()
		{
			int i = 0;
			for (i=0; i<10; i++)
			{
				try
				{
					Thread.sleep(300);
				}
				catch (InterruptedException ex)
				{
					System.out.println("Thread is interrupted");
					return;
				}
				Counter.decrement();
				System.out.println("Consumer consumes");
			}
		}
	}
