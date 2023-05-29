package com.gishokalolo.testexpr.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun isGreaterThanM(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}