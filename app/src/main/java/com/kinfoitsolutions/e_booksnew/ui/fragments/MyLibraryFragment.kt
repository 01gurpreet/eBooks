package com.kinfoitsolutions.e_booksnew.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.kinfoitsolutions.e_booksnew.AppConstants
import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.adapters.CategoryBooksRecycleAdapter
import com.kinfoitsolutions.e_booksnew.adapters.LibraryBookAdapter
import com.kinfoitsolutions.e_booksnew.response.CategoryResponse.CategorySuccess
import com.kinfoitsolutions.e_booksnew.response.LibraryResponse.GetBookLibraryResponse
import com.kinfoitsolutions.e_booksnew.restclient.RestClient
import com.kinfoitsolutions.e_booksnew.ui.BaseFragment
import com.kinfoitsolutions.e_booksnew.ui.activities.HomeActivity
import com.kinfoitsolutions.e_booksnew.util.Utils
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_category.view.*
import kotlinx.android.synthetic.main.fragment_mylibrary.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyLibraryFragment : BaseFragment() {


    internal lateinit var viewOfLayout: View
    internal var title: TextView? = null
    internal var context: Context? = null

    private lateinit var libbookadapter: LibraryBookAdapter

    override fun provideYourFragmentView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewOfLayout = inflater.inflate(R.layout.fragment_mylibrary, parent, false)
        context = activity

        val mainActivity = activity as HomeActivity
        mainActivity.setToolbarTittle("My Library")

        mainActivity.hideSearchImg()


        val circularProgressBar = viewOfLayout.findViewById<View>(R.id.circle_progress) as CircularProgressBar
        circularProgressBar.color = ContextCompat.getColor(context!!, R.color.green)
        circularProgressBar.backgroundColor = ContextCompat.getColor(context!!, R.color.grey)
        circularProgressBar.progressBarWidth = resources.getDimension(R.dimen.circle_width)
        circularProgressBar.backgroundProgressBarWidth = resources.getDimension(R.dimen.circle_width)
        val animationDuration = 2500 // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(65f, animationDuration) // Default duration = 1500ms
        val layoutManager = GridLayoutManager(context, 3)
        viewOfLayout.library_books_list.setLayoutManager(layoutManager)
        viewOfLayout.library_books_list.setItemAnimator(DefaultItemAnimator())

        getLibBooks()
        return viewOfLayout
    }

    private fun getLibBooks() {

        if (isNetworkConnected(context!!)) {

            val myDialog = Utils.showProgressDialog(context, "Please wait......")

            val restClient = RestClient.getClient()

            val stringHashMap = HashMap<String, String>()
            stringHashMap.put("token",Hawk.get(AppConstants.TOKEN))

            restClient.getbookslib(stringHashMap).enqueue(object : Callback<GetBookLibraryResponse> {

                override fun onResponse(
                    call: Call<GetBookLibraryResponse>,
                    response: Response<GetBookLibraryResponse>
                ) {

                    if (response.isSuccessful) {

                        if (response.body()!!.code.equals("100")) {

                            val allLibBooks = response.body()!!.books

                            libbookadapter = LibraryBookAdapter(context, allLibBooks)
                            viewOfLayout.library_books_list.setAdapter(libbookadapter)
                            libbookadapter.notifyDataSetChanged()

                            myDialog.dismiss()

                        } else {
                            Utils.showSnackBar(context, response.body()!!.msg, activity!!.main_container)
                            myDialog.dismiss()

                        }


                    } else if (response.code() == 401) {
                        // Handle unauthorized
                        Utils.showSnackBar(context, "Unauthorized", activity!!.main_container)
                        myDialog.dismiss()


                    } else if (response.code() == 500) {
                        // Handle unauthorized

                        Utils.showSnackBar(context, "Server Error", activity!!.main_container)
                        myDialog.dismiss()


                    } else {
                        //response is failed
                        Utils.showSnackBar(context, "Failed", activity!!.main_container)
                        myDialog.dismiss()


                    }
                }

                override fun onFailure(call: Call<GetBookLibraryResponse>, t: Throwable) {

                    Utils.showSnackBar(context, t.toString(), activity!!.main_container)
                    myDialog.dismiss()

                }
            })


        } else {
            showSnackBarFrag("Check your internet connection", activity!!.main_container)

        }

    }


}
