package edu.cmu.hcii.novo.kadarbra;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.annotation.TargetApi;
import android.os.Build;

public class ProcedureActivity extends Activity {
	private static final String TAG = "ProcedureActivity";	// used for logging purposes
	ViewPager viewPager;
	Breadcrumb breadcrumb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_procedure);
		
		initViewPager(); // initializes ViewPager (the horizontal swiping UI element)
		initBreadcrumb(); // initilizes the breadcrumb (the step numbers at the top)
	}

	
	// initializes ViewPager (the horizontal swiping UI element)
	private void initViewPager(){
		viewPager = (ViewPager) findViewById(R.id.viewpager);	// gets the ViewPager UI object from its XML id
		String[] testStr={"1","2","3","4","5"}; // dummy data that serves as text for the step pages
		ArrayList<StepPage> sp = new ArrayList<StepPage>();
		
		for (int i = 0; i<5; i++){ // populates the StepPage array with dummy data
			sp.add(new StepPage(this, testStr[i]));
		}
		
		PagerAdapter pagerAdapter = new StepPagerAdapter(this, sp); // the PagerAdapter is used to popuplate the ViewPager
		
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
			}
			
		});
	}
	
	// initalizes the Breadcrumb (currently just step numbers)
	private void initBreadcrumb(){
		breadcrumb = (Breadcrumb) findViewById(R.id.breadcrumb);
		breadcrumb.setTotalSteps(viewPager.getAdapter().getCount());
		breadcrumb.setCurrentStep(1);
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
        Log.v(TAG, "onResume");

    }
    
    // The activity is paused
    @Override
    protected void onPause(){
    	super.onPause();
    	Log.v(TAG, "onPause");
    }
    
    // The activity is no longer visible (it is now "stopped")
    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
    }
    
    // The activity is about to be destroyed.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }
	

/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.procedure, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
*/
}
