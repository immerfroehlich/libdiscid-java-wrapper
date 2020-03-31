# NOTE: must be run as run\query.bat
set LIB_PATH=native\libdiscid-0.2.2-win32bin-mingw\bin;native\win32\x86
set CLASSPATH=build;lib/benow-launch.jar
set CLASS=test.org.musicbrainz.discid.Query
echo Running as:
echo java -Djava.library.path=%LIB_PATH% -cp %CLASSPATH% %CLASS% %1 %2 %3
java -Djava.library.path=%LIB_PATH% -cp %CLASSPATH% %CLASS% %1 %2 %3
