package com.drivingschool.android

import android.app.Application
import com.orhanobut.hawk.Hawk


class ApplicationClass: Application() {

    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).build()

    }


}