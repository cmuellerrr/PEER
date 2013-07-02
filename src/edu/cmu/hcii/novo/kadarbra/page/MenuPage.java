/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import edu.cmu.hcii.novo.kadarbra.MainActivity;
import edu.cmu.hcii.novo.kadarbra.ProcedureActivity;
import edu.cmu.hcii.novo.kadarbra.R;
import edu.cmu.hcii.novo.kadarbra.structure.Procedure;

/**
 * A layout for a menu system.  Displays all available options.
 * 
 * @author Chris
 *
 */
public class MenuPage extends Activity {
	private String TAG ="MenuPage";
	private DataUpdateReceiver dataUpdateReceiver;	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Log.v(TAG, "onCreate");
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    // These flags allow touches outside of the ConnectionPopUp to be detected. In this case, outside touches will close the pop-up.
	    // THIS IS JUST FOR TESTING
	    getWindow().setFlags(LayoutParams.FLAG_NOT_TOUCH_MODAL, LayoutParams.FLAG_NOT_TOUCH_MODAL);
	    getWindow().setFlags(LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
	    
	    
	    setContentView(R.layout.menupopup);
        
        initStowageButton();
        initNavigateButton();
        initAnnotationButton();
	}
	
	private void initStowageButton(){
		Button stowageButton = (Button) findViewById(R.id.stowageButton);
		stowageButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				Procedure procedure = (Procedure)intent.getSerializableExtra(MainActivity.PROCEDURE);
				setContentView(new StowagePage(MenuPage.this, procedure.getStowageItems()));
			}
			
		});
	}
	
	private void initNavigateButton(){
		Button navButton = (Button) findViewById(R.id.navButton);
		navButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				Procedure procedure = (Procedure)intent.getSerializableExtra(MainActivity.PROCEDURE);
				int curStep = (Integer) intent.getSerializableExtra(ProcedureActivity.CURRENT_STEP);
				setContentView(new NavigationPage(MenuPage.this, procedure.getSteps(), curStep));
			}
			
		});
	}
	
	private void initAnnotationButton(){
		Button annotationButton = (Button) findViewById(R.id.annotationButton);
		annotationButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setContentView(new AnnotationPage(MenuPage.this));
			}
			
		});
	}
	
	/**
	 * For testing
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    // If we've received a touch notification that the user has touched outside the pop-up, finish and close the activity.
	if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
	  finish();
	  return true;
	}
	
	// Delegate everything else to Activity.
	    return super.onTouchEvent(event);
	  }

	@Override
	protected void onStart() {
	    super.onStart();
	    Log.v(TAG, "onStart");
	    // The activity is about to become visible.
	   
	}
	@Override
	protected void onResume() {
	    super.onResume();
	    Log.v(TAG, "onResume");
	    // The activity has become visible (it is now "resumed").
	    if (dataUpdateReceiver == null) 
	    	dataUpdateReceiver = new DataUpdateReceiver();
	    IntentFilter intentFilter = new IntentFilter("command");
	    registerReceiver(dataUpdateReceiver, intentFilter);
	}
	@Override
	protected void onPause(){
		super.onPause();
		Log.v(TAG, "onPause");
		if (dataUpdateReceiver != null) 
			unregisterReceiver(dataUpdateReceiver);
	}
	@Override
	protected void onStop() {
	    super.onStop();
	    Log.v(TAG, "onStop");
	    // The activity is no longer visible (it is now "stopped")
	}
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    Log.v(TAG, "onDestroy");	    
	    // The activity is about to be destroyed.
	}
  	  
    private class DataUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("command")) {
            	Bundle b = intent.getExtras();
            	//String msg = b.getString("msg");
            	final int navigate = b.getInt("navigate");

            	//Log.v(TAG, "on receive: "+msg);
            	Log.v(TAG, "on receive: "+navigate);

            	if (navigate!=0){
	            	Intent in = new Intent("command");
	            	in.putExtra("command", "navigate");
	            	in.putExtra("navigate", navigate);
	            	MenuPage.this.setResult(Activity.RESULT_OK,in);
	            	finish();
	            }
            	
          }
        }
    }
    
}
