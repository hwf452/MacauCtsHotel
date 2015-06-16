package com.halong.macauctshotel.book;


import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;


public class DemoApplication extends Application {
	
    private static DemoApplication mInstance = null;
    public boolean m_bKeyRight = true;
    BMapManager mBMapManager = null;

    public static final String strKey = "NSr9Owg1Z66kSWM0MF3HwtOj";
	
	@Override
    public void onCreate() {
	    super.onCreate();
		mInstance = this;
		initEngineManager(this);
	}
	
	public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(DemoApplication.getInstance().getApplicationContext(), 
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
	}
	
	public static DemoApplication getInstance() {
		return mInstance;
	}
	
	
	
    static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
            	Toast.makeText(DemoApplication.getInstance().getApplicationContext(), "您的网络未联接！",
                        Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                
            }
          
        }

        @Override
        public void onGetPermissionState(int iError) {
        	
            if (iError != 0) {
               
//            	 Toast.makeText(DemoApplication.getInstance().getApplicationContext(), 
//                         "请输入正确的授权Key"+iError, Toast.LENGTH_LONG).show();
                 DemoApplication.getInstance().m_bKeyRight = false;
            }
            else{
            	DemoApplication.getInstance().m_bKeyRight = true;
            	
            }
        }
    }
}