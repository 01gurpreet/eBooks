package com.kinfoitsolutions.e_booksnew.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kinfoitsolutions.e_booksnew.AppConstants
import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.adapters.CategoryBooksRecycleAdapter
import com.kinfoitsolutions.e_booksnew.models.CategorySelectModelClass
import com.kinfoitsolutions.e_booksnew.response.CategoryResponse.CategorySuccess
import com.kinfoitsolutions.e_booksnew.restclient.RestClient
import com.kinfoitsolutions.e_booksnew.ui.BaseFragment
import com.kinfoitsolutions.e_booksnew.ui.activities.HomeActivity
import com.kinfoitsolutions.e_booksnew.util.Utils
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_category.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList

class CategoryFragment : BaseFragment() {

    internal lateinit var viewOfLayout: View
    internal lateinit var context: Context
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryAdapter: CategoryBooksRecycleAdapter
    private lateinit var cateNameArray: ArrayList<String>



    override fun provideYourFragmentView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        viewOfLayout = inflater.inflate(R.layout.fragment_category, parent, false)
        context = activity!!

        val mainActivity = activity as HomeActivity
        mainActivity.setToolbarTittle("Category")

        mainActivity.hideSearchImg()
        cateNameArray = ArrayList<String>()

        val layoutManager = GridLayoutManager(context, 2)
        viewOfLayout.recyclerViewCat.layoutManager = layoutManager
        viewOfLayout.recyclerViewCat.itemAnimator = DefaultItemAnimator()

        getBooksByCat()

        return viewOfLayout


    }


    private fun getBooksByCat() {

        if (isNetworkConnected(context!!)) {

            val myDialog = Utils.showProgressDialog(context, "Please wait......")

            val restClient = RestClient.getClient()

            val stringHashMap = HashMap<String, String>()
            stringHashMap.put("token", Hawk.get(AppConstants.TOKEN))

            restClient.getBooksByCat(stringHashMap).enqueue(object : Callback<CategorySuccess>,
                CategoryBooksRecycleAdapter.mCategoryClickRow {

                override fun mClick(v: View?, pos: Int) {

                }


                override fun onResponse(call: Call<CategorySuccess>, response: Response<CategorySuccess>) {

                    if (response.isSuccessful) {

                        if (response.body()!!.code.equals("100")) {

                            val allBooksCat = response.body()!!.categories

                            viewOfLayout.total_books.text = response.body()!!.totalBooks.toString()+ " Books"

                            categoryAdapter = CategoryBooksRecycleAdapter(context,allBooksCat,this)
                            viewOfLayout.recyclerViewCat.setAdapter(categoryAdapter)

                            categoryAdapter.notifyDataSetChanged()

                          //  Utils.showSnackBar(context, "Success", activity!!.main_container)
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

                override fun onFailure(call: Call<CategorySuccess>, t: Throwable) {

                    Utils.showSnackBar(context, t.toString(), activity!!.main_container)
                    myDialog.dismiss()

                }
            })


        } else {
            showSnackBarFrag("Check your internet connection", activity!!.main_container)

        }

    }

}
