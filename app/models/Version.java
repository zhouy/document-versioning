//
//  PostEntry.java
//  playPostPermalink
//
//  Created by Zhou Yang on 8/2/11.
//  Copyright 2011 yangzhou1030@gmail.com. All rights reserved.
//

package models;

import play.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;
import java.text.*;

@Entity
public class Version extends Model {
	
	@Required
	@Lob
	public String content;
	
	@Required
	public Date date;
	
	@Required
	@ManyToOne
	public Post post;
	
	@OneToMany(mappedBy="version", cascade=CascadeType.ALL)
	public List<Comment> comments;
	
	public Version(Post post_, String content_) {
		
		this.post = post_;
		this.content = content_;
		this.date = new Date();
		this.comments = new ArrayList<Comment>();
	}
	
	/* */
	public List<Comment> getAllComments() {
		
		return this.comments;
	}
	
	/* */
	public Version addComment(Comment comment) {
		
		comments.add(comment);
		return this;
	}
	
	/* */
	public String getTimef() {
		
		return DateFormat.getDateTimeInstance(DateFormat.SHORT,
											  DateFormat.SHORT).format(this.date);
	}
}