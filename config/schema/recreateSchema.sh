BASEDIR=$(dirname $0)

mysql -u root < $BASEDIR/common.sql
mysql -u root < $BASEDIR/actionsSchema.sql
mysql -u root < $BASEDIR/sample.sql