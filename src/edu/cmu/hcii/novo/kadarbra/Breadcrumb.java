package edu.cmu.hcii.novo.kadarbra;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Breadcrumb extends View{


	int curStep = 0;
	int totalSteps = 0;
	
	Paint p; // paint used for drawing

	// constructor
	public Breadcrumb(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	
	// initializes the paint used by this class for drawing
	private void init(){
		p = new Paint();
		p.setColor(Color.CYAN);
		p.setTextSize(50f);
		
	}
	
	// sets the current step
	public void setCurrentStep(int step){
		curStep = step;
		invalidate(); // whenever invalidate is called, onDraw is called
	}

	// sets the total # of steps
	public void setTotalSteps(int totsteps){
		totalSteps = totsteps;
	}
	
	// draws stuff on the view
	@Override
	public void onDraw(Canvas c){	
		c.drawText(curStep+"/"+totalSteps, 50, 50, p);
	}
	
}
