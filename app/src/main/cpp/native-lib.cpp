#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_freaktemplate_shopping_MyApplication_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "shopjoin";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_freaktemplate_shopping_MyApplication_baseApiJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "https://freaktemplate.com/ecommerce/api/";
    return env->NewStringUTF(hello.c_str());
}


