package com.kinfoitsolutions.e_booksnew.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.kinfoitsolutions.e_booksnew.AppConstants;


public class NetworkUtil {

    private static int TYPE_WIFI = 1;
    private static int TYPE_MOBILE = 2;
    private static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {

        int conn = NetworkUtil.getConnectivityStatus(context);

        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            //status = "Wifi enabled";
            status = AppConstants.INSTANCE.getCONNECT_TO_WIFI();
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            //status = "Mobile data enabled";
            System.out.println(AppConstants.INSTANCE.getCONNECT_TO_MOBILE());
            status = getNetworkClass(context);
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = AppConstants.INSTANCE.getNOT_CONNECT();
        }

        return status + " / " + DateTime.getCurrentDataTime();
    }

    private static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info == null || !info.isConnected())
            return "-"; //not connected

        if(info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if(info.getType() == ConnectivityManager.TYPE_MOBILE){
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                    return "4G";
                default:
                    return "UNKNOWN";
            }
        }
        return "UNKNOWN";
    }
}
