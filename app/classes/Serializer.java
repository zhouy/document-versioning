//
//  Serializer.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 8/15/11.
//  Copyright 2011 yangzhou1030@gmail.com. All rights reserved.
//

package classes;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import play.db.jpa.JPA;
import java.text.*;

public class Serializer
{	
	private static Serializer serializer;
	
	/* Hide constructor */
	private Serializer()
	{
	}
	
	/* Singleton class implementation */
	public static Serializer getInstance()
	{
		if (serializer==null)
		{
			serializer = new Serializer();
		}
		return serializer;
	}
	
	/* Get serializable posts */
	public List<PostJSON> getAllPosts()
	{
		List<Post> posts = Post.all().fetch();
		List<PostJSON> postJSONs = new ArrayList<PostJSON>();
		
		int i = 0, j = 0, k = 0;
		// Add posts which need to be serialized
		for (i=0; i<posts.size(); i++)
		{
			Post post = posts.get(i);
			PostJSON postJSON = new PostJSON(post.id, post.subject);
			
			// Add all versions
			for (j=0; j<post.versions.size(); j++)
			{
				Version version = post.versions.get(j);
				VersionJSON versionJSON = new VersionJSON(version.id,
														  version.content,
														  version.getTimef());
				// Add all comments to the version
				for (k=0; k<version.comments.size(); k++)
				{
					Comment comment = version.comments.get(k);
					CommentJSON commentJSON = new CommentJSON(comment.id,
															  comment.subject,
															  comment.content,
															  comment.getTimef());
					versionJSON.addComment(commentJSON);
				}
				postJSON.addVersion(versionJSON);
			}
			postJSONs.add(postJSON);
		}
		return postJSONs;
	}
	
	/* Create serializable post with only the last version */
	public PostJSON getPost(Long id)
	{
		Post post = Post.findById(id);
		Version lastVersion = post.getLastVersion();
		// Create a post serializable object
		PostJSON postJSON = new PostJSON(post.id, post.subject);
		// Create a version serializable
		if (lastVersion!=null)
		{
			VersionJSON versionJSON = new VersionJSON(lastVersion.id,
													  lastVersion.content,
													  lastVersion.getTimef());
			Integer i = 0;
			// Add all comments to the version
			for (i=0; i<lastVersion.comments.size(); i++)
			{
				Comment comment = lastVersion.comments.get(i);
				CommentJSON commentJSON = new CommentJSON(comment.id,
														  comment.subject,
														  comment.content,
														  comment.getTimef());
				versionJSON.addComment(commentJSON);
			}
			postJSON.addVersion(versionJSON);
		}
		return postJSON;
	}
}