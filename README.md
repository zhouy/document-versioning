For a working copy:
---------
* Uncomment the following line in app/conf/application.conf: 
	
		db=mem

For problematic copy:
---------
* Create a database document_versioning using the following command:
	
		mysql> create database document_versioning;

* Uncomment the following line in app/conf/application.conf of:
	
		db=mysql:root@document_versioning