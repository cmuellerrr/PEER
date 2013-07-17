package edu.cmu.hcii.novo.kadarbra.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Timer extends TextView{
	private long startTime;
	
	
	public Timer(Context context) {
		super(context);
		
		Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/Lifeline.ttf");
		setTypeface(typeFace);
		
	}

	public void init(){
		startTime = System.currentTimeMillis();
	}
	
	public void update(){
		
	}
	
	
}
