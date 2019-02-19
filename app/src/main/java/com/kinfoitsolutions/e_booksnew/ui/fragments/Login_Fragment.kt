package com.kinfoitsolutions.e_booksnew.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.ui.BaseFragment
import com.kinfoitsolutions.e_booksnew.ui.activities.HomeActivity

class Login_Fragment : BaseFragment() {


    internal lateinit var viewOfLayout: View


    override fun provideYourFragmentView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View {

        viewOfLayout = inflater.inflate(R.layout.fragment_login, parent, false)



        return viewOfLayout
    }



}
