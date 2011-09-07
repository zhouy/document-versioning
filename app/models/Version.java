//
//  DocumentEntry.java
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
import java.text.*;

@Entity
public class Version extends Model
{	
	@Required
	@Lob
	public String content;
	
	@Required
	public Date date;
	
	@Required
	@ManyToOne
	public Document document;
	
	@OneToMany(mappedBy="version", cascade=CascadeType.ALL)
	public List<Comment> comments;
	
	public Version()
	{		
	}
	
	public Version(Document document_, String content_)
	{
		this.document = document_;
		this.content = content_;
		this.date = new Date();
	}
	
	/* */
	public List<Comment> getAllComments()
	{
		return this.comments;
	}
	
	/* */
	public Version addComment(Comment comment)
	{
		// Use lazy initialization whenever possible
		if (this.comments==null)
		{
			this.comments = new ArrayList<Comment>();
		}
		this.comments.add(comment);
		return this;
	}
	
	/* */
	public Version deleteComment(Long id)
	{
		Comment retvar = null;
		for(Comment acomment : comments)
		{
			if (acomment.id==id)
			{
				retvar = acomment;
				comments.remove(acomment);
				break;
			}
		}
		if (retvar!=null)
		{
			retvar.delete();
		}
		this.save();
		return this;
	}
	
	/* */
	public String getTimef()
	{
		return DateFormat.getDateTimeInstance(DateFormat.SHORT,
											  DateFormat.SHORT).format(this.date);
	}
}