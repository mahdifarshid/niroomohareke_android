#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_farshid_api2_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_application_mahabad_niroomohareke_Activities_SplashScreen_stringFromJNI(JNIEnv *env,
                                                                                 jobject instance) {

    // TODO


    return env->NewStringUTF("kkii");
}