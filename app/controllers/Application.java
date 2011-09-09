package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import play.db.jpa.JPA;
import models.*;
import classes.*;
import classes.serialization.*;
import javax.persistence.Query;
/*import play.libs.OpenID;
import play.libs.OpenID.*;*/

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
			// First check if user database table is empty
			if (User.count()==0)
			{
				Config config = Config.getInstance();
				// Add a global admin account
				User user = new User(config.getUserEmail(),
									 config.getUserPassword());
				user.save();
			}
            Authentication.login("Application.index");
        }
		else
		{
            renderArgs.put("user", Authentication.getEmail());
        }
    }
	
	/* */
    public static void index()
	{
		if(Authentication.isLoggedIn())
		{
			// First check if current user in database, if not then add save
			// user to database user table
			List<User> usersOnFile = User.find(
								"select u from User u where u.userEmail=?",
								Authentication.getEmail()).fetch();
			// Check if there an existing user matches logged in user
			if (usersOnFile.size()==0)
			{
				User user = new User(Authentication.getEmail(),
									 // Use empty password for dev stage
									 "password");
				user.save();
			}
			// Find logged in user in table
			Config config = Config.getInstance();
			usersOnFile = User.find(
									"select u from User u where u.userEmail=?",
									Authentication.getEmail()).fetch();
			config.setSingedInUserId(usersOnFile.get(0).id);
			
			List<Document> documents = Document.all().fetch();
			Object userEmail = renderArgs.get("user");
			render(userEmail, documents);
		}
		else
		{
			Application.login();
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
		User currentUser = User.findById(config.getSingedInUserId());
		// Create a new document for current user, current user is defined in Config
		Document document = new Document(subject, parentId).save();
		Version aversion = new Version(currentUser, document, content).save();
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
		Config config = Config.getInstance();
		User currentUser = User.findById(config.getSingedInUserId());
		Document document = Document.findById(id);
		Version newVersion = new Version(currentUser, document, content).save();
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
	
	/* */
	/*public static void testing()
	{
		// Thread testing
		Thread producer = new Thread(new Producer());
		Thread consumer = new Thread(new Consumer());
		producer.start();
		consumer.start();
		try
		{
		producer.join();
		consumer.join();
		}
		catch (InterruptedException ex)
		{
		}
		System.out.println("The value is " + Counter.value());
	}*/
}