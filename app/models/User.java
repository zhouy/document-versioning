//
//  User.java
//  playPostPermalink
//
//  Created by Zhou Yang on 8/2/11.
//  Copyright 2011 zhouy@moofwd.com All rights reserved.
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
	
	@OneToMany(mappedBy="author", cascade=CascadeType.ALL)
	public List<Post> posts;
	
	/* Constructor with minmum requirements */
	public User(String userEmail_)
	{
		this.userEmail = userEmail_;
		this.posts = new ArrayList<Post>();
	}
	
	/* Constructor with all data members */
	public User(String userEmail_,
				String firstName_,
				String lastName_)
	{		
		this.userEmail = userEmail_;
		this.firstName = firstName_;
		this.lastName = lastName_;
		this.posts = new ArrayList<Post>();
	}
}