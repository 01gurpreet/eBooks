package com.kinfoitsolutions.e_booksnew.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.kinfoitsolutions.e_booksnew.ui.fragments.Login_Fragment;


/**
 * Created by wolfsoft on 10/11/2015.
 */
public class LoginPagerAdapter extends FragmentStatePagerAdapter {



    public LoginPagerAdapter(FragmentManager fm) {
        super(fm);

    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Login_Fragment tab1 = new Login_Fragment();
                return tab1;

            case 1:
                Login_Fragment tab6 = new Login_Fragment();
                return tab6;


            case 2:
                Login_Fragment tab2 = new Login_Fragment();
                return tab2;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}