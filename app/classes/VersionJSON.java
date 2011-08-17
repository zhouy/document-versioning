//
//  VersionJSON.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 8/15/11.
//  Copyright 2011 yangzhou1030@gmail.com. All rights reserved.
//

package classes;

import java.util.*;

public class VersionJSON
{	
	public Long id;
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
		this.comments = new ArrayList<CommentJSON>();
	}
	
	/* */
	public void addComment(CommentJSON comment)
	{
		this.comments.add(comment);
	}
}