//
//  Permalink.java
//  playDocumentPermalink
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
public class Permalink extends Model
{	
	@Required
	public String subject;
	
	@Lob
	public String content;
	
	/* Constructor with minmum requirements */
	public Permalink(String subject_)
	{
		this.subject = subject_;
	}
	
	/* Constructor with all data members */
	public Permalink(String subject_, String content_)
	{
		this.subject = subject_;
		this.content = content_;
	}
}