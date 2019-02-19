package com.kinfoitsolutions.e_booksnew.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.kinfoitsolutions.e_booksnew.AppConstants
import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.response.LibraryResponse.AddBookLibraryRes
import com.kinfoitsolutions.e_booksnew.restclient.RestClient
import com.kinfoitsolutions.e_booksnew.ui.BaseActivity
import com.kinfoitsolutions.e_booksnew.util.Utils
import com.orhanobut.hawk.Hawk
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDetailActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        /*   val bitmap = BlurImage.with(applicationContext).load(R.drawable.the_dreaming).intensity(15F).getImageBlur()
           imageBlur.setImageBitmap(bitmap)
   */

        val bookName = Hawk.get(AppConstants.BOOK_NAME, "")
        val bookImage = Hawk.get(AppConstants.BOOK_IMAGE, "")
        val authorName = Hawk.get(AppConstants.AUTHOR_NAME, "")


        try {
            Picasso.get().load(bookImage).fit().placeholder(R.drawable.loading).error(R.drawable.no_image)
                .into(imageBlur)
        } catch (e: Exception) {
            e.printStackTrace()
            imageBlur.setImageResource(R.drawable.no_image)
        }

        try {
            Picasso.get().load(bookImage).fit().placeholder(R.drawable.loading).error(R.drawable.no_image)
                .into(smallImage)
        } catch (e: Exception) {
            e.printStackTrace()
            smallImage.setImageResource(R.drawable.no_image)
        }


        txtBookTitle.setText(bookName)
        txtAuthorName.setText(authorName)

        bookDetailBack.setOnClickListener {
            finish()
        }

        shareBook.setOnClickListener {
            shareBook(bookName)
        }

        addToLibrary.setOnClickListener {

            addLibrary()

        }


        readBookClick.setOnClickListener {
            val intent = Intent(this@BookDetailActivity, BookReadActivity::class.java)
            intent.putExtra(AppConstants.BOOK_NAME, bookName)

            startActivity(intent)
        }

    }

    private fun addLibrary() {

        if (isNetworkConnected()) {

            val myDialog = Utils.showProgressDialog(this, "Progressing......")
            val bookid = Hawk.get(AppConstants.BOOK_ID, "")

            val stringHashMap = HashMap<String, String>()
            stringHashMap.put("token", Hawk.get(AppConstants.TOKEN))
            stringHashMap.put("book_id",bookid)
            Log.e("addbooklibrary", "" + stringHashMap);
            val restClient = RestClient.getClient()

            restClient.addbookslib(stringHashMap).enqueue(object : Callback<AddBookLibraryRes> {

                override fun onResponse(call: Call<AddBookLibraryRes>, response: Response<AddBookLibraryRes>) {

                    if (response.isSuccessful) {

                        if (response.body()!!.code.equals(100)) {
                            Utils.showSnackBar(applicationContext, response.body()!!.msg, bookdetail_root_layout)
                            val intent = Intent(this@BookDetailActivity, HomeActivity::class.java)
                            intent.putExtra(AppConstants.LIBRARY_FRAG, "LIB")
                            startActivity(intent)
                            myDialog.dismiss()

                        } else if (response.code() == 401) {
                            // Handle unauthorized
                            Utils.showSnackBar(applicationContext, "Unauthorized", bookdetail_root_layout)
                            myDialog.dismiss()

                        } else if (response.code() == 500) {
                            // Handle unauthorized
                            Utils.showSnackBar(applicationContext, "Server Error", bookdetail_root_layout)
                            myDialog.dismiss()

                        } else {
                            //code 101 invalid credentials
                            Utils.showSnackBar(applicationContext, response.body()!!.msg, bookdetail_root_layout)
                            myDialog.dismiss()

                        }
                    } else {
                        //response is failed
                        Utils.showSnackBar(applicationContext, response.body()!!.msg, bookdetail_root_layout)
                        myDialog.dismiss()

                    }

                }

                override fun onFailure(call: Call<AddBookLibraryRes>, t: Throwable) {

                    Utils.showSnackBar(applicationContext, t.toString(), bookdetail_root_layout)
                    myDialog.dismiss()


                }

            })

        } else {
            Utils.showSnackBar(this, "Check your internet connection", bookdetail_root_layout)

        }
    }

    private fun shareBook(bookName: String) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "E-Books")
            var shareMesssage = "\nLet me recommend you this Book\n\n"
            shareMesssage = shareMesssage + bookName
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMesssage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            e.toString()
        }
    }


}
