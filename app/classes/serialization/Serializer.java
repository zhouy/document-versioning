//
//  Serializer.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 8/15/11.
//  Copyright 2011 zhouy@moofwd.com All rights reserved.
//

package classes.serialization;

import play.*;
import play.mvc.*;
import java.util.*;
import models.*;
import play.db.jpa.JPA;
import java.text.*;
import classes.*;
import classes.serialization.*;

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
	
	/* Get all serializable documents */
	public List<DocumentJSON> getAllDocuments()
	{
		Config config = Config.getInstance();
		// Find documents using JPQL query
		List<Document> documents = Document.find(
		"select p from Document p join p.author u where p.author.id=u.id and u.id=?",
												config.getUserId()).fetch();
		List<DocumentJSON> documentJSONs = new ArrayList<DocumentJSON>();
		
		int i = 0, j = 0, k = 0;
		// Add documents which need to be serialized
		for (i=0; i<documents.size(); i++)
		{
			Document document = documents.get(i);
			DocumentJSON documentJSON = new DocumentJSON(document.id,
														 document.subject,
														 document.subject);
			
			// Add all versions
			for (j=0; j<document.versions.size(); j++)
			{
				Version version = document.versions.get(j);
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
				documentJSON.addVersion(versionJSON);
			}
			documentJSONs.add(documentJSON);
		}
		return documentJSONs;
	}
	
	/* Create serializable document */
	public DocumentJSON getDocument(Long id)
	{
		Document document = Document.findById(id);
		// Create a document serializable object
		DocumentJSON documentJSON = new DocumentJSON(document.id,
													 document.subject,
													 document.subject);
		int j = 0, k = 0;
		// Add all versions
		for (j=0; j<document.versions.size(); j++)
		{
			Version version = document.versions.get(j);
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
			documentJSON.addVersion(versionJSON);
		}
		return documentJSON;
	}
	
	/* Create serializable document with only the last version */
	public DocumentJSON getDocumentLastVersion(Long id)
	{
		Document document = Document.findById(id);
		Version lastVersion = document.getLastVersion();
		// Create a document serializable object
		DocumentJSON documentJSON = new DocumentJSON(document.id,
													 document.subject,
													 document.subject);
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
			documentJSON.addVersion(versionJSON);
		}
		return documentJSON;
	}
	
	/* Get all serializable documents */
	public List<DocumentJSON> getDocuments(Long[] documentIds)
	{
		List<DocumentJSON> documentJSONs = new ArrayList<DocumentJSON>();
		int i = 0;
		for (i=0; i<documentIds.length; i++)
		{
			documentJSONs.add(this.getDocument(documentIds[i]));
		}
		return documentJSONs;
	}
	
	/* */
	public void buildNode(Long parentDocumentId, DocumentJSON parentNode)
	{
		String queryString = "select p from Document p where p.parentId=";
		if (parentDocumentId==0L)
		{
			queryString += 0L;
		}
		else
		{
			queryString += parentDocumentId;
		}
		List<Document> documents = Document.find(queryString).fetch();
		Integer i = 0;
		for (i=0; i<documents.size(); i++)
		{
			Document childDocument = documents.get(i);
			DocumentJSON childNode = new DocumentJSON(childDocument.id,
													  childDocument.subject);
			parentNode.addChild(childNode);
			buildNode(childDocument.id, childNode);
		}
	}
}