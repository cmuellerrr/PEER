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
	
	Paint p;

	public Breadcrumb(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	
	
	private void init(){
		p = new Paint();
		p.setColor(Color.BLACK);
		p.setTextSize(50f);
		
	}
	
	
	public void setCurrentStep(int step){
		curStep = step;
		invalidate();
	}

	public void setTotalSteps(int totsteps){
		totalSteps = totsteps;
	}
	
	@Override
	public void onDraw(Canvas c){	
		c.drawText(curStep+"/"+totalSteps, 50, 50, p);
	}
	
}
