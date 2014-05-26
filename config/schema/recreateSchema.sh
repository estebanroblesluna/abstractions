#!/bin/bash

green='\033[0;32m';
yellow='\033[0;33m';
red='\033[0;31m';
reset='\033[0m';

BASEDIR=$(dirname $0)
DATABASE=actions
USER=root
HOST=localhost
PASS=

echo $0;
echo -e "${yellow}Executing common.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/common.sql
echo -e "${yellow}Executing actionsSchema.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/actionsSchema.sql
echo -e "${yellow}Executing sample.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/sample.sql
echo -e "${yellow}Executing update-icons.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/update-icons.sql
echo -e "${yellow}Executing module-sql.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/module-sql.sql
echo -e "${yellow}Executing module-cache.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/module-cache.sql
echo -e "${yellow}Executing chain.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/chain.sql
echo -e "${yellow}Executing null.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/null.sql
echo -e "${yellow}Executing amazon-sample.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/amazon-sample.sql
echo -e "${yellow}Executing add-server-fields.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-server-fields.sql
echo -e "${yellow}Executing add-sample-server.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-sample-server.sql
echo -e "${yellow}Executing update-icons-2.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/update-icons-2.sql
echo -e "${yellow}Executing resource-based-dust-renderer.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/resource-based-dust-renderer.sql
echo -e "${yellow}Executing add-server-stats.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-server-stats.sql
echo -e "${yellow}Executing class-rename.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/class-rename.sql
echo -e "${yellow}Executing add-icons-table.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-icons-table.sql
echo -e "${yellow}Executing update-icons-3.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/update-icons-3.sql
echo -e "${yellow}Executing add-auth-tables.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-auth-tables.sql
echo -e "${yellow}Executing add-resources-table.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-resources-table.sql
echo -e "${yellow}Executing add-external-id-server.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-external-id-server.sql
echo -e "${yellow}Executing add-server-command.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-server-command.sql
echo -e "${yellow}Executing add-profiling-info.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-profiling-info.sql
echo -e "${yellow}Executing optimize-resource-table.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/optimize-resource-table.sql
echo -e "${yellow}Executing extend-resource-blob-type.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/extend-resource-blob-type.sql
echo -e "${yellow}Executing initial-resources.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/initial-resources.sql
echo -e "${yellow}Executing update-resources-table.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/update-resources-table.sql
echo -e "${yellow}Executing add-logging-info.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/add-logging-info.sql

echo -e "${yellow}Executing hello-world-sample.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/hello-world-sample.sql
echo -e "${yellow}Executing update-dust-renderer-example-flow.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.1/update-dust-renderer-example-flow.sql
echo -e "${yellow}Executing add-auth-tables2.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.3/add-auth-tables2.sql

#v0.2

echo -e "${yellow}Executing update-snapshot-table.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.2/update-snapshot-table.sql

#v0.3

echo -e "${yellow}Executing refactor-environment.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.3/refactor-environment.sql
echo -e "${yellow}Executing add-connectors-table.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.3/add-connectors-table.sql
echo -e "${yellow}Executing for-each-router.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.3/for-each-router.sql
echo -e "${yellow}Executing add-connector-definition.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/migration/0.3/add-connector-definition.sql

echo -e "${green}Done recreating schema!${reset}";

echo -e "${green}Adding some data${reset}";

echo -e "${yellow}Executing lifia-new-site.sql${reset}";
mysql -h $HOST -u $USER $PASS $DATABASE < $BASEDIR/data/lifia-new-site.sql

echo -e "${green}DONE Adding some data${reset}";
