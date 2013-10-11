BASEDIR=$(dirname $0)

rm -R $BASEDIR/../../modules/actions-editor/files;
mkdir $BASEDIR/../../modules/actions-editor/files;
cp -R $BASEDIR/2 $BASEDIR/../../modules/actions-editor/files;
