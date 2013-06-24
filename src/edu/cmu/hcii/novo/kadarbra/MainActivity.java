package edu.cmu.hcii.novo.kadarbra;

import java.util.ArrayList;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";	// used for logging purposes
	
	private MainApp MainApp;
	
	/** ConnectionService **/
	private DataUpdateReceiver dataUpdateReceiver;
	private ConnectionService mBoundService;
	private boolean mIsBound;
	
	ListView procedureListView; // android widget for lists
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		MainApp = (MainApp)this.getApplicationContext();
		setContentView(R.layout.activity_main);
		
        // to begin ConnectionService
       	if (!isMyServiceRunning())
       		startService(new Intent(MainActivity.this,ConnectionService.class));
       	doBindService();
       	

		initExampleList();
	}
	
	// Initializes the example list
	private void initExampleList(){
		procedureListView = (ListView) findViewById(R.id.listView1);
	
		final ArrayList<String> procedureList = new ArrayList<String>();
		
		for (int i = 0; i < 10; i++){ // dummy data that fills the list up
			procedureList.add("Procedure #"+i);
		}
		
		final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, procedureList);
		procedureListView.setAdapter(adapter); // adapter is used to populate the ListView
		
		
		// sets an listener for if the user clicks on one of the ListView items
		procedureListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		      @Override
		      public void onItemClick(AdapterView<?> parent, final View view,
		          int position, long id) {
		    	  //if (position==0){
		    		  Intent intent = new Intent(parent.getContext(), ProcedureActivity.class);
		    		  startNewActivity(); 
		    	 // }
		      }

		    });		
	}
	
	
	// This function currently starts the ProcedureActivity activity
	private void startNewActivity(){
		  Intent intent = new Intent(this, ProcedureActivity.class);
		  startActivity(intent);
	}
	

    // The activity is about to become visible.
    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");   
    }
	
    // The activity has become visible (it is now "resumed").     
    @Override
    protected void onResume() {
        super.onResume();
        MainApp.setCurrentActivity(this);
        Log.v(TAG, "onResume");
        
        // sets up data update receiver for receiving broadcast messages from ConnectionService
        if (dataUpdateReceiver == null) 
        	dataUpdateReceiver = new DataUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter("command");
        registerReceiver(dataUpdateReceiver, intentFilter);
    }
    
    // The activity is paused
    @Override
    protected void onPause(){
    	super.onPause();
    	clearReferences();
    	Log.v(TAG, "onPause");
    	if (dataUpdateReceiver != null) 
    		unregisterReceiver(dataUpdateReceiver);
    }
    
    // The activity is no longer visible (it is now "stopped")
    @Override
    protected void onStop() {
        super.onStop();
        clearReferences();
        Log.v(TAG, "onStop");
    }
    
    // The activity is about to be destroyed.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearReferences();
        Log.v(TAG, "onDestroy");
        doUnbindService();
    }

    private void clearReferences(){
        Activity currActivity = MainApp.getCurrentActivity();
        if (currActivity != null && currActivity.equals(this))
            MainApp.setCurrentActivity(null);
    }
    
	
    // declares service and connects
    private ServiceConnection mConnection = new ServiceConnection() {
    	public void onServiceConnected(ComponentName className, IBinder service) {
        	 Log.v("TAG", "set mBoundService");
            mBoundService = ((ConnectionService.LocalBinder)service).getService();

        }
        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };

    // Binds the service to the activity 
    // Allows access service functions/variables available to binded activities.
    	// See LocalBinder class in ConnectionService
    private void doBindService() {
    	Log.v(TAG, "bind service");
        bindService(new Intent(MainActivity.this, ConnectionService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }


    // unbinds the service and activity
    private void doUnbindService() {
        if (mIsBound) {
        	Log.v(TAG, "unbind service");
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }
    
    // returns whether or not the service is running
    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ConnectionService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
	// Adds items to the menu bar (currently used for managing the receive socket)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    Log.v(TAG, "menu create");
		menu.add("Enable Server (Socket Currently Closed)");		
		return true;
	}
	
	// Called when a menu item is selected (starts the socket)
	public boolean onOptionsItemSelected (MenuItem item){
		mBoundService.startServer();		
		return false;
	}
	
	// Called any time the bottom menu pops up
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
	   	 Log.v(TAG, "menu prepare");
	   	 
		   	 if (mBoundService.getConnectionStatus()==0){  // if not connected
		   		 menu.getItem(0).setTitle("Enable Server (Socket Currently Closed)");
		   	 }else if (mBoundService.getConnectionStatus()==1){ // if waiting
		   		 menu.getItem(0).setTitle("Socket Waiting for Connection...");	   		 
		   	 }else if (mBoundService.getConnectionStatus()==2){ // if connected
		   		 menu.getItem(0).setTitle("Socket Connected!!!1");
		   	 }

	   	 
     super.onPrepareOptionsMenu(menu);
   	 return true;
	}
	
	// Listens to broadcast messages
    private class DataUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        	Log.v(TAG, "on receive");
            if (intent.getAction().equals("command")) {
            	Bundle b = intent.getExtras();
            	String msg = b.getString("msg");
            	
            	Log.v(TAG, msg);
            	
            	// For testing test input 
            	if (msg.equals("Back")){
            		
            	}else if (msg.equals("Next")){
		    		  startNewActivity(); 
            	}
            	
          }
          
        }
    }
    

	
}
