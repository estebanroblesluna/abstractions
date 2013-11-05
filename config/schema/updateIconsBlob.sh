BASEDIR=$(dirname $0)

mysql -u root < $BASEDIR/migration/0.1/update-icons-3.sql
