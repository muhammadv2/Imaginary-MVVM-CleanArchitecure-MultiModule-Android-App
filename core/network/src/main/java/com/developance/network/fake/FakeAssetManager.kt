package com.developance.network.fake

import java.io.BufferedReader
import java.io.InputStream

fun interface FakeAssetManager {
    fun open(fileName: String): BufferedReader
}
