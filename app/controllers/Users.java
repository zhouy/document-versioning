//
//  Users.java
//  playDocumentPermalink
//
//  Created by Zhou Yang on 8/2/11.
//  Copyright 2011 Moofwd Inc All rights reserved.
//

package controllers;

import play.*;
import play.mvc.*;
import classes.*;

public class Users extends CRUD
{
	/* */
	public static void getUserId()
	{
		Config config = Config.getInstance();
		renderJSON(config.getUserId());
	}
	
	/* */
	public static void getUserName()
	{
	}
	
	/* */
	public static void getUserPassword()
	{
	}
}