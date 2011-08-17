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
		
* Problem solved
		
	If using MYSQL5.5, please execute following command:
		
		$ mysql -u root
		mysql> use document_versioning
		mysql> alter table comment add constraint fk_comment_version foreign key (version_id) references version(id);