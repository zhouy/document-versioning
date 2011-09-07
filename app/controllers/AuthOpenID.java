//
//  OpenID.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 9/6/11.
//  Copyright 2011 zhouy@moofwd.com All rights reserved.
//

package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import play.libs.OpenID;
import play.libs.OpenID.*;

public class AuthOpenID extends Controller
{
	/* */
	@Before(unless={"login", "authenticate"})
	static void checkAuthenticated()
	{
		if(!session.contains("user.id"))
		{
			System.out.println("* Heading back to login");
			AuthOpenID.login();
		}
	}
	
	/* */
	public static void login()
	{
		String greeting = "Greetings: " + session.get("user.id");
		render(greeting);
	}
	
	/* */
	public static void logout()
	{
		session.remove("user.id");
		session.remove("user.email");
		System.out.println("* User logged out");
		AuthOpenID.login();
	}
    
	/* */
	public static void authenticate()
	{
		if (OpenID.isAuthenticationResponse())
		{
			// Retrieve the verified id
			UserInfo user = OpenID.getVerifiedID();
			if (user == null)
			{
				flash.put("error", "Oops. Authentication has failed");
				AuthOpenID.login();
			}
			else
			{
				session.put("user.id", user.id);
				session.put("user.email", user.extensions.get("email"));
				AuthOpenID.login();
			}
		}
		else
		{
			// Verify the id
			if (!OpenID.id("https://www.google.com/accounts/o8/id")
				.required("email", "http://axschema.org/contact/email")
				.verify())
			{
				flash.put("error", "Oops. Cannot contact google");
				AuthOpenID.login();
			}
		}
	}
}