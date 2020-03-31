# NOTE: must be run as run/query.sh

LIB_PATH=native/linux/amd64
CLASSPATH=build
CLASS=test.de.immerfroehlich.discid.Main

if [ $0 != 'run/discid.amd64.sh' ]; then
  echo Please run from within the project root directory.  ie: run/discid.amd64.sh
  exit -2;
fi;
echo Running as:
#echo java -Djava.library.path=$LIB_PATH:/usr/lib/x86_64-linux-gnu -cp $CLASSPATH $CLASS $*
#java -Djava.library.path=$LIB_PATH:/usr/lib/x86_64-linux-gnu -cp $CLASSPATH $CLASS $*

echo java -Djava.library.path=$LIB_PATH -cp $CLASSPATH $CLASS $*
java -Djava.library.path=$LIB_PATH -cp $CLASSPATH $CLASS $*