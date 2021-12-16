package com.melkonian.postsandalbums

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PostsAlbumsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    private class ReleaseTree : Timber.DebugTree() {
        override fun isLoggable(tag: String?, priority: Int): Boolean {
            return priority >= Log.WARN
        }
    }

    private class DebugTree : Timber.DebugTree() {
        override fun isLoggable(tag: String?, priority: Int): Boolean {
            return super.isLoggable(tag, priority)
        }
    }
}