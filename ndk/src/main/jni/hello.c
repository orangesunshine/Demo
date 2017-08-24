#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <jni.h>
#include <android/log.h>
#include <lame.h>
#include "com_orangesunshine_ndk_LameActivity.h"

#define LOG_TAG "orangesunshine"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG ,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG ,__VA_ARGS__)

int getRandom() {
    return rand();
}

char*   Jstring2CStr(JNIEnv*   env,   jstring   jstr)
{
char*   rtn   =   NULL;
jclass   clsstring   =   (*env)->FindClass(env,"java/lang/String");
jstring   strencode   =   (*env)->NewStringUTF(env,"GB2312");
jmethodID   mid   =   (*env)->GetMethodID(env,clsstring,   "getBytes",   "(Ljava/lang/String;)[B");
jbyteArray   barr=   (jbyteArray)(*env)->CallObjectMethod(env,jstr,mid,strencode); // String .getByte("GB2312");
jsize   alen   =   (*env)->GetArrayLength(env,barr);
jbyte*   ba   =   (*env)->GetByteArrayElements(env,barr,JNI_FALSE);
if(alen   >   0)
{
rtn   =   (char*)malloc(alen+1);         //"\0"
memcpy(rtn,ba,alen);
rtn[alen]=0;
}
(*env)->ReleaseByteArrayElements(env,barr,ba,0);  //
return rtn;
}

jint Java_com_orangesunshine_ndk_NdkActivity_getPress(JNIEnv *env, jobject obj) {
    int result = getRandom() % 300;
    LOGE("result: %d\n", result);
    return result;
}

JNIEXPORT void JNICALL Java_com_orangesunshine_ndk_LameActivity_convertMp3(JNIEnv * env, jobject jobj, jstring jwav, jstring jmp3){
    char* cwav = Jstring2CStr(env,jwav);
    char* cmp3 = Jstring2CStr(env,jmp3);
    LOGE("cwav: %s\n",cwav);
    LOGE("cmp3: %s\n",cmp3);
    FILE* fwav = fopen(cwav,"rb");
    FILE* fmp3 = fopen(cmp3,"wb");

    short int wav_buffer[8192*2];
    unsigned char mp3_buffer[8192];

    //1.初始化lame编码器
    lame_t lame = lame_init();

    //设置lame编码的采样率
    lame_set_in_samplerate(lame, 44100);
    lame_set_num_channels(lame,2);
    //设置设置lame的编码方式
    lame_set_VBR(lame, vbr_default);

    //设置参数
    lame_init_params(lame);
    LOGE("lame initial finish");
    int read;int write;
    do{
        LOGE("do");
        read = fread(wav_buffer, sizeof(short int)*2,8192,fwav);
        LOGE("read=%d\n",read);
        if(read != 0){
            LOGE("converting");
            write = lame_encode_buffer_interleaved(lame,wav_buffer,read,mp3_buffer,8192);
            fwrite(mp3_buffer , sizeof(unsigned char),8192,fmp3);
        }

        if(0 == read){
            lame_encode_flush(lame, mp3_buffer, 8192);
        }
    }while(read != 0);

    LOGE("convert finish");
    lame_close(lame);
    fclose(fmp3);
    fclose(fwav);
}

JNIEXPORT jstring JNICALL Java_com_orangesunshine_ndk_LameActivity_getLameVersion
        (JNIEnv * env, jobject jobj){
    LOGE("getLameVersion");
    return (*env)->NewStringUTF(env,get_lame_version());
}

