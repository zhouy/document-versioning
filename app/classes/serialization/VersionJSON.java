//
//  VersionJSON.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 8/15/11.
//  Copyright 2011 zhouy@moofwd.com All rights reserved.
//

package classes.serialization;

import java.util.*;

public class VersionJSON extends EntityJSON
{	
	public String content;
	public String date;
	public List<CommentJSON> comments; 
	
	/* */
	public VersionJSON()
	{
	}
	
	/* */
	public VersionJSON(Long id_, String content_, String date_)
	{
		this.id = id_;
		this.content = content_;
		this.date = date_;
	}
	
	/* */
	public void addComment(CommentJSON comment)
	{
		if (this.comments==null)
		{
			this.comments = new ArrayList<CommentJSON>();
		}
		this.comments.add(comment);
	}
}