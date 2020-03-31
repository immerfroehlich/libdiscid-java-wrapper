#include <stdlib.h>
#include <jni.h>
#include <discid/discid.h>


JNIEXPORT jstring JNICALL Java_de_immerfroehlich_discid_DiscIdCalculator_calculate
  (JNIEnv *env, jobject obj, jstring jdevicePath) {
	
	DiscId drive_disc;	/* libdiscid disc object */
	char *disc_id = "";	/* tha actual disc ID */
	const char *drive = (*env)->GetStringUTFChars(env, jdevicePath, 0);
	
	drive_disc = discid_new();
	if (!discid_read_sparse(drive_disc, drive, 0)) {
		discid_free(drive_disc);
		jstring jstring_disc_id = (*env)->NewStringUTF(env, disc_id);
		return jstring_disc_id;
	}
	disc_id = discid_get_id(drive_disc);
	
	//JNI method
	jstring jstring_disc_id = (*env)->NewStringUTF(env, disc_id);
	
	discid_free(drive_disc);
	//free(disc_id);
	//free(drive);
	
	return jstring_disc_id;
	
}
