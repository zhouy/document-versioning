//
//  DocumentVersions.java
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
import classes.serialization.*;

@With(Secure.class)
public class Versions extends CRUD
{
	/* */
	public static void ajaxDeleteVersion(Long documentId, Long versionId)
	{
		Document document = Document.findById(documentId);
		document.deleteVersion(versionId);
	}
}