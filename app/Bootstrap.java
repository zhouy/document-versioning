//
//  Bootstrap.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 8/19/11.
//  Copyright 2011 Moofwd Inc All rights reserved.
//

import play.*;
import play.jobs.*;
import play.test.*;
import models.*;
import classes.*;

@OnApplicationStart
public class Bootstrap extends Job {
	
    public void doJob()
	{
		// This is a temporary solution to user data
        // If no user added to database, then add one user
        if(User.count() == 0)
		{
			Config config = Config.getInstance();
			User user = new User(config.getUserEmail(),
								 config.getUserPassword());
			user.save();
        }
    }
	
}
