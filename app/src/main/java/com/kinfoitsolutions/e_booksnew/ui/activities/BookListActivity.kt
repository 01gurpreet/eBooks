package com.kinfoitsolutions.e_booksnew.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinfoitsolutions.e_booksnew.AppConstants
import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.adapters.BookListRecycleAdapter
import com.kinfoitsolutions.e_booksnew.adapters.Top50Adapter
import com.kinfoitsolutions.e_booksnew.adapters.Top50ListAdapter
import com.kinfoitsolutions.e_booksnew.response.FilterResponse.FilterPayload
import com.kinfoitsolutions.e_booksnew.response.GetAllBooksReponse.GetAllBooksSuccess
import com.kinfoitsolutions.e_booksnew.response.GetAllBooksReponse.RecommendedPayload
import com.kinfoitsolutions.e_booksnew.restclient.RestClient
import com.kinfoitsolutions.e_booksnew.ui.BaseActivity
import com.kinfoitsolutions.e_booksnew.util.Utils
import com.kinfoitsolutions.e_booksnew.util.Utils.showSnackBar
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_book_list.*
import kotlinx.android.synthetic.main.toolbar_booklist.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class BookListActivity : BaseActivity() {

    private lateinit var bAdapter: BookListRecycleAdapter
    private lateinit var top50ListAdapter: Top50ListAdapter
    private lateinit var type: String

    private var recomSize: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)


        val layoutManager = LinearLayoutManager(this@BookListActivity)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.setItemAnimator(DefaultItemAnimator())

        backBtn.setOnClickListener {
            finish()
        }

        filterClick.setOnClickListener {

            startActivity(Intent(this,EbooksFilterActivity::class.java))
            Hawk.put(AppConstants.BOOKS_SIZE,recomSize.toString())
        }



        if( getIntent().getExtras() != null)
        {
            //do here
            try {
                type  = getIntent().getStringExtra("key")
            } catch (e: Exception) {
            }

        }

        try {
            if (type.equals("top50")){
                bookListTitle.setText("Top 50 Books")
            }
        } catch (e: Exception) {
        }


        viewAllBooksApi(type)

    }

    private fun viewAllBooksApi(type: String) {

        if (isNetworkConnected()) {

            val myDialog = Utils.showProgressDialog(this, "Please wait......")

            val restClient = RestClient.getClient()

            val stringHashMap = HashMap<String, String>()
            stringHashMap.put("token", AppConstants.TOKEN)

            restClient.get_books(stringHashMap).enqueue(object : Callback<GetAllBooksSuccess>,
                BookListRecycleAdapter.mRecommendedBookClickListener, Top50ListAdapter.mTop50ListClickListener {

                override fun mClickList50(v: View?, pos: Int,
                                          bookName: String?,
                                          bookFile: String?,
                                          bookImage: String?,
                                          authorName: String?) {

                    Hawk.put(AppConstants.PDF_FILE, bookFile)
                    Hawk.put(AppConstants.BOOK_IMAGE, bookImage)
                    Hawk.put(AppConstants.BOOK_NAME, bookName)
                    Hawk.put(AppConstants.AUTHOR_NAME, authorName)

                    val intent = Intent(this@BookListActivity,BookDetailActivity::class.java)
                    startActivity(intent)

                }


                override fun mClickRecommeded(v: View?, pos: Int,
                                              bookName: String?,
                                              bookFile: String?,
                                              bookImage: String?,
                                              authorName: String?) {


                    Hawk.put(AppConstants.PDF_FILE, bookFile)
                    Hawk.put(AppConstants.BOOK_IMAGE, bookImage)
                    Hawk.put(AppConstants.BOOK_NAME, bookName)
                    Hawk.put(AppConstants.AUTHOR_NAME, authorName)
                    val intent = Intent(this@BookListActivity,BookDetailActivity::class.java)
                    intent.putExtra(AppConstants.BOOK_NAME,bookName)
                    startActivity(intent)


                }


                override fun onResponse(call: Call<GetAllBooksSuccess>, response: Response<GetAllBooksSuccess>) {

                    if (response.isSuccessful) {

                        if (response.body()!!.code.equals("100")) {


                            val allBooksRecommend = response.body()!!.recommended
                            val allBooksTop50 = response.body()!!.top50

                            if (type.equals("recommended")){

                                recomSize = allBooksRecommend.size

                                bAdapter = BookListRecycleAdapter(this@BookListActivity, allBooksRecommend,this)
                                recyclerView.setAdapter(bAdapter)
                                bAdapter.notifyDataSetChanged()


                            }
                            else{

                                recomSize = allBooksTop50.size

                                top50ListAdapter = Top50ListAdapter(this@BookListActivity,allBooksTop50,this)
                                recyclerView.setAdapter(top50ListAdapter)
                                top50ListAdapter.notifyDataSetChanged()

                            }


                            Utils.showSnackBar(applicationContext, "Success", bookListContainer)
                            myDialog.dismiss()

                        } else {
                            Utils.showSnackBar(applicationContext, response.body()!!.msg, bookListContainer)
                            myDialog.dismiss()

                        }


                    } else if (response.code() == 401) {
                        // Handle unauthorized
                        Utils.showSnackBar(applicationContext, "Unauthorized", bookListContainer)
                        myDialog.dismiss()


                    } else if (response.code() == 500) {
                        // Handle unauthorized

                        Utils.showSnackBar(applicationContext, "Server Error", bookListContainer)
                        myDialog.dismiss()


                    } else {
                        //response is failed
                        Utils.showSnackBar(applicationContext, "Failed", bookListContainer)
                        myDialog.dismiss()


                    }
                }


                override fun onFailure(call: Call<GetAllBooksSuccess>, t: Throwable) {

                    Utils.showSnackBar(applicationContext, t.toString(), bookListContainer)
                    myDialog.dismiss()

                }
            })


        } else {
            showSnackBar(this,"Check your internet connection", bookListContainer)

        }
    }



}
