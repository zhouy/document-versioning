//
//  Posts.java
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

public class Posts extends CRUD
{
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/* Ajax section */
	
	/*
	 * List all posts
	 */
	public static void ajaxListPosts()
	{
		Serializer serializer = Serializer.getInstance();
		renderJSON(serializer.getAllPosts());
	}
	
	/*
	 * Create a post and save using Ajax
	 */
	public static void ajaxCreatePost(String subject, String content)
	{
		/*
		 * Create a post and save
		 * Create first version of the post
		 * Create permalink of that version
		 */
		
		Post post = new Post(subject).save();
		System.out.println("* A new post is successfully created.");
		Version aversion = new Version(post, content).save();
		System.out.println("* A new version is successfully created.");
		post.addVersion(aversion);
		
		Posts.ajaxListPosts();
	}
	
	/*
	 *
	 */
	public static void ajaxFormEdit(Long postId)
	{
		Serializer serializer = Serializer.getInstance();
		renderJSON(serializer.getPost(postId));
	}
	
	/*
	 *
	 */
	public static void ajaxEditPost(Long postId, String newContent)
	{
		Post post = Post.findById(postId);
		Version newVersion = new Version(post, newContent).save();
		System.out.println("* A new version is successfully created.");
		post.addVersion(newVersion);
		post.save();
	}
	
	/*
	 *
	 */
	public static void ajaxDeletePost(Long postId)
	{
		Post post = Post.findById(postId);
		post.delete();
	}
}