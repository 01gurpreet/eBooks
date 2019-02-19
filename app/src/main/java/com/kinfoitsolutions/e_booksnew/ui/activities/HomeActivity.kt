package com.kinfoitsolutions.e_booksnew.ui.activities

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.toolbar.*
import com.kinfoitsolutions.e_booksnew.ui.fragments.HomeFragment
import androidx.annotation.IdRes
import kotlinx.android.synthetic.main.activity_home.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.kinfoitsolutions.e_booksnew.AppConstants
import com.kinfoitsolutions.e_booksnew.R
import com.kinfoitsolutions.e_booksnew.ui.fragments.CategoryFragment
import com.kinfoitsolutions.e_booksnew.ui.fragments.MyLibraryFragment
import com.kinfoitsolutions.e_booksnew.ui.fragments.SettingFragment


class HomeActivity : AppCompatActivity() {

    private lateinit var fragToLoad: String
    private lateinit var fragment: Fragment
    private var fManager = supportFragmentManager
    var fTransaction = fManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)




        if (Build.VERSION.SDK_INT >= 21) {
            window.navigationBarColor = ContextCompat.getColor(
                this,
                R.color.orange
            ) // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            window.statusBarColor = ContextCompat.getColor(
                this,
                com.kinfoitsolutions.e_booksnew.R.color.orange
            ) //status bar or the time bar at the top
        }

        toolbartitle.setText("eBooks")

        searchView.visibility = View.VISIBLE

        val extras = intent.extras
        if (extras != null) {
            fragToLoad = extras.getString(AppConstants.LIBRARY_FRAG)

            if (fragToLoad.equals("LIB")) {

                fragment = MyLibraryFragment()
                fTransaction.add(
                    R.id.main_container, fragment,
                    "FTAG"
                ).commit()

            }

        }


        supportFragmentManager.beginTransaction()
            .replace(com.kinfoitsolutions.e_booksnew.R.id.main_container, HomeFragment(), "HomeFragment")
            .addToBackStack(null).commit()

        bottom_navigation.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                    when (item.getItemId()) {

                        R.id.homeTab -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.main_container, HomeFragment(), "HomeFragment").addToBackStack(null)
                                .commit()

                        }
                        R.id.category -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.main_container, CategoryFragment(), "CategoryFragment")
                                .addToBackStack(null).commit()

                        }

                        R.id.myLib -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.main_container, MyLibraryFragment(), "LibraryFragment")
                                .addToBackStack(null).commit()

                        }

                        R.id.settings -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.main_container, SettingFragment(), "SettingsFragment")
                                .addToBackStack(null).commit()

                        }

                    }
                    return true
                }
            })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val mSearch = menu!!.findItem(R.id.action_search)


        return super.onCreateOptionsMenu(menu)

    }

    fun setToolbarTittle(title: String) {
        toolbartitle.setText(title)
    }

    fun hideSearchImg() {
        searchView.visibility = View.GONE
    }

    fun showSearhcImg() {
        searchView.visibility = View.VISIBLE
    }




}
