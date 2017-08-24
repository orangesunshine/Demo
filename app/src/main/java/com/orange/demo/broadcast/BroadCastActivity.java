package com.orange.demo.broadcast;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.orange.demo.R;
import com.orange.demo.base.activity.ButterKnifeActivity;

import butterknife.Bind;

import static com.orange.demo.R.id.tv_content;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class BroadCastActivity extends ButterKnifeActivity {

    @Bind(tv_content)
    protected TextView tvContent;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_broadcast;
    }

    @Override
    public void onAfterSetContentView(Bundle savedInstanceState) {
        checkState();
        tvContent.setOnClickListener(this);
    }

    @Override
    public void onEffectiveClick(View v) {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new MyBroadCastReceiver(), filter);
    }

    class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean initialStickyBroadcast = isInitialStickyBroadcast();
            System.out.println("网络状态发生变化 + isInitialStickyBroadcast" + initialStickyBroadcast); //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
//             获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//             获取ConnectivityManager对象对应的NetworkInfo对象
//             获取WIFI连接的信息
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//             获取移动数据连接的信息
                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
                } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                    Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
                } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
                } //API大于23时使用下面的方式进行网络监听
            } else {
                System.out.println("API level 大于23");
//                获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//                获取所有网络连接的信息
                Network[] networks = connMgr.getAllNetworks();
//                用于存放网络连接信息
                StringBuilder sb = new StringBuilder();
//                通过循环将网络信息逐个取出来
                for (int i = 0; i < networks.length; i++) {
//                    获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                    sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
                }
                Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkState() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            checkState_23();
        } else {
            checkState_23orNew();
        }
    }

    //检测当前的网络状态 //API版本23以下时调用此方法进行检测 //因为API23后getNetworkInfo(int networkType)方法被弃用
    public void checkState_23() {
        // 步骤1：通过Context.getSystemService(Context.CONNECTIVITY_SERVICE)获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 步骤2：获取ConnectivityManager对象对应的NetworkInfo对象
        // NetworkInfo对象包含网络连接的所有信息
        // 步骤3：根据需要取出网络连接信息
        // 获取WIFI连接的信息
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Boolean isWifiConn = networkInfo.isConnected();
        // 获取移动数据连接的信息
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        Boolean isMobileConn = networkInfo.isConnected();
        tvContent.setText("Wifi是否连接:" + isWifiConn);
        tvContent.setText("移动数据是否连接:" + isMobileConn);
    }

    // API 23及以上时调用此方法进行网络的检测
    // getAllNetworks() 在API 21后开始使用
    // 步骤非常类似
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void checkState_23orNew() {
        // 获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取所有网络连接的信息
        Network[] networks = connMgr.getAllNetworks();
        // 用于存放网络连接信息
        StringBuilder sb = new StringBuilder();
        // 通过循环将网络信息逐个取出来
        for (int i = 0; i < networks.length; i++) {
            // 获取ConnectivityManager对象对应的NetworkInfo对象
            NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
            sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
        }
        tvContent.setText(sb.toString());
    }
}

