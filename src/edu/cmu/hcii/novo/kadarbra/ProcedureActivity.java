package edu.cmu.hcii.novo.kadarbra;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import edu.cmu.hcii.novo.kadarbra.page.ExecNotesPage;
import edu.cmu.hcii.novo.kadarbra.page.PageAdapter;
import edu.cmu.hcii.novo.kadarbra.page.StepPage;
import edu.cmu.hcii.novo.kadarbra.page.StowagePage;
import edu.cmu.hcii.novo.kadarbra.page.TitlePage;
import edu.cmu.hcii.novo.kadarbra.structure.Procedure;
import edu.cmu.hcii.novo.kadarbra.structure.Step;

public class ProcedureActivity extends Activity {
	private static final String TAG = "ProcedureActivity";	// used for logging purposes
	private MainApp MainApp;
	
	private Procedure procedure;
	private ViewPager viewPager;
	private Breadcrumb breadcrumb;
	
	private DataUpdateReceiver dataUpdateReceiver;
	private ProcedureActivity mProcedureActivity;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		MainApp = (MainApp)this.getApplicationContext();
		mProcedureActivity = this;
		
		Intent intent = getIntent();
		procedure = (Procedure)intent.getSerializableExtra(MainActivity.PROCEDURE);
		
		setContentView(R.layout.activity_procedure);
		
		initViewPager(); // initializes ViewPager (the horizontal swiping UI element)
		initBreadcrumb(); // initializes the breadcrumb (the step numbers at the top)
	}

	
	
	// initializes ViewPager (the horizontal swiping UI element)
	private void initViewPager(){
		viewPager = (ViewPager) findViewById(R.id.viewpager);	// gets the ViewPager UI object from its XML id
		List<ViewGroup> sp = setupPages();
		
		PagerAdapter pagerAdapter = new PageAdapter(this, sp); // the PagerAdapter is used to popuplate the ViewPager
		
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(0);
		
		
		// sets a listener for whenever the page in the ViewPager changes.
		viewPager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {
				//Log.v("viewPager","onPageScrollStateChanged");
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				//Log.v("viewPager","onPageScrolled");
				
			}

			@Override
			public void onPageSelected(int arg0) {
				Log.v("viewPager","onPageSelected "+arg0);
				breadcrumb.setCurrentStep(arg0+1); // updates breadcrumb when a new page is selected
				/*if (!(viewPager.getChildAt(viewPager.getCurrentItem()).getClass() == StepPage.class)) {
					Log.v(TAG, "Removing breadcrumb");
					breadcrumb.setVisibility(View.INVISIBLE);
				} else {
					Log.v(TAG, "Removing breadcrumb");
					breadcrumb.setVisibility(View.VISIBLE);
				}*/
			}
			
		});
	}
	
	
	
	/**
	 * Setup the procedure steps as a list of step pages.
	 * 
	 * @return
	 */
	private List<ViewGroup> setupPages() {
		List<ViewGroup> result = new ArrayList<ViewGroup>();
		
		result.add(new TitlePage(this, procedure.getNumber(), procedure.getTitle(), procedure.getObjective(), procedure.getDuration()));
		
		result.add(new StowagePage(this, procedure.getStowageItems()));
		
		result.add(new ExecNotesPage(this, procedure.getExecNotes()));
		
		for (int i = 0; i < procedure.getNumSteps(); i++){ // populates the StepPage array with dummy data
			result.addAll(setupStepPage(procedure.getStep(i), null));
		}
		
		return result;
	}
	
	
	
	/**
	 * Setup the given step as a list of step pages.  Recursively
	 * loops through any substeps to get all children.
	 * 
	 * TODO: redo how step pages get their parents. this is dumb
	 * 
	 * @param step
	 * @return
	 */
	private List<ViewGroup> setupStepPage(Step step, Step parent) {
		List<ViewGroup> result = new ArrayList<ViewGroup>();
		
		if (step.getNumSubsteps() > 0) {
			for (int i = 0; i < step.getNumSubsteps(); i++) {
				result.addAll(setupStepPage(step.getSubstep(i), step));
			}
		} else {
			if (parent != null) {
				result.add(new StepPage(this, step, parent));
			} else {
				result.add(new StepPage(this, step));
			}
		}
		
		return result;
	}
	
	
	
	// initalizes the Breadcrumb (currently just step numbers)
	private void initBreadcrumb(){
		breadcrumb = (Breadcrumb) findViewById(R.id.breadcrumb);
		breadcrumb.setTotalSteps(viewPager.getAdapter().getCount());
		breadcrumb.setCurrentStep(1);
		//breadcrumb.setVisibility(View.INVISIBLE);
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
    }
    
    
    
    private void clearReferences() {
    	Activity currActivity = MainApp.getCurrentActivity();
    	if (currActivity != null && currActivity.equals(this))
    		MainApp.setCurrentActivity(null);
    }
    
    
    
	// Listens to broadcast messages
    private class DataUpdateReceiver extends BroadcastReceiver {
    	@Override
        public void onReceive(Context context, Intent intent) {
        	Log.v(TAG, "on receive: " +intent.getAction());
            if (intent.getAction().equals("command") && MainApp.getCurrentActivity()==mProcedureActivity) {
            	Bundle b = intent.getExtras();
            	String msg = b.getString("msg");
            	
            	Log.v(TAG, msg);
            	
            	// For testing test input 
            	if (msg.equals("Back")){
            		prevPage();
            	}else if (msg.equals("Next")){
            		nextPage();
            	}
            	
          }
          
        }
    }
   
    
    
    private void prevPage(){
    	if (viewPager.getCurrentItem()>0)
    		viewPager.setCurrentItem(viewPager.getCurrentItem()-1,true);
    	else
    		finish();
    }
    
    
    
    private void nextPage(){
    	if (viewPager.getCurrentItem()<viewPager.getChildCount());
    		viewPager.setCurrentItem(viewPager.getCurrentItem()+1,true);
    }

}
