package com.kinfoitsolutions.e_booksnew.ui.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.kinfoitsolutions.e_booksnew.ui.BaseActivity
import kotlinx.android.synthetic.main.toolbar_help.*
import kotlinx.android.synthetic.main.activity_check_box.*
import android.view.ViewGroup
import android.widget.*
import android.widget.CheckBox
import com.kinfoitsolutions.e_booksnew.AppConstants
import com.kinfoitsolutions.e_booksnew.response.GetFilterData.GetFilterDataSuccess
import com.kinfoitsolutions.e_booksnew.restclient.RestClient
import com.kinfoitsolutions.e_booksnew.util.Utils
import com.orhanobut.hawk.Hawk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.kinfoitsolutions.e_booksnew.R
import androidx.recyclerview.widget.RecyclerView
import com.kinfoitsolutions.e_booksnew.adapters.CheckBoxAdapter
import com.kinfoitsolutions.e_booksnew.models.CheckBoxModel
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast


class CheckBoxActivity : BaseActivity() {


    var checkBoxSelectTextList = ArrayList<String>()
    private lateinit var selectText: String

    private lateinit var filterType: String


    private lateinit var mAdapter: CheckBoxAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    private lateinit var checkBoxList: ArrayList<CheckBoxModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_box)

        toolbar_text.setText("Back")

        mLayoutManager = LinearLayoutManager(this@CheckBoxActivity)

        filterType = Hawk.get(AppConstants.FILTER_KEY, "")

        backArrowHelp.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

        }

        reset.setOnClickListener {
            removeAllChecks(checkBoxRecycler)
        }

        if (filterType.equals("authors")) {

            getFilterData("authors")

        } else {
            getFilterData("languages")

        }

        checkBoxApply.setOnClickListener {

            //Toast.makeText(getApplicationContext(), checkBoxSelectTextList.toString(), Toast.LENGTH_SHORT).show();

            var data = ""
            var idList = ""
            val stList = (mAdapter as CheckBoxAdapter).nameList

            for (i in stList.indices) {
                val singleName = stList.get(i)

                if (singleName.isSelected.equals(true)) {
                    data = data + "\n" + singleName.getName().toString()

                    idList = idList + "," + singleName.id.toString()

                }
            }

            // Toast.makeText(this@CheckBoxActivity, "Selected Students: \n$data", Toast.LENGTH_LONG).show()
            // val intent = Intent(this@CheckBoxActivity, EbooksFilterActivity::class.java)
            val intent = Intent()


            intent.putExtra("array", data)
            intent.putExtra("arrayid", idList)
            setResult(Activity.RESULT_OK, intent)
            finish()

        }

    }

    private fun getFilterData(s: String) {

        if (isNetworkConnected()) {

            val myDialog = Utils.showProgressDialog(this, "Please wait......")

            val restClient = RestClient.getClient()

            val stringHashMap = HashMap<String, String>()
            stringHashMap.put("token", Hawk.get(AppConstants.TOKEN))

            restClient.getFilterData(stringHashMap).enqueue(object : Callback<GetFilterDataSuccess> {

                override fun onResponse(
                    call: Call<GetFilterDataSuccess>,
                    response: Response<GetFilterDataSuccess>
                ) {

                    if (response.isSuccessful) {

                        if (response.body()!!.code.equals("100")) {

                            val allAuthors = response.body()!!.authors
                            val allLanguages = response.body()!!.languages

                            checkBoxList = ArrayList<CheckBoxModel>()




                            checkBoxRecycler.setHasFixedSize(true);

                            // use a linear layout manager
                            checkBoxRecycler.setLayoutManager(mLayoutManager)

                            // create an Object for Adapter
                            mAdapter = CheckBoxAdapter(checkBoxList)

                            // set the adapter object to the Recyclerview
                            checkBoxRecycler.setAdapter(mAdapter)


                            if (s.equals("authors")) {


                                for (i in 0 until allAuthors.size) {

                                    val st = CheckBoxModel(
                                        response.body()!!.authors.get(i).name,
                                        response.body()!!.authors.get(i).id,
                                        false
                                    )
                                    checkBoxList.add(st)

                                }

                                checkBoxRecycler.setHasFixedSize(true);

                                // use a linear layout manager
                                checkBoxRecycler.setLayoutManager(mLayoutManager)

                                // create an Object for Adapter
                                mAdapter = CheckBoxAdapter(checkBoxList)

                                // set the adapter object to the Recyclerview
                                checkBoxRecycler.setAdapter(mAdapter)


                            } else {


                                for (i in 0 until allLanguages.size) {
                                    val st = CheckBoxModel(
                                        response.body()!!.languages.get(i).name,
                                        response.body()!!.languages.get(i).id,
                                        false
                                    )
                                    checkBoxList.add(st)
                                }

                                checkBoxRecycler.setHasFixedSize(true);

                                // use a linear layout manager
                                checkBoxRecycler.setLayoutManager(mLayoutManager)

                                // create an Object for Adapter
                                mAdapter = CheckBoxAdapter(checkBoxList)

                                // set the adapter object to the Recyclerview
                                checkBoxRecycler.setAdapter(mAdapter)


                            }

                            myDialog.dismiss()

                        } else {
                            Utils.showSnackBar(this@CheckBoxActivity, response.body()!!.msg, checkBoxRecycler)
                            myDialog.dismiss()

                        }


                    } else if (response.code() == 401) {
                        // Handle unauthorized
                        Utils.showSnackBar(this@CheckBoxActivity, "Unauthorized", checkBoxRecycler)
                        myDialog.dismiss()


                    } else if (response.code() == 500) {
                        // Handle unauthorized

                        Utils.showSnackBar(this@CheckBoxActivity, "Server Error", checkBoxRecycler)
                        myDialog.dismiss()


                    } else {
                        //response is failed
                        Utils.showSnackBar(this@CheckBoxActivity, "Failed", checkBoxRecycler)
                        myDialog.dismiss()


                    }
                }

                override fun onFailure(call: Call<GetFilterDataSuccess>, t: Throwable) {

                    Utils.showSnackBar(this@CheckBoxActivity, t.toString(), checkBoxRecycler)
                    myDialog.dismiss()

                }
            })


        } else {
            Utils.showSnackBar(this, "Check your internet connection", checkBoxRecycler)

        }
    }

    private fun getOnClickDoSomething(checkBox: CheckBox): View.OnClickListener? {

        return View.OnClickListener {
            Toast.makeText(getApplicationContext(), "and text***" + checkBox.getText().toString(), Toast.LENGTH_SHORT)
                .show();

            selectText = checkBox.text.toString()
            checkBoxSelectTextList.add(selectText)

        }
    }

    //recursive blind checks removal for everything inside a View
    private fun removeAllChecks(vg: ViewGroup) {
        var v: View? = null
        for (i in 0 until vg.childCount) {
            try {
                v = vg.getChildAt(i)
                (v as CheckBox).isChecked = false
            } catch (e1: Exception) { //if not checkBox, null View, etc
                try {
                    removeAllChecks((v as ViewGroup?)!!)
                } catch (e2: Exception) { //v is not a view group
                    continue
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


}
