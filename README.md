Using memory as database:
---------
* Uncomment the following line in app/conf/application.conf: 
	
		db=mem

Using MySQL 5:
---------
* Create a database document_versioning using the following command:
	
		mysql> create database document_versioning;

* Uncomment the following line in app/conf/application.conf of:
	
		db=mysql:root@document_versioning
		
* It is possible that you encounter a problem of dependency (MySQL 5.5), please try execute following command:
		
		$ mysql -u root
		mysql> use document_versioning
		mysql> alter table comment add constraint fk_comment_version foreign key (version_id) references version(id);