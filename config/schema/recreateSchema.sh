green='\033[0;32m';
yellow='\033[0;33m';
red='\033[0;31m';
reset='\033[0m';

echo -e "${yellow}Please wait, it may take a few minutes...${reset}";

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
mysql -u root < $BASEDIR/migration/0.1/update-resources-table.sql

mysql -u root < $BASEDIR/migration/0.1/add-logging-info.sql
mysql -u root < $BASEDIR/migration/0.1/optimize-resource-table.sql
mysql -u root < $BASEDIR/migration/0.1/extend-resource-blob-type.sql
mysql -u root < $BASEDIR/migration/0.1/initial-resources.sql

echo -e "${green}Done!${reset}";