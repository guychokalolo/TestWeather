package com.gishokalolo.testexpr.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.gishokalolo.testexpr.utils.Constants
import com.gishokalolo.testexpr.utils.connectivityManager
import com.gishokalolo.testexpr.utils.isGreaterThanM
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherInterceptor @Inject constructor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("access_key", Constants.API_KEY).build()
        val request = chain.request().newBuilder().url(url).build()
        if (!isInternetAvailable()) {
            throw Exception()
        }
        return chain.proceed(request)
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        if (isGreaterThanM()) {
            val networkCapabilities = context.connectivityManager.activeNetwork ?: return false
            val actNw = context.connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            context.connectivityManager.run {
                context.connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }
}