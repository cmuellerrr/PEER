/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.MessageHandler;
import edu.cmu.hcii.novo.kadarbra.R;

/**
 * @author Chris
 *
 */
public class CycleSelectPage extends LinearLayout {

	private int reps;
	private int step;
	
	/**
	 * @param context
	 */
	public CycleSelectPage(Context context, int reps, int step) {
		super(context);
		
		this.reps = reps;
		this.step = step;
		
		LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup page = (ViewGroup)inflater.inflate(R.layout.cycle_select_page, null);
        
        for (int i = 0; i < reps; i++) {
        	final int rep = i+1;
        	final int s = step;
        	
        	TextView newItem = (TextView)inflater.inflate(R.layout.cycle_select_item, null);       	
        	newItem.setText("CYCLE " + (i+1));
        	newItem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent("command");
					intent.putExtra("msg", MessageHandler.COMMAND_GO_TO_STEP);
					intent.putExtra("str", rep);
					getContext().sendBroadcast(intent);
				}
        		
        	});
        	
        	page.addView(newItem);
        }
        this.addView(page);
        this.setGravity(Gravity.CENTER);
	}
	
	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public CycleSelectPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @return the reps
	 */
	public int getReps() {
		return reps;
	}
	
	

	/**
	 * @param reps the reps to set
	 */
	public void setReps(int reps) {
		this.reps = reps;
	}

	
	
	/**
	 * @return the step
	 */
	public int getStep() {
		return step;
	}

	
	
	/**
	 * @param step the step to set
	 */
	public void setStep(int step) {
		this.step = step;
	}
}
