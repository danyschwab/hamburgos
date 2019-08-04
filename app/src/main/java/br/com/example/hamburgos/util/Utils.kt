package br.com.example.hamburgos.util

import android.content.Context
import android.net.ConnectivityManager

object Utils {

    fun isNetworkAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetworkInfo
        if (network != null) {
            result = network.isConnected
        }
        return result
    }
}
