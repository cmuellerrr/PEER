/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.structure.Step;

/**
 * A layout for a navigation page.  Displays all parent 
 * steps.
 * 
 * @author Chris
 *
 */
public class NavigationPage extends LinearLayout {

	private List<Step> steps;
	
	/**
	 * @param context
	 */
	public NavigationPage(final Context context, List<Step> steps, int current_step) {
		super(context);
		this.setOrientation(VERTICAL);
		
		this.steps = steps;

		for (int i = 0; i < steps.size(); i++) {
			final int stepNum = i;
			Step s = steps.get(i);
			
			TextView newStep = new TextView(context);
			newStep.setText(s.getNumber() + " " + s.getText());
			newStep.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			
			if (i == current_step) newStep.setTextColor(Color.CYAN);			
			
			newStep.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent("command");
					intent.putExtra("msg", "navigate");
					intent.putExtra("step", stepNum);
					context.sendBroadcast(intent);
				}
				
			});
			
			this.addView(newStep);
		}
	}

	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public NavigationPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the steps
	 */
	public List<Step> getSteps() {
		return steps;
	}



	/**
	 * @param steps the steps to set
	 */
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
}
