package edu.cmu.hcii.peer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * This is a progress bar that displays the proportion of the procedure steps that you have completed
 * 
 * @author Chris
 */
public class Breadcrumb extends View {

	int w = 0; // width of view
	int h = 0; // height of view
	float progress = 0; // pixel size of progress bar that is filled in
	float curStep = 0; // current step
	float totalSteps = 0; // total steps in procedure
	Paint p; // paint used for drawing

	
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
	
	
	
	/**
	 * Sets the current step
	 * @param step
	 */
	public void setCurrentStep(int step){
		curStep = step;
		setProgress();
		invalidate(); // whenever invalidate is called, onDraw is called
	}
	
	

	/**
	 * Sets the total # of steps
	 * @param total steps
	 */
	public void setTotalSteps(int total){
		totalSteps = total;
		setProgress();
	}
	
	
	/**
	 * Set progress of steps
	 */
	private void setProgress() {
		progress = curStep > 0 ? (curStep / totalSteps) * w : 0;
	}
	
	
	/**
	 * Performs drawing of progress bar 
	 */
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
