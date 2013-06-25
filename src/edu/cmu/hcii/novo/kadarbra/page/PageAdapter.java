package edu.cmu.hcii.novo.kadarbra.page;

import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class PageAdapter extends PagerAdapter {
	Activity activity;
	List<ViewGroup> pages;
	
	// constructor
		// takes in the current activity and an ArrayList of StepPage objects
	public PageAdapter(Activity activity, List<ViewGroup> pages){
		this.pages = pages;
		this.activity = activity;
	}
	
	// called when adding a view to the ViewPager
	@Override
	public Object instantiateItem(View collection, int position) {
		ViewGroup view = pages.get(position);
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
		return pages.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == ((View) arg1);
	}

}
