# NOTE: must be run as run/query.sh

LIB_PATH=native
CLASSPATH=build:lib/benow-launch.jar
CLASS=test.org.musicbrainz.discid.Query

if [ $0 != 'run/query.sh' ]; then
  echo Please run from within the project root directory.  ie: run/query.sh
  exit -2;
fi;
echo Running as:
echo java -Djava.library.path=$LIB_PATH:/usr/lib -cp $CLASSPATH $CLASS $*
java -Djava.library.path=$LIB_PATH:/usr/lib -cp $CLASSPATH $CLASS $*

