package com.ragini.artistdataitunes.util

import android.util.Log

object Logger {

    fun printLog(tagName: String, message: String? = "NULL value") {
        Log.d(tagName, message + "")
    }
}