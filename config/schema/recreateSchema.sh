BASEDIR=$(dirname $0)

mysql -u root < $BASEDIR/common.sql
mysql -u root < $BASEDIR/actionsSchema.sql
mysql -u root < $BASEDIR/sample.sql

mysql -u root < $BASEDIR/migration/0.1/update-icons.sql
mysql -u root < $BASEDIR/migration/0.1/module-sql.sql
mysql -u root < $BASEDIR/migration/0.1/module-cache.sql
mysql -u root < $BASEDIR/migration/0.1/chain.sql
mysql -u root < $BASEDIR/migration/0.1/null.sql
mysql -u root < $BASEDIR/migration/0.1/amazon-sample.sql
mysql -u root < $BASEDIR/migration/0.1/add-server-fields.sql
mysql -u root < $BASEDIR/migration/0.1/add-sample-server.sql


