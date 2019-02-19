package com.kinfoitsolutions.e_booksnew.ui


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


class abstract abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanseState: Bundle?): View? {
        return provideYourFragmentView(inflater, parent, savedInstanseState)
    }

    abstract fun provideYourFragmentView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }


    fun showSnackBarFrag(message: String, main_container: View) {
        val snackbar = Snackbar.make(main_container, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }


}
