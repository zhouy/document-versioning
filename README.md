For a working copy:
-------------------------------------------------------------------
Uncomment the following line in app/conf/application.conf: 

db=mem

For problematic copy:
-------------------------------------------------------------------
1. Create a database document_versioning using the following command:

mysql> create database document_versioning;

2. Uncomment the following line in app/conf/application.conf of:</h3>

#db=mysql:root@document_versioning