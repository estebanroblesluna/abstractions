BASEDIR=$(dirname $0)

rm -Rf $BASEDIR/../../modules/actions-editor/files;
mkdir $BASEDIR/../../modules/actions-editor/files;
cp -Rf $BASEDIR/2 $BASEDIR/../../modules/actions-editor/files;
