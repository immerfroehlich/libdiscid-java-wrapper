#include <stdio.h>
#include <jni.h>
 	 	
/*
 * Class:     test_org_xiph_libshout_LinkerTest
 * Method:    test
 * Signature: ()V
 */
/*
 * Class:     test_ca_benow_jni_LinkerTest
 * Method:    test
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_test_ca_benow_jni_LinkerTest_test
  (JNIEnv *env, jobject obj) {
	printf("It works!\n");
	return;
}
