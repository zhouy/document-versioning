//
//  Comments.java
//  playDocumentPermalink
//
//  Created by Zhou Yang on 8/9/11.
//  Copyright 2011 Moofwd Inc All rights reserved.
//

package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import javax.persistence.Query;
import play.db.jpa.JPA;
import classes.serialization.*;

public class Comments extends CRUD
{
	/* */
	public static void ajaxComment(Long versionId,
								   String subject,
								   String content)
	{
		Version version = Version.findById(versionId);
		Comment comment = new Comment(version, subject, content).save();
		version.addComment(comment);
		version.save();
	}
	
	/* */
	public static void ajaxDeleteComment(Long versionId, Long commentId)
	{
		Version version = Version.findById(versionId);
		version.deleteComment(commentId);
	}
}