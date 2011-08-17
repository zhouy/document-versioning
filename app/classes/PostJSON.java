//
//  PostJSON.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 8/15/11.
//  Copyright 2011 yangzhou1030@gmail.com. All rights reserved.
//

package classes;

import java.util.*;

public class PostJSON
{	
	public Long id;
	public String subject;
	public List<VersionJSON> versions; 
	
	/* */
	public PostJSON()
	{
	}
	
	/* */
	public PostJSON(Long id_, String subject_)
	{
		this.id = id_;
		this.subject = subject_;
		this.versions = new ArrayList<VersionJSON>();
	}
	
	/* */
	public void addVersion(VersionJSON version)
	{
		this.versions.add(version);
	}
}