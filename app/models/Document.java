//
//  Document.java
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

/*
 * For each document, only one document will be generated and it may contain several
 * versions
 */
@Entity
public class Document extends Model
{	
	@Required
	public String subject;
	
	@Required
	@ManyToOne
	public User author;
	
	public Long parentId;
	
	@OneToMany(mappedBy="document", cascade=CascadeType.ALL)
	public List<Version> versions;
	
	/* Constructor with minmum requirements */
	public Document(User author_, String subject_, Long parentId_)
	{
		this.author = author_;
		this.subject = subject_;
		this.parentId = parentId_;
	}
	
	/* Add a version to existing document */
	public Document addVersion(Version aversion_)
	{
		if (this.versions==null)
		{
			this.versions = new ArrayList<Version>();
		}
		this.versions.add(aversion_);
		return this;
	}
	
	/* Retrieve all versions */
	public List<Version> getAllVersions()
	{
		return this.versions;
	}
	
	/* */
	public Version getLastVersion()
	{
		Version lastVersion = null;
		if (versions.size()>0)
		{
			lastVersion = this.versions.get(versions.size()-1);
		}
		return lastVersion;
	}
	
	/* */
	public Document deleteVersion(Long id)
	{
		// How to delete an entity with dependecy?
		// If document contains only one version, then remove the document as well?
		Version retvar = null;
		for(Version aversion : versions)
		{
			if (aversion.id==id)
			{
				retvar = aversion;
				versions.remove(aversion);
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
	public <T extends JPABase> T delete()
	{
		return super.delete();
    }
}