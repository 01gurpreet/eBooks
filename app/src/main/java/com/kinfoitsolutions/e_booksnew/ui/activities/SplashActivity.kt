package com.kinfoitsolutions.e_booksnew.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kinfoitsolutions.e_booksnew.AppConstants
import com.kinfoitsolutions.e_booksnew.R
import com.orhanobut.hawk.Hawk
import java.lang.Exception

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)


        val timerThread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(2*1000)

                    if (Hawk.get(AppConstants.TOKEN, "") == "") {
                        val i = Intent(applicationContext, LoginActivity::class.java)
                        startActivity(i)
                        overridePendingTransition(0,0)
                        finish()
                    } else {
                        val i = Intent(applicationContext, HomeActivity::class.java)
                        startActivity(i)
                        overridePendingTransition(0,0)
                        finish()
                    }


                } catch (i: Exception) {
                    i.printStackTrace()
                }
            }
        }
        timerThread.start()
    }
}


