/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <jni.h>

#include <unistd.h>
#include <sys/mman.h>

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   apps/samples/hello-jni/project/src/com/example/HelloJni/HelloJni.java
 */
 

 
#include <linux/limits.h>
#include <linux/ioctl.h>

#define ASHMEM_NAME_LEN 256

#define ASHMEM_NAME_DEF "dev/ashmem"

#define ASHMEM_NOT_PURGED 0
#define ASHMEM_WAS_PURGED 1

#define ASHMEM_IS_UNPINNED 0
#define ASHMEM_IS_PINNED 1

struct ashmem_pin {
 __u32 offset;
 __u32 len;
};

#define __ASHMEMIOC 0x77

#define ASHMEM_SET_NAME _IOW(__ASHMEMIOC, 1, char[ASHMEM_NAME_LEN])
#define ASHMEM_GET_NAME _IOR(__ASHMEMIOC, 2, char[ASHMEM_NAME_LEN])
#define ASHMEM_SET_SIZE _IOW(__ASHMEMIOC, 3, size_t)
#define ASHMEM_GET_SIZE _IO(__ASHMEMIOC, 4)
#define ASHMEM_SET_PROT_MASK _IOW(__ASHMEMIOC, 5, unsigned long)
#define ASHMEM_GET_PROT_MASK _IO(__ASHMEMIOC, 6)
#define ASHMEM_PIN _IOW(__ASHMEMIOC, 7, struct ashmem_pin)
#define ASHMEM_UNPIN _IOW(__ASHMEMIOC, 8, struct ashmem_pin)
#define ASHMEM_GET_PIN_STATUS _IO(__ASHMEMIOC, 9)
#define ASHMEM_PURGE_ALL_CACHES _IO(__ASHMEMIOC, 10)
static struct {
    jfieldID    descriptor;       /* int */
    jmethodID   constructorInt;
    jmethodID   setFD;
    jclass      clazz;
} gCachedFields;

 inline int getFd(JNIEnv* env, jobject obj)
{
    return (*env)->GetIntField(env, obj, gCachedFields.descriptor);
}

jstring
Java_edu_ucla_cens_ipc_MemoryShareSender_stringFromJNI( JNIEnv* env,
                                                  jobject thiz )
{
    return (*env)->NewStringUTF(env, "Hello from JNI !");
}
jint
Java_edu_ucla_cens_ipc_MemoryShareSender_pinAndWrite(JNIEnv* env, jobject clazz,
        jobject fileDescriptor, jobject memoryFile, jbyteArray buffer, jint srcOffset, jint destOffset,
        jint count)
{
    jclass fdClass = (*env)->FindClass(env,"java/io/FileDescriptor");
    jfieldID fdClassDescriptorFieldID = (*env)->GetFieldID(env,fdClass,
    "descriptor", "I");
    jclass mfClass = (*env)->FindClass(env,"android/os/MemoryFile");
    jfieldID addressFieldID = (*env)->GetFieldID(env, mfClass,
    "mAddress", "I");
    

    int fd =(*env)->GetIntField(env,fileDescriptor, fdClassDescriptorFieldID);
    int address =(*env)->GetIntField(env,memoryFile, addressFieldID);
    struct ashmem_pin pin = { 0, count };
    ioctl(fd, ASHMEM_PIN, &pin);
    (*env)->GetByteArrayRegion(env, buffer, srcOffset, count, (jbyte *)address + destOffset);
    return count;
}
jint
Java_edu_ucla_cens_ipc_MemoryShareReceiver_readAndUnpin(JNIEnv* env, jobject clazz,
        jobject fileDescriptor, jobject memoryFile, jbyteArray buffer, jint srcOffset, jint destOffset,
        jint count)
{
    
    jclass fdClass = (*env)->FindClass(env,"java/io/FileDescriptor");
    jfieldID fdClassDescriptorFieldID = (*env)->GetFieldID(env,fdClass,
    "descriptor", "I");
    jclass mfClass = (*env)->FindClass(env,"android/os/MemoryFile");
    jfieldID addressFieldID = (*env)->GetFieldID(env, mfClass,
    "mAddress", "I");
    

    
    int fd =(*env)->GetIntField(env,fileDescriptor, fdClassDescriptorFieldID);
    int address =(*env)->GetIntField(env,memoryFile, addressFieldID);
    (*env)->SetByteArrayRegion(env,buffer, destOffset, count, (jbyte *)address + srcOffset);
        //ashmem_unpin_region(fd, 0, 0);
    struct ashmem_pin pin = { 0, count};
    ioctl(fd, ASHMEM_UNPIN, &pin);
    return count;
}