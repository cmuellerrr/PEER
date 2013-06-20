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
	}
	
	
	public void updateStep(int step){
		curStep = step;
		invalidate();
	}
	
	@Override
	public void onDraw(Canvas c){	
		c.drawText(""+curStep, 0, 0, p);
	}
	
}
