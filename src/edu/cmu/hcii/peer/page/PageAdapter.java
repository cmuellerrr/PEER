package edu.cmu.hcii.peer.page;

import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * An adapter used for getting normal pages into the step page scroll view.
 * 
 * @author Gordon
 *
 */
public class PageAdapter extends PagerAdapter {
	
	private Activity activity;
	private List<ViewGroup> pages;
	


	/**
	 * Takes in the current activity and an ArrayList of StepPage objects
	 * 
	 * @param activity
	 * @param pages
	 */
	public PageAdapter(Activity activity, List<ViewGroup> pages){
		this.pages = pages;
		this.activity = activity;
	}
	
	
	
	/**
	 * Called when adding a view to the ViewPager.  Adds the adapter
	 * page at the given position to the beginning of the given collection.
	 * The object added is then returned
	 */
	@Override
	public Object instantiateItem(View collection, int position) {
		ViewGroup view = pages.get(position);
		view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		view.setTag(position);
		((ViewPager) collection).addView(view,0);
		
		return view;
	}

	
	
	/**
	 * Removes the given item from the given view.
	 */
	@Override
	public void destroyItem(View collection, int position, Object view) {
	    ((ViewPager) collection).removeView((View) view);
	}
	
	
	
	/**
	 * Gets the amount of pages in the adapter
	 */
	@Override
	public int getCount() {
		return pages.size();
	}

	
	
	/**
	 * Checks if the given parameters are equal.
	 */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == ((View) arg1);
	}
	
	
	
	/**
	 * 
	 * @return the pages
	 */
	public List<ViewGroup> getPages(){
		return pages;
	}
	
	
	
	/**
	 * 
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}
}
