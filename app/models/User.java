//
//  User.java
//  playDocumentPermalink
//
//  Created by Zhou Yang on 8/2/11.
//  Copyright 2011 Moofwd Inc All rights reserved.
//

package models;

import play.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class User extends Model
{	
	@Required
	public String userEmail;
	
	public String firstName;
	public String lastName;
	
	@Required
	public String password;
	
	@OneToMany(mappedBy="author", cascade=CascadeType.ALL)
	public List<Document> documents;
	
	/* Constructor with minmum requirements */
	public User(String userEmail_, String password_)
	{
		this.userEmail = userEmail_;
		this.password = password_;
		this.documents = new ArrayList<Document>();
	}
	
	/* Constructor with all data members */
	public User(String userEmail_,
				String firstName_,
				String lastName_,
				String password_)
	{		
		this.userEmail = userEmail_;
		this.firstName = firstName_;
		this.lastName = lastName_;
		this.password = password_;
		this.documents = new ArrayList<Document>();
	}
}