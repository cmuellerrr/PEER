package edu.cmu.hcii.novo.kadarbra;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StepPage extends LinearLayout {
	private Step step;
	
	// constructs a step page, which is a layout object that can have children objects - the type of layout object used will change in the future (probably a vertical ViewPager)
	public StepPage(Context context, Step step) {
		super(context);
		
		// adds text to the layout by adding a TextView
		this.step = step;
		TextView temp = new TextView(context);
		temp.setText(step.getNumber() + ": " + step.getText());
		temp.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		this.addView(temp);
		
		//this.setBackgroundColor(Color.RED);
	}
	
	

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed,l,t,r,b);
		// TODO Auto-generated method stub	
	}

}
