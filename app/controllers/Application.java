package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import javax.persistence.Query;
import play.db.jpa.JPA;
import java.text.*;
import classes.*;

public class Application extends Controller
{
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/* Regular */
	
	/* */
    public static void index()
	{
		List<Post> posts = Post.all().fetch();
        render(posts);
    }

	/*
	 * Create a post and save
	 */
	public static void create(String subject, String content)
	{
		/*
		 * Create a post and save
		 * Create first version of the post
		 * Create permalink of that version
		 */
		
		Post post = new Post(subject).save();
		Version aversion = new Version(post, content).save();
		post.addVersion(aversion);
		
		/* Redirect to index page */
		Application.index();
	}
	
	/*
	 * Generate a form of existing post using the last version of that post
	 */
	public static void form(Long id)
	{
		Post post = Post.findById(id);
		Version lastVersion = post.getLastVersion();
		render(id, post, lastVersion);
	}
	
	/*
	 * Edit the last version of a post and create a new version
	 */
	public static void edit(Long id, String subject, String content)
	{
		Post post = Post.findById(id);
		Version newVersion = new Version(post, content).save();
		post.addVersion(newVersion);
		post.save();
		
		/* Redirect to index page */
		Application.index();
	}
	
	public static void comment(Long postId, Long versionId)
	{
		render(postId, versionId);
	}
	
	public static void postComment(Long postId,
								   Long versionId,
								   String subject,
								   String content)
	{
		Version version = Version.findById(versionId);
		Comment comment = new Comment(version, subject, content).save();
		version.addComment(comment);
		version.save();
		
		/* Redirect to index page */
		Application.index();
	}
	
	/*
	 *
	 */
	public static void deleteVersion(Long postId, Long versionId)
	{
		Post post = Post.findById(postId);
		post.deleteVersion(versionId);
		
		/* Redirect to index page */
		Application.index();
	}
	
	/*
	 *
	 */
	public static void deleteComment(Long versionId, Long commentId)
	{
		Version version = Version.findById(versionId);
		version.deleteComment(commentId);
		
		/* Redirect to index page */
		Application.index();
	}
	
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
		
		Application.ajaxListPosts();
	}
	
	/*
	 *
	 */
	public static void ajaxGenerateFormEdit(Long postId)
	{
		Serializer serializer = Serializer.getInstance();
		renderJSON(serializer.getPost(postId));
	}
	
	/*
	 *
	 */
	public static void ajaxPostEdit(Long postId, String newContent)
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
	public static void ajaxDeleteVersion(Long postId, Long versionId)
	{
		Post post = Post.findById(postId);
		post.deleteVersion(versionId);
	}
	
	/*
	 *
	 */
	public static void ajaxComment(Long versionId,
								   String subject,
								   String content)
	{
		Version version = Version.findById(versionId);
		Comment comment = new Comment(version, subject, content).save();
		version.addComment(comment);
		version.save();
	}
	
	/*
	 *
	 */
	public static void ajaxDeleteComment(Long versionId, Long commentId)
	{
		Version version = Version.findById(versionId);
		version.deleteComment(commentId);
	}
}