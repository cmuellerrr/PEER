/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import java.util.List;

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

		//The list is 1 based
		int stepNumber = 1;
		for (int i = 0; i < steps.size(); i++) {
			if (steps.get(i).isCycle()) {
				Cycle c = (Cycle)steps.get(i);
				this.addView(getCycle(stepNumber, c));
				stepNumber += c.getNumChildren();
				
			} else {
				this.addView(getStep(stepNumber, (Step) steps.get(i), 1));
				stepNumber++;
			}
		}
		
		this.setGravity(Gravity.CENTER);
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
	 * 
	 * TODO combine this with addCycleStep
	 * 
	 * @param context
	 * @param index
	 * @param s
	 */
	private ViewGroup getStep(int index, Step s, int reps) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		ViewGroup newStep = (ViewGroup) inflater.inflate(R.layout.nav_item, null);
		
		((TextView)newStep.findViewById(R.id.navItemNumber)).setText("STEP " + s.getNumber());
		((TextView)newStep.findViewById(R.id.navItemText)).setText(s.getText());
		
		//If a lone step, set a margin so that it matches those which are a the cycle
		if (reps < 2) {
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(30, 0, 0, 0);
			newStep.setLayoutParams(params);
		}
		
		if (index == current) ((TextView)newStep.findViewById(R.id.navItemNumber)).setTextColor(getResources().getColor(R.color.main));
		
		final String step = String.valueOf(index);
		
		newStep.setOnClickListener(new OnClickListener(){

			//The step number sent will be 0 indexed.
			//So step 1 will send over the index of 0.
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent("command");
				intent.putExtra("msg", MessageHandler.COMMAND_GO_TO_STEP);
				intent.putExtra("str", step);
				getContext().sendBroadcast(intent);
			}
			
		});
		
		return newStep;
	}
	
	
	private ViewGroup getCycle(int index, Cycle c) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		ViewGroup newCycle = (ViewGroup) inflater.inflate(R.layout.nav_item_cycle, null);
		
		((TextView)newCycle.findViewById(R.id.navCycleCount)).setText("x" + c.getReps());		
		ViewGroup steps = (ViewGroup) newCycle.findViewById(R.id.navCycleSteps);
		
		for (int j = 0; j < c.getNumChildren(); j++) {
			//TODO this will break on cycles within cycles
			steps.addView(getStep(index, (Step) c.getChild(j), c.getReps()));
			index++;
		}
		
		return newCycle;
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
