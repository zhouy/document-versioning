//
//  Comment.java
//  playDocumentPermalink
//
//  Created by Zhou Yang on 8/3/11.
//  Copyright 2011 zhouy@moofwd.com All rights reserved.
//

package models;

import play.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import java.text.*;

@Entity
public class Comment extends Model
{	
	@Required
	public Date date;
	
	@Required
	public String subject;
	
	@Required
	@Lob
	public String content;
	
	@Required
	@ManyToOne
	public Version version;
	
	public Comment()
	{
	}
	
	/* */
	public Comment(Version version_,
				   String subject_,
				   String content_)
	{
		this.version = version_;
		this.subject = subject_;
		this.content = content_;
		this.date = new Date();
	}
	
	/* */
	public String getTimef()
	{
		return DateFormat.getDateTimeInstance(DateFormat.SHORT,
											  DateFormat.SHORT).format(this.date);
	}
	
	/* */
	public <T extends JPABase> T delete()
	{
		return super.delete();
    }
}