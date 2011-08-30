//
//  Security.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 8/19/11.
//  Copyright 2011 yangzhou1030@gmail.com. All rights reserved.
//

package controllers;

import models.*;

public class Security extends Secure.Security {
	
	/* */
    static boolean authenticate(String username, String password) {
        return true;
    }
    
}
