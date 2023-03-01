#include "secrets.hpp"

#include <jni.h>

#include "sha256.hpp"
#include "sha256.cpp"

/* Copyright (c) 2020-present Klaxit SAS
*
* Permission is hereby granted, free of charge, to any person
* obtaining a copy of this software and associated documentation
* files (the "Software"), to deal in the Software without
* restriction, including without limitation the rights to use,
* copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the
* Software is furnished to do so, subject to the following
* conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
* OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
* HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
* OTHER DEALINGS IN THE SOFTWARE.
*/

char *customDecode(char *str) {
    /* Add your own logic here
    * To improve your key security you can encode it before to integrate it in the app.
    * And then decode it with your own logic in this function.
    */
    return str;
}

jstring getOriginalKey(
        char *obfuscatedSecret,
        int obfuscatedSecretSize,
        jstring obfuscatingJStr,
        JNIEnv *pEnv) {

    // Get the obfuscating string SHA256 as the obfuscator
    const char *obfuscatingStr = pEnv->GetStringUTFChars(obfuscatingJStr, NULL);
    char buffer[2 * SHA256::DIGEST_SIZE + 1];

    sha256(obfuscatingStr, buffer);
    const char *obfuscator = buffer;

    // Apply a XOR between the obfuscated key and the obfuscating string to get original string
    char out[obfuscatedSecretSize + 1];
    for (int i = 0; i < obfuscatedSecretSize; i++) {
        out[i] = obfuscatedSecret[i] ^ obfuscator[i % strlen(obfuscator)];
    }

    // Add string terminal delimiter
    out[obfuscatedSecretSize] = 0x0;

    // (Optional) To improve key security
    return pEnv->NewStringUTF(customDecode(out));
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_developance_imaginary_Secrets_getEYjTFlEW(
        JNIEnv *pEnv,
        jobject pThis,
        jstring packageName) {
    char obfuscatedSecret[] = { 0x53, 0xa, 0x4f, 0x61, 0x6, 0x2f, 0x45, 0x62, 0x37, 0x66, 0xd, 0x48, 0x4d, 0x0, 0x1, 0x4d, 0x41, 0x2e, 0x62, 0x2b, 0xa, 0x0, 0x7, 0x72, 0x78, 0x6d, 0x9, 0x14, 0x75, 0x5a, 0x33, 0x66, 0x10, 0x20, 0x54, 0x7, 0x7e, 0x76, 0x42, 0x4a, 0x7d, 0xa, 0x5d };
    return getOriginalKey(obfuscatedSecret, sizeof(obfuscatedSecret), packageName, pEnv);
}