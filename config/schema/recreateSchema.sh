green='\033[0;32m';
yellow='\033[0;33m';
red='\033[0;31m';
reset='\033[0m';

echo -e "${yellow}Please wait, it may take a few minutes...${reset}";

BASEDIR=$(dirname $0)
DATABASE=actions
USER=root
HOST=localhost
PASS=

mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/common.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/actionsSchema.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/sample.sql

mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/update-icons.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/module-sql.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/module-cache.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/chain.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/null.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/amazon-sample.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-server-fields.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-sample-server.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/update-icons-2.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/resource-based-dust-renderer.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-server-stats.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/class-rename.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-icons-table.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/update-icons-3.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-auth-tables.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-resources-table.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-external-id-server.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-server-command.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-profiling-info.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/optimize-resource-table.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/extend-resource-blob-type.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/initial-resources.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/update-resources-table.sql
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-logging-info.sql

echo -e "${green}Done!${reset}";
