package com.kinfoitsolutions.e_booksnew.ui

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kinfoitsolutions.e_booksnew.service.NetworkChangeReceiver

open class BaseActivity: AppCompatActivity() {


    internal lateinit var intentFilter: IntentFilter
    internal lateinit var receiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intentFilter = IntentFilter()
        intentFilter.addAction(CONNECTIVITY_ACTION)
        receiver = NetworkChangeReceiver()

    }



    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, intentFilter)
    }



    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }


    fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }


}

