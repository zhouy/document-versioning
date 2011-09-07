package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import javax.persistence.Query;
import play.db.jpa.JPA;
import classes.*;
import classes.serialization.*;
import play.libs.OpenID;
import play.libs.OpenID.*;

public class Application extends Controller
{
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/* Regular */
	
	/* */
	@Before
    static void checkConnected()
	{
        if (Authentication.getUser()==null)
		{
            Authentication.login("Application.index");
        }
		else
		{
            renderArgs.put("user.email", Authentication.getEmail());
        }
    }
	
	/* */
    public static void index()
	{
		if(Authentication.isLoggedIn())
		{
			System.out.println("* User successfully logged in");
			List<Document> documents = Document.all().fetch();
			render(documents);
        }
    }

	/*
	 * Create a document and save
	 */
	public static void create(String subject,
							  String content,
							  Long parentId)
	{
		/*
		 * Create a document and save
		 * Create first version of the document
		 * Create permalink of that version
		 */
		
		Config config = Config.getInstance();
		User currentUser = User.findById(config.getUserId());
		// Create a new document for current user, current user is defined in Config
		Document document = new Document(currentUser, subject, parentId).save();
		Version aversion = new Version(document, content).save();
		document.addVersion(aversion);
		
		/* Redirect to index page */
		Application.index();
	}
	
	/*
	 * Generate a form of existing document using the last version of that document
	 */
	public static void form(Long id)
	{
		Document document = Document.findById(id);
		Version lastVersion = document.getLastVersion();
		render(id, document, lastVersion);
	}
	
	/*
	 * Edit the last version of a document and create a new version
	 */
	public static void edit(Long id, String subject, String content)
	{
		Document document = Document.findById(id);
		Version newVersion = new Version(document, content).save();
		document.addVersion(newVersion);
		document.save();
		
		/* Redirect to index page */
		Application.index();
	}
	
	/* */
	public static void comment(Long documentId, Long versionId)
	{
		render(documentId, versionId);
	}
	
	/* */
	public static void documentComment(Long documentId,
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
	
	/* */
	public static void deleteVersion(Long documentId, Long versionId)
	{
		Document document = Document.findById(documentId);
		document.deleteVersion(versionId);
		
		/* Redirect to index page */
		Application.index();
	}
	
	/* */
	public static void deleteComment(Long versionId, Long commentId)
	{
		Version version = Version.findById(versionId);
		version.deleteComment(commentId);
		
		/* Redirect to index page */
		Application.index();
	}
	
	/* */
    public static void login()
	{
		System.out.println("* User logged in");
        Authentication.login("Application.index");
    }
	
	/* */
    public static void logout()
	{
		System.out.println("* User logged out");
        Authentication.logout("Application.index");
    }
}