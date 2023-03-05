package com.developance.network.fake

import android.content.Context
import java.io.BufferedReader

class FakeAndroidTestAssetManager(private val context: Context) : FakeAssetManager {
    override fun open(fileName: String): BufferedReader =
        context.assets.open(fileName).bufferedReader()
}