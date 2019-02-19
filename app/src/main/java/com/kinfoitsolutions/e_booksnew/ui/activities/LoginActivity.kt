package com.kinfoitsolutions.e_booksnew.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

import com.kinfoitsolutions.e_booksnew.adapters.LoginPagerAdapter
import com.kinfoitsolutions.e_booksnew.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.kinfoitsolutions.e_booksnew.AppConstants
import com.orhanobut.hawk.Hawk
import com.kinfoitsolutions.e_booksnew.R
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.kinfoitsolutions.e_booksnew.response.LoginResponse.LoginResponse
import com.kinfoitsolutions.e_booksnew.restclient.RestClient
import com.kinfoitsolutions.e_booksnew.util.Utils
import com.kinfoitsolutions.e_booksnew.util.Utils.showSnackBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener {


    private lateinit var loginPagerAdapter: LoginPagerAdapter
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var email_g_login: String
    private lateinit var name_g_login: String
    private lateinit var google_id: String
    private lateinit var photo_url: String
    private val RC_SIGN_IN = 1

    private var mGoogleApiClient: GoogleApiClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        if (Build.VERSION.SDK_INT >= 21) {
            window.navigationBarColor = ContextCompat.getColor(
                this,
                R.color.orange
            ) // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            window.statusBarColor = ContextCompat.getColor(this, R.color.white) //status bar or the time bar at the top
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()


        btnLoginGoogle.setOnClickListener {
            signIn()
        }

        loginPagerAdapter = LoginPagerAdapter(supportFragmentManager)

        viewpager.setAdapter(loginPagerAdapter)

        indicator.setViewPager(viewpager)

        loginPagerAdapter.registerDataSetObserver(indicator.dataSetObserver)

    }


    override fun onConnectionFailed(@NonNull connectionResult: ConnectionResult) {}

    private fun goToWelcomeScreen(email_g_login: String, google_id: String, name_g_login: String) {



        if (isNetworkConnected()){

            val myDialog = Utils.showProgressDialog(this, "Progressing......")

            val stringHashMap = HashMap<String, String>()
            stringHashMap.put("email", email_g_login)
            stringHashMap.put("type", "2")
            stringHashMap.put("google_id", google_id)
            stringHashMap.put("name", name_g_login)

            val restClient = RestClient.getClient()

            restClient.login(stringHashMap).enqueue(object : Callback<LoginResponse> {

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                    if (response.isSuccessful) {

                        if (response.body()!!.code.equals(100)) {
                            Hawk.put(AppConstants.TOKEN, response.body()!!.token)
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                            myDialog.dismiss()

                        }

                        else if (response.code() == 401) {
                            // Handle unauthorized
                            Utils.showSnackBar(applicationContext, "Unauthorized", login_root_layout)
                            myDialog.dismiss()

                        }
                        else if (response.code() == 500) {
                            // Handle unauthorized
                            Utils.showSnackBar(applicationContext, "Server Error", login_root_layout)
                            myDialog.dismiss()

                        }

                        else {
                            //code 101 invalid credentials
                            Utils.showSnackBar(applicationContext, response.body()!!.msg, login_root_layout)
                            myDialog.dismiss()

                        }
                    }



                    else {
                        //response is failed
                        Utils.showSnackBar(applicationContext, response.body()!!.msg, login_root_layout)
                        myDialog.dismiss()

                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                    Utils.showSnackBar(applicationContext, t.toString(), login_root_layout)
                    myDialog.dismiss()


                }

            })

        }
        else{
            showSnackBar(this,"Check your internet connection",login_root_layout)

        }

    }



    private fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }


    private fun handleSignInResult(result: GoogleSignInResult) {
        Log.e("gmail", "handleSignInResult:" + result.isSuccess)
        if (result.isSuccess) {
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount

            Log.e("gmail", "display name: " + acct!!.displayName!!)

            if(acct.getPhotoUrl() == null){
                //set default image
                photo_url = ""

            } else {

                photo_url = acct.getPhotoUrl().toString(); //photo_url is String

            }

            email_g_login = acct.email!!
            name_g_login = acct.displayName!!
            google_id = acct.id!!

            Hawk.put(AppConstants.USER_IMAGE, photo_url)

            Log.e(
                "gmail", "Name: " + name_g_login + ", email: " + email_g_login
                        + ", Image: " + photo_url
            )    // User is signed in


            Hawk.put(AppConstants.USER_NAME, name_g_login)
            Hawk.put(AppConstants.USER_EMAIL, email_g_login)

            goToWelcomeScreen(email_g_login,google_id,name_g_login)

        } else {
            // Signed out, show unauthenticated UI.

        }
    }

}
