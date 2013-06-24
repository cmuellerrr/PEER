package edu.cmu.hcii.novo.kadarbra;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class StepPagerAdapter extends PagerAdapter {
	Activity activity;
	ArrayList<StepPage> steps;
	
	// constructor
		// takes in the current activity and an ArrayList of StepPage objects
	public StepPagerAdapter(Activity act, ArrayList<StepPage> stepPages){
		steps = stepPages;
		activity = act;
	}
	
	// called when adding a view to the ViewPager
	@Override
	public Object instantiateItem(View collection, int position) {
		StepPage view = steps.get(position);
		view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		((ViewPager) collection).addView(view,0);
		
		return view;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
	    ((ViewPager) collection).removeView((View) view);
	}
	
	
	@Override
	public int getCount() {
		return steps.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == ((View) arg1);
	}

}
