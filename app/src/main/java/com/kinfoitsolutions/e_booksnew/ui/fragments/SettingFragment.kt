package com.kinfoitsolutions.e_booksnew.ui.fragments

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kinfoitsolutions.e_booksnew.AppConstants
import com.kinfoitsolutions.e_booksnew.BuildConfig
import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.ui.BaseFragment
import com.kinfoitsolutions.e_booksnew.ui.activities.HomeActivity
import com.orhanobut.hawk.Hawk
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*

import com.kinfoitsolutions.e_booksnew.response.Logout.LogoutResponse
import com.kinfoitsolutions.e_booksnew.restclient.RestClient
import com.kinfoitsolutions.e_booksnew.ui.activities.HelpActivity
import com.kinfoitsolutions.e_booksnew.ui.activities.LoginActivity
import com.kinfoitsolutions.e_booksnew.util.Utils
import com.kinfoitsolutions.e_booksnew.util.Utils.showSnackBar
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response






class SettingFragment : BaseFragment() {


    internal lateinit var viewOfLayout: View
    private lateinit var userName: String
    private lateinit var userImage: String

    override fun provideYourFragmentView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        viewOfLayout = inflater.inflate(R.layout.fragment_setting, parent, false)


        val mainActivity = activity as HomeActivity
        mainActivity.setToolbarTittle("Settings")

        mainActivity.hideSearchImg()


        userName = Hawk.get(AppConstants.USER_NAME,"")

        viewOfLayout.txtUserName.setText(userName.toString())

        userImage = Hawk.get(AppConstants.USER_IMAGE,"")


        try {
            Picasso.get().load(userImage).fit()
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(viewOfLayout.profile_image)
        } catch (e: Exception) {
            e.printStackTrace()
            viewOfLayout.profile_image.setImageResource(R.drawable.no_image)
        }

        viewOfLayout.logout.setOnClickListener {
            createalert()
        }

        viewOfLayout.share_friends.setOnClickListener {
            shareAppCode()
        }

        viewOfLayout.rateus.setOnClickListener {
            rateAppCode()
        }

        viewOfLayout.facebook.setOnClickListener {
            faceBookPage()
        }

        viewOfLayout.helpcenter.setOnClickListener {
            helpCenterPage()
        }

        return viewOfLayout



    }

    private fun helpCenterPage() {
        startActivity(Intent(context,HelpActivity::class.java))

    }

    private fun faceBookPage() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/apple.fruit"))
        startActivity(browserIntent)

    }

    private fun rateAppCode() {
        val uri = Uri.parse("market://details?id=" + context!!.packageName)
        val goToMarket =Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)

        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + context!!.packageName)))
        }
    }

    private fun shareAppCode() {
        try {
            val shareIntent =Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"E-Books")
            var shareMesssage ="\nLet me recommend you this application\n\n"
            shareMesssage = shareMesssage + "https://play.google.com/store/apps/details?id=" +BuildConfig.APPLICATION_ID + "\n\n"
            shareIntent.putExtra(Intent.EXTRA_TEXT,shareMesssage)
            startActivity(Intent.createChooser(shareIntent,"choose one"))
        }catch (e: Exception){
            e.toString()
        }
    }

    private fun createalert() {
        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage("Are you sure you want to logout?")
        builder1.setCancelable(false)

        builder1.setPositiveButton("Yes") { dialog, id ->

            if (isNetworkConnected(context!!)){
                logoutApi()
                dialog.cancel()
            }
            else{
                showSnackBar(context,"Check your internet connection",activity!!.main_container)

            }

        }

        builder1.setNegativeButton(
            "No"
        ) { dialog, id -> dialog.cancel() }

        val alert11 = builder1.create()
        alert11.show()

    }

    private fun logoutApi() {
        val myDialog = Utils.showProgressDialog(context, "Progressing......")

        val stringHashMap = HashMap<String, String>()
        stringHashMap.put("token", Hawk.get(AppConstants.TOKEN))

        val restClient = RestClient.getClient()

        restClient.logout(stringHashMap).enqueue(object : Callback<LogoutResponse> {


            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {

                if (response.isSuccessful) {

                    if (response.body()!!.code.equals(100)) {

                        myDialog.dismiss()
                        Hawk.delete(AppConstants.TOKEN)
                        val i = Intent(context, LoginActivity::class.java)
                        i.putExtra(AppConstants.OPEN_LOGIN_FRAG, 1)
                        startActivity(i)
                        Utils.showSnackBar(context, "Success", activity!!.main_container)

                    } else if (response.code() == 401) {
                        // Handle unauthorized
                        Utils.showSnackBar(context, "Unauthorized", activity!!.main_container)

                    } else if (response.code() == 500) {
                        // Handle unauthorized
                        Utils.showSnackBar(context, "Server Error", activity!!.main_container)

                    } else {
                        //code 101 invalid credentials
                        Utils.showSnackBar(context, "Failed", activity!!.main_container)
                        myDialog.dismiss()

                    }


                } else {

                    //response is failed
                    Utils.showSnackBar(context, "Api Failed", activity!!.main_container)
                    myDialog.dismiss()


                }

            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                Utils.showSnackBar(context, t.toString(), activity!!.main_container)
                myDialog.dismiss()

            }


        })
    }


}
