package com.kinfoitsolutions.e_booksnew.ui.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import com.kinfoitsolutions.e_booksnew.AppConstants
import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.response.GetFilterData.GetFilterDataSuccess
import com.kinfoitsolutions.e_booksnew.restclient.RestClient
import com.kinfoitsolutions.e_booksnew.ui.BaseActivity
import com.kinfoitsolutions.e_booksnew.util.Utils
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_check_box.*
import kotlinx.android.synthetic.main.activity_ebooks_filter.*
import kotlinx.android.synthetic.main.toolbar_help.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import android.util.Log
import android.widget.AdapterView
import com.kinfoitsolutions.e_booksnew.response.FilterResponse.FilterSuccess





class EbooksFilterActivity : BaseActivity(), View.OnClickListener {

    private lateinit var arraylist: String
    private lateinit var arraylistId: String
    private lateinit var item: String
    private lateinit var rating: String
    private val STATIC_INTEGER_FILTER = 0
    private lateinit var statusFilterIndex: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ebooks_filter)

        val booksSize = Hawk.get(AppConstants.BOOKS_SIZE, "")

        toolbar_text.setText("Filter" + " (" + booksSize + " Books)")

        backArrowHelp.setOnClickListener {
            finish()
        }

        getFilterData()

        val list = ArrayList<String>()
        list.add("Recommended")
        list.add("Top 50")


        val dataAdapter = ArrayAdapter(this@EbooksFilterActivity,
            R.layout.spinner_item, R.id.spinner_text, list)
        spinnerSort.adapter = dataAdapter

        linear1.setOnClickListener(this)
        linear2.setOnClickListener(this)
        linear3.setOnClickListener(this)
        linear4.setOnClickListener(this)
        linear5.setOnClickListener(this)

        rating = one.text.toString().trim()
        rating = two.text.toString().trim()
        rating = three.text.toString().trim()
        rating = four.text.toString().trim()
        rating = five.text.toString().trim()



        val intent = intent


        try {
            arraylist = intent.getStringExtra("array")
            arraylistId = intent.getStringExtra("arrayid")

            Log.e("arraylist", "" + arraylist)
            Log.e("arraylistId", "" + arraylistId)


        } catch (e: Exception) {

        }


        authorsFilter.setOnClickListener {

            // 1. create an intent pass class name or intent action name
            val intent = Intent(Intent(this@EbooksFilterActivity, CheckBoxActivity::class.java))

            // 2. put ic_filter choice in intent
          //  intent.putExtra("statusFilterIndex", statusFilterIndex)
            overridePendingTransition(R.anim.enter, R.anim.exit)
            Hawk.put(AppConstants.FILTER_KEY, "authors")

            // 3. start the activity
            startActivityForResult(intent, STATIC_INTEGER_FILTER)
        }

        languagesFilter.setOnClickListener {

            // 1. create an intent pass class name or intent action name
            val intent = Intent(Intent(this@EbooksFilterActivity, CheckBoxActivity::class.java))

            // 2. put ic_filter choice in intent
           // intent.putExtra("statusFilterIndex", statusFilterIndex)

            overridePendingTransition(R.anim.enter, R.anim.exit)
            Hawk.put(AppConstants.FILTER_KEY, "languages")

            // 3. start the activity
            startActivityForResult(intent, STATIC_INTEGER_FILTER)
        }


        spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                 item = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}

        }
        applyFilterButton.setOnClickListener {

            filterApi()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            STATIC_INTEGER_FILTER -> {

                if (resultCode == Activity.RESULT_OK) {

                    val res = data!!.getExtras()
                     statusFilterIndex = res.getString("arrayid")

                    val text = if (statusFilterIndex.startsWith(","))
                        statusFilterIndex.substring(1) else statusFilterIndex

                    showMessage("FilterActivity done, result: $text")

                    invalidateOptionsMenu()
                }
            }
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    private fun filterApi() {

        if (isNetworkConnected()) {

            val myDialog = Utils.showProgressDialog(this, "Please wait......")

            val restClient = RestClient.getClient()

            val stringHashMap = HashMap<String, String>()
            stringHashMap.put("token", Hawk.get(AppConstants.TOKEN))
            stringHashMap.put("book_type", item)
            stringHashMap.put("rating", rating)
            stringHashMap.put("authorid", statusFilterIndex)
            stringHashMap.put("languageid", statusFilterIndex)

            restClient.filter_books(stringHashMap).enqueue(object : Callback<FilterSuccess> {

                override fun onResponse(call: Call<FilterSuccess>, response: Response<FilterSuccess>) {

                    if (response.isSuccessful) {

                        if (response.body()!!.code.equals("100")) {

                            val allBooks = response.body()!!.books


                           val intent = Intent(this@EbooksFilterActivity, BookListActivity::class.java)
                            intent.putExtra("FILES_TO_SEND","any value")
                            val type = "recommended"
                            intent.putExtra("key",type)
                            startActivityForResult(intent, 11)
                            finish()

                        } else {

                            Utils.showSnackBar(this@EbooksFilterActivity, response.body()!!.msg, filterContainer)
                            myDialog.dismiss()

                        }
                        myDialog.dismiss()

                    }


                }

                override fun onFailure(call: Call<FilterSuccess>, t: Throwable) {

                    Utils.showSnackBar(this@EbooksFilterActivity, t.toString(), filterContainer)
                    myDialog.dismiss()

                }
            })


        } else {

            Utils.showSnackBar(this, "Check your internet connection", filterContainer)

        }
    }

    private fun getFilterData() {

        if (isNetworkConnected()) {

            val myDialog = Utils.showProgressDialog(this, "Please wait......")

            val restClient = RestClient.getClient()

            val stringHashMap = HashMap<String, String>()
            stringHashMap.put("token", Hawk.get(AppConstants.TOKEN))

            restClient.getFilterData(stringHashMap).enqueue(object : Callback<GetFilterDataSuccess> {

                override fun onResponse(call: Call<GetFilterDataSuccess>, response: Response<GetFilterDataSuccess>) {

                    if (response.isSuccessful) {

                        if (response.body()!!.code.equals("100")) {

                            val allAuthors = response.body()!!.authors
                            val allLanguages = response.body()!!.languages

                            val subSizeAuthors = allAuthors.size - 2
                            val subSizeLanguages = allLanguages.size - 2

                            authorsSubText.text =
                                allAuthors.get(0).name + " , " + allAuthors.get(1).name + " +" + subSizeAuthors + " more"
                            langSubText.text =
                                allLanguages.get(0).name + " , " + allLanguages.get(1).name + " +" + subSizeLanguages + " more"

                        } else {

                            Utils.showSnackBar(this@EbooksFilterActivity, response.body()!!.msg, filterContainer)
                            myDialog.dismiss()

                        }
                        myDialog.dismiss()

                    }


                }

                override fun onFailure(call: Call<GetFilterDataSuccess>, t: Throwable) {

                    Utils.showSnackBar(this@EbooksFilterActivity, t.toString(), filterContainer)
                    myDialog.dismiss()

                }
            })


        } else {

            Utils.showSnackBar(this, "Check your internet connection", filterContainer)

        }
    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.linear1 -> {

                linear1.setBackgroundResource(R.drawable.orange_rect)
                linear2.setBackgroundResource(R.drawable.spinner_rect)
                linear3.setBackgroundResource(R.drawable.spinner_rect)
                linear4.setBackgroundResource(R.drawable.spinner_rect)
                linear5.setBackgroundResource(R.drawable.spinner_rect)

                star1.setImageResource(R.drawable.ic_star_white)
                star2.setImageResource(R.drawable.ic_star_gray)
                star3.setImageResource(R.drawable.ic_star_gray)
                star4.setImageResource(R.drawable.ic_star_gray)
                star5.setImageResource(R.drawable.ic_star_gray)


                one.setTextColor(Color.parseColor("#ffffff"))
                two.setTextColor(Color.parseColor("#828282"))
                three.setTextColor(Color.parseColor("#828282"))
                four.setTextColor(Color.parseColor("#828282"))
                five.setTextColor(Color.parseColor("#828282"))
            }

            R.id.linear2 -> {

                linear1.setBackgroundResource(R.drawable.spinner_rect)
                linear2.setBackgroundResource(R.drawable.orange_rect)
                linear3.setBackgroundResource(R.drawable.spinner_rect)
                linear4.setBackgroundResource(R.drawable.spinner_rect)
                linear5.setBackgroundResource(R.drawable.spinner_rect)

                star1.setImageResource(R.drawable.ic_star_gray)
                star2.setImageResource(R.drawable.ic_star_white)
                star3.setImageResource(R.drawable.ic_star_gray)
                star4.setImageResource(R.drawable.ic_star_gray)
                star5.setImageResource(R.drawable.ic_star_gray)


                one.setTextColor(Color.parseColor("#828282"))
                two.setTextColor(Color.parseColor("#ffffff"))
                three.setTextColor(Color.parseColor("#828282"))
                four.setTextColor(Color.parseColor("#828282"))
                five.setTextColor(Color.parseColor("#828282"))
            }

            R.id.linear3 -> {

                linear1.setBackgroundResource(R.drawable.spinner_rect)
                linear2.setBackgroundResource(R.drawable.spinner_rect)
                linear3.setBackgroundResource(R.drawable.orange_rect)
                linear4.setBackgroundResource(R.drawable.spinner_rect)
                linear5.setBackgroundResource(R.drawable.spinner_rect)

                star1.setImageResource(R.drawable.ic_star_gray)
                star2.setImageResource(R.drawable.ic_star_gray)
                star3.setImageResource(R.drawable.ic_star_white)
                star4.setImageResource(R.drawable.ic_star_gray)
                star5.setImageResource(R.drawable.ic_star_gray)


                one.setTextColor(Color.parseColor("#828282"))
                two.setTextColor(Color.parseColor("#828282"))
                three.setTextColor(Color.parseColor("#ffffff"))
                four.setTextColor(Color.parseColor("#828282"))
                five.setTextColor(Color.parseColor("#828282"))
            }


            R.id.linear4 -> {


                linear1.setBackgroundResource(R.drawable.spinner_rect)
                linear2.setBackgroundResource(R.drawable.spinner_rect)
                linear3.setBackgroundResource(R.drawable.spinner_rect)
                linear4.setBackgroundResource(R.drawable.orange_rect)
                linear5.setBackgroundResource(R.drawable.spinner_rect)

                star1.setImageResource(R.drawable.ic_star_gray)
                star2.setImageResource(R.drawable.ic_star_gray)
                star3.setImageResource(R.drawable.ic_star_gray)
                star4.setImageResource(R.drawable.ic_star_white)
                star5.setImageResource(R.drawable.ic_star_gray)


                one.setTextColor(Color.parseColor("#828282"))
                two.setTextColor(Color.parseColor("#828282"))
                three.setTextColor(Color.parseColor("#828282"))
                four.setTextColor(Color.parseColor("#ffffff"))
                five.setTextColor(Color.parseColor("#828282"))
            }


            R.id.linear5 -> {


                linear1.setBackgroundResource(R.drawable.spinner_rect)
                linear2.setBackgroundResource(R.drawable.spinner_rect)
                linear3.setBackgroundResource(R.drawable.spinner_rect)
                linear4.setBackgroundResource(R.drawable.spinner_rect)
                linear5.setBackgroundResource(R.drawable.orange_rect)

                star1.setImageResource(R.drawable.ic_star_gray)
                star2.setImageResource(R.drawable.ic_star_gray)
                star3.setImageResource(R.drawable.ic_star_gray)
                star4.setImageResource(R.drawable.ic_star_gray)
                star5.setImageResource(R.drawable.ic_star_white)


                one.setTextColor(Color.parseColor("#828282"))
                two.setTextColor(Color.parseColor("#828282"))
                three.setTextColor(Color.parseColor("#828282"))
                four.setTextColor(Color.parseColor("#828282"))
                five.setTextColor(Color.parseColor("#ffffff"))
            }
        }

    }

}
