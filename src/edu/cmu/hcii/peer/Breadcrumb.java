package edu.cmu.hcii.peer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class Breadcrumb extends View {

	int w = 0;
	int h = 0;
	float progress = 0;
	float curStep = 0;
	float totalSteps = 0;
	Paint p; // paint used for drawing

	
	
	// constructor
	public Breadcrumb(Context context, AttributeSet attrs) {
		super(context, attrs);
		p = new Paint();

		final View bc = this;
		this.getViewTreeObserver().addOnGlobalLayoutListener( 
			    new OnGlobalLayoutListener(){

			        @Override
			        public void onGlobalLayout() {
			            // gets called after layout has been done but before display
			            // so we can get the height

			        	w = bc.getWidth();
			            h = bc.getHeight() - 2;

			            bc.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			        }

			});
	}
	
	
	
	// sets the current step
	public void setCurrentStep(int step){
		curStep = step;
		setProgress();
		invalidate(); // whenever invalidate is called, onDraw is called
	}
	
	

	// sets the total # of steps
	public void setTotalSteps(int total){
		totalSteps = total;
		setProgress();
	}
	
	
	
	private void setProgress() {
		progress = curStep > 0 ? (curStep / totalSteps) * w : 0;
	}
	
	
	
	@Override
    public void onDraw(Canvas canvas) {
        p.setColor(0xFFAAAAAA);
        p.setStrokeWidth(1);
        canvas.drawRect(0, 1, w, h, p);
        
        p.setStrokeWidth(0);
        p.setColor(0xFFA4ECE8);
        canvas.drawRect(0, 1, progress, h, p );        
    }
}
