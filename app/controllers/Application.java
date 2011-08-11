package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import javax.persistence.Query;
import play.db.jpa.JPA;
import java.util.Random;
import java.text.*;

public class Application extends Controller {

    public static void index() {
		
		List<Post> posts = Post.all().fetch();
        render(posts);
    }

	/*
	 * Create a post and save
	 */
	public static void create(String subject, String content) {
		
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
	public static void form(Long id) {
		
		Post post = Post.findById(id);
		Version lastVersion = post.getLastVersion();
		render(id, post, lastVersion);
	}
	
	/*
	 * Edit the last version of a post and create a new version
	 */
	public static void edit(Long id, String subject, String content) {
		
		Post post = Post.findById(id);
		Version newVersion = new Version(post, content).save();
		post.addVersion(newVersion);
		post.save();
		
		/* Redirect to index page */
		Application.index();
	}
	
	public static void comment(Long postId, Long versionId) {
		
		render(postId, versionId);
	}
	
	public static void postComment(Long postId,
								   Long versionId,
								   String subject,
								   String content) {
		
		Version version = Version.findById(versionId);
		Comment comment = new Comment(version, subject, content);
		version.addComment(comment);
		//comment.save();
		version.save();
		
		/* Redirect to index page */
		Application.index();
	}
	
	/*
	 * Delete was not implemented
	 */
	public static void deleteVersion(Long postId, Long versionId) {
	
		Post post = Post.findById(postId);
		post.deleteVersion(versionId);
		
		/* Redirect to index page */
		Application.index();
	}
	
	/*
	 * Delete was not implemented
	 */
	public static void deleteComment(Long commentId) {
		
		
		/* Redirect to index page */
		Application.index();
	}
	
	/* One simple example of Ajax in play framework */
	/*public static void ajaxtest(String data) {
		
		// renderJSON function will take an object as argument, string will not
		// work in this case
		Object greetings = "Hello world!";
		renderJSON(greetings);
	}*/
}