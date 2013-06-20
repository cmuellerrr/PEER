package edu.cmu.hcii.novo.kadarbra;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StepPage extends LinearLayout{
	String stepText;
	
	
	public StepPage(Context context, String text) {
		super(context);
		//this.setText(text);
		
		stepText = text;
		// TODO Auto-generated constructor stub
		TextView temp = new TextView(context);
		temp.setText(stepText);
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
