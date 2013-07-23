package edu.cmu.hcii.peer;

import edu.cmu.hcii.peer.structure.Procedure;
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
	
	private static Activity mCurrentActivity = null;
	private DataUpdateReceiver dataUpdateReceiver; // receives broadcast messages from ConnectionService

	private static Procedure current_procedure;
	
    public void onCreate() {
        super.onCreate();
        
		if (dataUpdateReceiver == null) 
        	dataUpdateReceiver = new DataUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter("command");
        registerReceiver(dataUpdateReceiver, intentFilter);
	}
	
    /**
     * Returns current procedure.
     * @return current procedure
     */
    public static Procedure getCurrentProcedure(){
    	return current_procedure;
    }
    
    /**
     * Sets current procedure
     * @param curProcedure current procedure
     */
    public static void setCurrentProcedure(Procedure curProcedure){
    	current_procedure = curProcedure;
    }
    
	/**
	 * Returns current activity.
	 * @param mCurrentAct current activity
	 */
    public static Activity getCurrentActivity(){
		return mCurrentActivity;
	}
	
	/**
	 * For keeping track of current activity.
	 * @param mCurrentAct
	 */
	public static void setCurrentActivity(Activity mCurrentAct){
	    mCurrentActivity = mCurrentAct;
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