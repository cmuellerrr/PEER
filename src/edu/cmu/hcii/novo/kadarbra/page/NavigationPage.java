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
import edu.cmu.hcii.novo.kadarbra.structure.Cycle;
import edu.cmu.hcii.novo.kadarbra.structure.ProcedureItem;
import edu.cmu.hcii.novo.kadarbra.structure.Step;

/**
 * A layout for a navigation page.  Displays all parent 
 * steps.
 * 
 * @author Chris
 *
 */
public class NavigationPage extends LinearLayout {

	private List<ProcedureItem> steps;
	private int current;
	
	/**
	 * @param context
	 */
	public NavigationPage(final Context context, List<ProcedureItem> steps, int current) {
		super(context);
		this.setOrientation(VERTICAL);
		
		this.steps = steps;
		this.current = current;

		for (int i = 0; i < steps.size(); i++) {			
			if (steps.get(i).isCycle()) {
				Cycle c = (Cycle)steps.get(i);
				for (int j = 0; j < c.getNumChildren(); j++) {
					//TODO this will break on cycles within cycles
					addCycleStep(i+j, (Step) c.getChild(j), c.getReps());
				}
				
				//TODO add cycle marker
				
			} else {
				addStep(i, (Step) steps.get(i));
			}
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
	 * Add a basic step
	 * @param context
	 * @param index
	 * @param s
	 */
	private void addStep(final int index, Step s) {
		TextView newStep = new TextView(getContext());
		newStep.setText(s.getNumber() + " " + s.getText());
		newStep.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		
		if (index == current) newStep.setTextColor(Color.CYAN);			
		
		newStep.setOnClickListener(new OnClickListener(){

			//The step number sent will be 0 indexed.
			//So step 1 will send over the index of 0.
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent("command");
				intent.putExtra("msg", "navigate");
				intent.putExtra("step", index);
				getContext().sendBroadcast(intent);
			}
			
		});
		
		this.addView(newStep);
	}
	
	
	
	/**
	 * Add a step within a cycle.  The main difference here is that it needs to 
	 * show how many repeditions and have a different click behavior.
	 * 
	 * @param context
	 * @param index
	 * @param s
	 * @param reps
	 */
	private void addCycleStep(final int index, Step s, final int reps) {		
		TextView newStep = new TextView(getContext());
		newStep.setText(s.getNumber() + " " + s.getText() + " -- X" + reps);
		newStep.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		
		if (index == current) newStep.setTextColor(Color.CYAN);			
		
		newStep.setOnClickListener(new OnClickListener(){

			//The step number sent will be 0 indexed.
			//So step 1 will send over the index of 0.
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent("command");
				intent.putExtra("msg", "navigate");
				intent.putExtra("step", index);
				intent.putExtra("reps", reps);
				getContext().sendBroadcast(intent);
			}
			
		});
		
		this.addView(newStep);
	}
	
	
	/**
	 * @return the steps
	 */
	public List<ProcedureItem> getSteps() {
		return steps;
	}



	/**
	 * @param steps the steps to set
	 */
	public void setSteps(List<ProcedureItem> steps) {
		this.steps = steps;
	}
}
