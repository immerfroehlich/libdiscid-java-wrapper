set LIB_PATH=native
set CLASSPATH=build
set CLASS=test.ca.benow.jni.LinkerTest
#VERBOSE= -verbose:jni -verbose

echo Running: java %VERBOSE% -Djava.library.path=%LIB_PATH% -cp %CLASSPATH% %CLASS% %1 %2 %3 %4
java %VERBOSE% -Djava.library.path=%LIB_PATH% -cp %CLASSPATH% %CLASS% %1 %2 %3 %4

