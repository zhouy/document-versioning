//
//  EntityJSON.java
//  playDoumentVersioning
//
//  Created by Zhou Yang on 8/19/11.
//  Copyright 2011 yangzhou1030@gmail.com. All rights reserved.
//

package classes.serialization;

import java.util.*;

abstract public class EntityJSON
{
	public Long id;
	
	public EntityJSON()
	{
	}
	
	public EntityJSON(Long id_)
	{
		this.id = id_;
	}
}
