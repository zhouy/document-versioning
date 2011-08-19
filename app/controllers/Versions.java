//
//  PostVersions.java
//  playPostPermalink
//
//  Created by Zhou Yang on 8/9/11.
//  Copyright 2011 zhouy@moofwd.com All rights reserved.
//

package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import javax.persistence.Query;
import play.db.jpa.JPA;
import classes.*;

public class Versions extends CRUD
{
	/*
	 *
	 */
	public static void ajaxDeleteVersion(Long postId, Long versionId)
	{
		Post post = Post.findById(postId);
		post.deleteVersion(versionId);
	}
}