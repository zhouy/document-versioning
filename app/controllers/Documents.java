//
//  Documents.java
//  playDocumentPermalink
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
import classes.serialization.*;

@With(Secure.class)
public class Documents extends CRUD
{
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/* Ajax section */
	
	/*
	 * List all documents
	 */
	public static void ajaxAllDocuments()
	{
		Serializer serializer = Serializer.getInstance();
		renderJSON(serializer.getAllDocuments());
	}
	
	/*
	 * List only requested documents
	 */
	public static void ajaxRequestDocuments(Long[] documentIds)
	{
		Serializer serializer = Serializer.getInstance();
		renderJSON(serializer.getDocuments(documentIds));
	}
	
	/*
	 * Create a document and save using Ajax
	 */
	public static void ajaxCreateDocument(String subject,
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
		System.out.println("* A new document is successfully created.");
		Version aversion = new Version(document, content).save();
		System.out.println("* A new version is successfully created.");
		document.addVersion(aversion);
		
		Documents.ajaxAllDocuments();
	}
	
	/* */
	public static void ajaxFormEdit(Long documentId)
	{
		Serializer serializer = Serializer.getInstance();
		renderJSON(serializer.getDocumentLastVersion(documentId));
	}
	
	/* */
	public static void ajaxEditDocument(Long documentId,
										String newTitle,
										String newContent)
	{
		Document document = Document.findById(documentId);
		document.changeSubject(newTitle);
		Version newVersion = new Version(document, newContent).save();
		System.out.println("* A new version is successfully created.");
		document.addVersion(newVersion);
		document.save();
	}
	
	/* */
	public static void ajaxDeleteDocument(Long documentId)
	{
		// If it is not the root document we are going to delete
		if (documentId!=0L)
		{
			Document document = Document.findById(documentId);
			List<Document> children = Document.find(
								"select p from Document p where p.parentId=?",
								document.id).fetch();
			// If target document contains children documents
			// then remove all children, don't leave scala documents
			int i = 0;
			for (i=0; i<children.size(); i++)
			{
				children.get(i).delete();
			}
			document.delete();
		}
	}
	
	/* */
	public static void ajaxBuildTree()
	{
		DocumentRootJSON root = new DocumentRootJSON("Documents");
		Serializer serializer = Serializer.getInstance();
		serializer.buildNode(0L, root);
		renderJSON(root);
	}
}