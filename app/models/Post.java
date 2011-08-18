//
//  Post.java
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

/*
 * For each document, only one post will be generated and it may contain several
 * versions
 */
@Entity
public class Post extends Model
{	
	@Required
	public String subject;
	
	@Required
	@ManyToOne
	public User author;
	
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL)
	public List<Version> versions;
	
	public Post()
	{
	}
	
	/* Constructor with minmum requirements */
	public Post(String subject_)
	{
		this.subject = subject_;
		this.versions = new ArrayList<Version>();
	}
	
	/* Add a version to existing post */
	public Post addVersion(Version aversion_)
	{
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
	public Post deleteVersion(Long id)
	{
		// How to delete an entity with dependecy?
		// If post contains only one version, then remove the post as well?
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