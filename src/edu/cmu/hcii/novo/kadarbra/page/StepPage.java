package edu.cmu.hcii.novo.kadarbra.page;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.structure.Step;

public class StepPage extends LinearLayout {
	private Step parent;
	private Step step;
	
	// constructs a step page, which is a layout object that can have children objects - the type of layout object used will change in the future (probably a vertical ViewPager)
	public StepPage(Context context, Step step) {
		super(context);
		this.setOrientation(VERTICAL);
		
		// adds text to the layout by adding a TextView
		this.step = step;
		this.parent = null;
		
		TextView temp = new TextView(context);
		temp.setText(step.getNumber() + ": " + step.getText());
		temp.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(temp);
	}
	
	public StepPage(Context context, Step step, Step parent) {
		super(context);
		this.setOrientation(VERTICAL);
		
		this.step = step;
		this.parent = parent;
		
		TextView parentView = new TextView(context);
		parentView.setText(parent.getNumber() + ": " + parent.getText());
		parentView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		this.addView(parentView);
		
		TextView subView = new TextView(context);
		subView.setText(step.getNumber() + ": " + step.getText());
		subView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		this.addView(subView);
	}
	
	

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed,l,t,r,b);
		// TODO Auto-generated method stub	
	}

}
