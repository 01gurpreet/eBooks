package com.kinfoitsolutions.e_booksnew.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.kinfoitsolutions.e_booksnew.AppConstants

import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.adapters.RecommandedRecycleAdapter
import com.kinfoitsolutions.e_booksnew.adapters.Top50Adapter
import com.kinfoitsolutions.e_booksnew.response.GetAllBooksReponse.GetAllBooksSuccess
import com.kinfoitsolutions.e_booksnew.restclient.RestClient
import com.kinfoitsolutions.e_booksnew.ui.BaseFragment
import com.kinfoitsolutions.e_booksnew.ui.activities.BookDetailActivity
import com.kinfoitsolutions.e_booksnew.ui.activities.BookListActivity
import com.kinfoitsolutions.e_booksnew.ui.activities.HomeActivity
import com.kinfoitsolutions.e_booksnew.util.Utils
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap
import com.kinfoitsolutions.e_booksnew.customfonts.TextViewGiorgia
import com.kinfoitsolutions.e_booksnew.response.AllSearchDataSuccess.AllSearchDataSuccess

class HomeFragment : BaseFragment() {


    private lateinit var viewOfLayout: View

    private lateinit var bAdapter: RecommandedRecycleAdapter
    // top50 data
    private lateinit var top50Adapter: Top50Adapter

    
    override fun provideYourFragmentView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View {

        // Inflate the layout for this fragment
        viewOfLayout =  inflater.inflate(R.layout.fragment_home, parent, false)
        setHasOptionsMenu(true)

        val mainActivity = activity as HomeActivity
        mainActivity.setToolbarTittle("eBooks")

        mainActivity.showSearhcImg()

        val searchView =  mainActivity.findViewById<SearchView>(R.id.searchView)
        val toolbarTitle =  mainActivity.findViewById<TextViewGiorgia>(R.id.toolbartitle)


        //recommanded recyclerview code is here
        val layoutManager = GridLayoutManager(context, 3)
        viewOfLayout.recommanded_recyclerview.setLayoutManager(layoutManager)
        viewOfLayout.recommanded_recyclerview.setItemAnimator(DefaultItemAnimator())


        //Recommended Apps Data
        getAllBooksApi()

        //   top 50 recyclerview code is here
        val layoutManager1 = GridLayoutManager(context, 3)
        viewOfLayout.top50_books_recyclerview.setLayoutManager(layoutManager1)
        viewOfLayout.top50_books_recyclerview.setItemAnimator(DefaultItemAnimator())


        viewOfLayout.viewAllRecommend.setOnClickListener {
            val intent = Intent(context, BookListActivity::class.java)
            val type = "recommended"
            intent.putExtra("key",type)
            startActivity(intent)
        }

        viewOfLayout.viewAllToday.setOnClickListener {
            val intent = Intent(context, BookListActivity::class.java)
            val type = "top50"
            intent.putExtra("key",type)
            startActivity(intent)
        }

        searchView.queryHint = "Search Books"

        searchView.setOnSearchClickListener {
            toolbarTitle.visibility = View.GONE
        }


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                toolbarTitle.visibility = View.VISIBLE
                searchApiCall(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

               // searchApiCall(newText!!)
                return false
            }

        })

        return viewOfLayout
    }

    private fun getAllBooksApi() {
        if (isNetworkConnected(context!!)) {

            val myDialog = Utils.showProgressDialog(context, "Please wait......")

            val restClient = RestClient.getClient()

            val stringHashMap = HashMap<String, String>()

            stringHashMap.put("token", AppConstants.TOKEN)

            restClient.get_books(stringHashMap).enqueue(object : Callback<GetAllBooksSuccess>,

                RecommandedRecycleAdapter.mClickListenerRecommended, Top50Adapter.mTop50ClickListener {

                override fun mTop50Click(c: View?, pos: Int,
                                         bookId: Int?,
                                         bookName: String?,
                                         bookFile: String?,
                                         bookImage: String?,
                                         authorName: String?,
                                         bookText: String?,
                                         bookAudio: String?) {

                    Hawk.put(AppConstants.PDF_FILE, bookFile)
                    Hawk.put(AppConstants.BOOK_IMAGE, bookImage)
                    Hawk.put(AppConstants.BOOK_NAME, bookName)
                    Hawk.put(AppConstants.AUTHOR_NAME, authorName)
                    Hawk.put(AppConstants.BOOKS_TEXT, bookText)
                    Hawk.put(AppConstants.BOOK_ID,bookId.toString())
                    Hawk.put(AppConstants.Audio_Book,bookAudio)

                    val intent = Intent(context, BookDetailActivity::class.java)
                    intent.putExtra(AppConstants.BOOK_NAME,bookName)
                    startActivity(intent)


                }

                override fun mRecommendClick(v: View?, position: Int,
                                             bookId:Int?,
                                             bookName: String?,
                                             bookFile: String?,
                                             bookImage: String?,
                                             authorName: String?,
                                             bookText: String?,
                                             audioFile: String?) {

                    Hawk.put(AppConstants.PDF_FILE, bookFile)
                    Hawk.put(AppConstants.BOOK_IMAGE, bookImage)
                    Hawk.put(AppConstants.BOOK_NAME, bookName)
                    Hawk.put(AppConstants.AUTHOR_NAME, authorName)
                    Hawk.put(AppConstants.BOOKS_TEXT,bookText)
                    Hawk.put(AppConstants.BOOK_ID,bookId.toString())
                    Hawk.put(AppConstants.Audio_Book,audioFile)



                    val intent = Intent(context,BookDetailActivity::class.java)
                    intent.putExtra(AppConstants.BOOK_NAME,bookName)
                    startActivity(intent)

                }

                override fun onResponse(call: Call<GetAllBooksSuccess>, response: Response<GetAllBooksSuccess>) {

                    if (response.isSuccessful) {

                        if (response.body()!!.code.equals("100")) {


                            //Recommended Data
                            val allRecommededBooks = response.body()!!.recommended
                            bAdapter = RecommandedRecycleAdapter(context, allRecommededBooks, this)
                            viewOfLayout.recommanded_recyclerview.setAdapter(bAdapter)
                            bAdapter.notifyDataSetChanged()

                            //Top 50 Data
                            val allTop50Books = response.body()!!.top50
                            top50Adapter = Top50Adapter(allTop50Books, context, this)
                            viewOfLayout.top50_books_recyclerview.setAdapter(top50Adapter)
                            top50Adapter.notifyDataSetChanged()


                            // Utils.showSnackBar(context, "Success", activity!!.main_container)
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


                override fun onFailure(call: Call<GetAllBooksSuccess>, t: Throwable) {

                    Utils.showSnackBar(context, t.toString(), activity!!.main_container)
                    myDialog.dismiss()

                }
            })


        } else {
            showSnackBarFrag("Check your internet connection", activity!!.main_container)

        }
    }

    private fun searchApiCall(searchText: String) {

        if (isNetworkConnected(context!!)) {

            val myDialog = Utils.showProgressDialog(context, "Please wait......")

            val restClient = RestClient.getClient()

            val stringHashMap = HashMap<String, String>()
            stringHashMap.put("token", Hawk.get(AppConstants.TOKEN))
            stringHashMap.put("term", searchText)
            stringHashMap.put("type", "1")


            restClient.searchAllData(stringHashMap)
                .enqueue(object : Callback<AllSearchDataSuccess>{


                    override fun onResponse(
                        call: Call<AllSearchDataSuccess>,
                        response: Response<AllSearchDataSuccess>
                    ) {

                        if (response.isSuccessful) {

                            if (response.body()!!.code.equals("100")) {

                                val searchAllBooks = response.body()!!.data

                                //Recommended Data
                                /*  val allRecommededBooks = response.body()!!.data

                                  bAdapter = RecommandedRecycleAdapter(context, allRecommededBooks, null)
                                  viewOfLayout.recommanded_recyclerview.setAdapter(bAdapter)
                                  bAdapter.notifyDataSetChanged()*/


                                // Utils.showSnackBar(context, "Success", activity!!.main_container)
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


                    override fun onFailure(call: Call<AllSearchDataSuccess>, t: Throwable) {

                        Utils.showSnackBar(context, t.toString(), activity!!.main_container)
                        myDialog.dismiss()

                    }
                })


        } else {
            showSnackBarFrag("Check your internet connection", activity!!.main_container)

        }

    }

}
