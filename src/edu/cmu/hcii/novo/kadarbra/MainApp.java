package edu.cmu.hcii.novo.kadarbra;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

public class MainApp extends Application{
	private static String TAG = "MainApp"; // string used for Logs
	
	private Activity mCurrentActivity = null;
	private DataUpdateReceiver dataUpdateReceiver; // receives broadcast messages from ConnectionService

	
    public void onCreate() {
        super.onCreate();
        
		if (dataUpdateReceiver == null) 
        	dataUpdateReceiver = new DataUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter("command");
        registerReceiver(dataUpdateReceiver, intentFilter);
	}
	
	public Activity getCurrentActivity(){
		return mCurrentActivity;
	}
	
	public void setCurrentActivity(Activity mCurrentActivity){
	    this.mCurrentActivity = mCurrentActivity;
	}
	
    // receives broadcast messages from ConnectionService
    private class DataUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        	//Log.v(TAG, "onReceive");
            if (intent.getAction().equals("connection")) {
            	Bundle b = intent.getExtras();
            	String msg = b.getString("msg");
            	
            	Log.v(TAG,"onReceive: "+ msg);

            	
	            if (msg.equals("1")) {
	            	
	            }else if (msg.equals("2")){
	            	
	            }
            }
            
        }
    }

	
}