package com.webtechsolution.ghumantey.app

import com.webtechsolution.ghumantey.BuildConfig

object Config {
    const val okHttpCacheSizeMB: Long = 10 * 1024 * 1024
    const val okHttpReadTimeOut: Long = 60
    const val okHttpConnectionTimeOut: Long = 30
    const val okHttpWriteTimeOut: Long = 30
    const val baseUrl: String = "http://192.168.1.142:3000/"

    const val DATABASE_NAME = "${BuildConfig.APPLICATION_ID}.room.db"
    const val DATABASE_VERSION = 1
}