# libdiscid-java makefile

# NOTE: if you are getting jni.h not found errors, ensure to run
# via ant (ie ant native) and not via 'make'.  If you want to run directly
# with make, you'll have to define the following variables


# v-- these properties are passed in by the ant build script.  should work fine with standard jvms --v
JAVADIR=${java.home}
OSNAME=${os.name}
ARCHNAME=${os.arch}
LIBDISCID_PREFIX=/usr
#relative to source path
JAVACLASSDIR=../../${java.classes}



CC=gcc
CPP_INCLUDES=-I$(JAVADIR)/include -I$(JAVADIR)/include/$(OSNAME) -I${LIBDISCID_PREFIX}/include
#RM_F=rm -f
RM=rm
MKDIR_P=mkdir -p

LIBBASENAME=libdiscid-java
LIBDEST=$(LIBBASENAME).so.1.0
#LIBDEST_ABSOLUTE=$(ABS_PREFIX)/$(LIBDEST)

LIBS=-ldiscid

#JAVACLASSDIR=../../build
JAVAH = javah -force -classpath $(JAVACLASSDIR)

#JNIOBJ=\
#	org_musicbrainz_discid_libdiscid.o

#OBJECTS=$(JNIOBJ) 

#JNICLASSFILES=\
#	LibDiscId.class

JNIHEADERS:=$(JNIOBJ:.o=.h)

JNICLASSES=\
	de.immerfroehlich.discid.DiscIdCalculator
	
BUILDPATH=build/$(OSNAME)/$(ARCHNAME)
SOURCEPATH=src

#----------------- rules -------------------

all: simple

#jni:
  

simple:
	echo Using JAVA_HOME=$(JAVADIR)
	$(MKDIR_P) $(BUILDPATH)
#	Each line is executed in a new shell so use && if commands needs to be executed in the same shell
# javah not needed anymore	cd $(SOURCEPATH) && $(JAVAH) -d . $(JNICLASSES)
	$(CC) -Wall -Wstrict-prototypes -fpic -g -shared \
	$(CPP_INCLUDES) \
	-o $(BUILDPATH)/libdiscid-java.so $(SOURCEPATH)/de_immerfroehlich_discid_DiscIdCalculator.c \
	$(LIBS)
	echo Successfully compiled to $(BUILDPATH)

clean:
	echo Doing cleanup
#	Cleaning header files generated by javah
	$(RM) $(SOURCEPATH)/*.h
	
	$(RM) -r $(BUILDPATH)
	

#test:
#	gcc -Wall -Wstrict-prototypes $(ARCH) -fpic -g -shared $(CPP_INCLUDES) $(SOURCEPATH)/test_ca_benow_jni_LinkerTest.c -o $(BUILDPATH)/liblinkertest.so
#	cd ..; java -cp build:libdiscid-java.jar -Djava.library.path=native/$(BUILDPATH) test.ca.benow.jni.LinkerTest

