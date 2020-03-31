LIB_PATH=native
CLASSPATH=build
CLASS=test.ca.benow.jni.LinkerTest
#VERBOSE= -verbose:jni -verbose

if [ $0 != 'run/linkerTest.sh' ]; then
  echo Please run from within the project root directory.  ie: run/linkerTest.sh
  exit -2;
fi;

echo Running: java $VERBOSE -Djava.library.path=$LIB_PATH:/usr/lib -cp $CLASSPATH $CLASS $*
java $VERBOSE -Djava.library.path=$LIB_PATH:/usr/lib -cp $CLASSPATH $CLASS $*

