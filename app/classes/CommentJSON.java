//
//  CommentJSON.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 8/16/11.
//  Copyright 2011 zhouy@moofwd.com All rights reserved.
//

package classes;

import java.util.*;

public class CommentJSON extends EntityJSON
{	
	public String subject;
	public String content;
	public String date;
	
	/* */
	public CommentJSON()
	{
	}
	
	/* */
	public CommentJSON(Long id_, String subject_, String content_, String date_)
	{
		this.id = id_;
		this.subject = subject_;
		this.content = content_;
		this.date = date_;
	}	
}
