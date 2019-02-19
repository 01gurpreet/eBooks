package com.kinfoitsolutions.e_booksnew.ui.activities

import android.os.Bundle
import android.view.View
import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.ui.BaseActivity
import kotlinx.android.synthetic.main.toolbar_help.*

class HelpActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        backArrowHelp.setOnClickListener {
            finish()
        }

        toolbar_text.setText("Help & Support")

        reset.visibility = View.INVISIBLE
    }
}
