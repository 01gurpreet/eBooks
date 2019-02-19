package com.kinfoitsolutions.e_booksnew.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.kinfoitsolutions.e_booksnew.AppConstants;
import com.kinfoitsolutions.e_booksnew.data.MessageEvent;
import com.kinfoitsolutions.e_booksnew.util.NetworkUtil;
import org.greenrobot.eventbus.EventBus;


public class NetworkChangeReceiver extends BroadcastReceiver {
    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        String status = NetworkUtil.getConnectivityStatusString(context);

        Log.e("Receiver ", "" + status);

        if (status.equals(AppConstants.INSTANCE.getNOT_CONNECT())) {
            Log.e("Receiver ", "not connection");// your code when internet lost
            EventBus.getDefault().post(new MessageEvent(status));



        } else {
           // checkNetStatus(false);
            EventBus.getDefault().post(new MessageEvent(status));

            Log.e("Receiver ", "connected to internet");//your code when internet connection come back
        }
       // DashboardActivity.addLogText(status);

    }




}
