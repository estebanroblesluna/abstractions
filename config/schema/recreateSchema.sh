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
mysql -u root < $BASEDIR/migration/0.1/update-icons-2.sql
mysql -u root < $BASEDIR/migration/0.1/resource-based-dust-renderer.sql
mysql -u root < $BASEDIR/migration/0.1/add-server-stats.sql
mysql -u root < $BASEDIR/migration/0.1/class-rename.sql
mysql -u root < $BASEDIR/migration/0.1/add-icons-table.sql
mysql -u root < $BASEDIR/migration/0.1/update-icons-3.sql
mysql -u root < $BASEDIR/migration/0.1/add-auth-tables.sql
mysql -u root < $BASEDIR/migration/0.1/add-resources-table.sql
mysql -u root < $BASEDIR/migration/0.1/add-external-id-server.sql

mysql -u root < $BASEDIR/migration/0.1/add-server-command.sql
mysql -u root < $BASEDIR/migration/0.1/add-profiling-info.sql
