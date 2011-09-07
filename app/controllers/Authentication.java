//
//  Authentication.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 9/6/11.
//  Copyright 2011 zhouy@moofwd.com All rights reserved.
//

package controllers;

import play.modules.gae.*;
import com.google.appengine.api.users.*;

public class Authentication
{	
    public static boolean isLoggedIn()
	{
        return GAE.isLoggedIn();
    }
	
    public static String getEmail()
	{
        return GAE.getUser().getEmail();
    }
	
	public static User getUser() {
        return GAE.getUser();
    }
	
    public static void login(String action)
	{
        GAE.login(action);
    }
	
    public static void logout(String action)
	{
        GAE.logout(action);
    }
}