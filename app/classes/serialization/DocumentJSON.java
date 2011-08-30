//
//  DocumentJSON.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 8/15/11.
//  Copyright 2011 zhouy@moofwd.com All rights reserved.
//

package classes.serialization;

import java.util.*;

public class DocumentJSON extends EntityJSON
{	
	public String subject;
	public List<VersionJSON> versions;
	
	public String data;
	public String state;
	public AttributeJSON attr;
	public List<DocumentJSON> children;
	
	/* */
	public DocumentJSON(Long id_)
	{
		super(id_);
		this.data = "";
		initialize();
	}
	
	/* */
	public DocumentJSON(Long id_, String data_)
	{
		super(id_);
		this.data = data_;
		initialize();
	}
	
	/* */
	public DocumentJSON(Long id_, String data_, String subject_)
	{
		super(id_);
		this.data = data_;
		this.subject = subject_;
	}
	
	/* */
	public void initialize()
	{
		this.state = "open";
		this.attr = new AttributeJSON();
		this.attr.addId(this.id);
	}
	
	/* */
	public void addVersion(VersionJSON version)
	{
		if (this.versions==null)
		{
			this.versions = new ArrayList<VersionJSON>();
		}
		this.versions.add(version);
	}
	
	/* */
	public List<DocumentJSON> addChild(DocumentJSON node)
	{
		// Use lazy initialization
		if (this.children==null)
		{
			this.children = new ArrayList<DocumentJSON>();
		}
		this.children.add(node);
		return this.children;
	}
}