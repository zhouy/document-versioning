//
//  Config.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 8/19/11.
//  Copyright 2011 Moofwd Inc All rights reserved.
//

package classes;

import java.util.*;

public class Config
{
	private static Config config;
	
	/* Hide constructor */
	private Config()
	{
	}
	
	/* Singleton class implementation */
	public static Config getInstance()
	{
		if (config==null)
		{
			config = new Config();
		}
		return config;
	}
	
	/* Temporary UserId, Username and Password */
	public String getUserEmail()
	{
		return "admin";
	}
	
	public String getUserPassword()
	{
		return "password";
	}
	
	/* I assume that the program is operated by user id 1 */
	public Long getUserId()
	{
		return 1L;
	}
}