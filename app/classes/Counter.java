//
//  Counter.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 9/9/11.
//  Copyright 2011 yangzhou1030@gmail.com. All rights reserved.
//

package classes;

public class Counter
{
    private static int c = 0;
	
    public static synchronized void increment()
	{
        c++;
    }
	
    public static synchronized void decrement()
	{
        c--;
    }
	
    public static synchronized int value()
	{
        return c;
    }
}
