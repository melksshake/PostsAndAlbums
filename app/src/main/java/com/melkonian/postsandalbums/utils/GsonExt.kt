package com.melkonian.postsandalbums.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.FileNotFoundException

suspend inline fun <reified T> Gson.fromAssets(context: Context, fileName: String): T {
    Timber.d("Use mock API file: $fileName")
    val json = withContext(Dispatchers.IO) {
        try {
            context.resources.assets.open(fileName)
                .bufferedReader().use { it.readText() }
        } catch (t: FileNotFoundException) {
            Timber.e(t)
            null
        }
    }
    return fromJson(json, object : TypeToken<T>() {}.type)
}