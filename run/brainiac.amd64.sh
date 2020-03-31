# NOTE: must be run as run/query.sh

LIB_PATH=native/linux/amd64
CLASSPATH=build:lib/benow-launch.jar:lib/java/xerces.jar:lib/java/log4j.jar
CLASS=org.musicbrainz.ws.MusicBrainiac

if [ $0 != 'run/brainiac.amd64.sh' ]; then
  echo Please run from within the project root directory.  ie: run/query.amd64.sh
  exit -2;
fi;
echo Running as:
echo java -Djava.library.path=$LIB_PATH:/usr/lib -cp $CLASSPATH $CLASS $*
java -Djava.library.path=$LIB_PATH:/usr/lib -cp $CLASSPATH $CLASS $*

