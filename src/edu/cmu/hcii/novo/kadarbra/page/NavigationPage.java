/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
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
	public NavigationPage(Context context, List<Step> steps) {
		super(context);
		this.setOrientation(VERTICAL);
		
		this.steps = steps;
		
		for (int i = 0; i < steps.size(); i++) {
			Step s = steps.get(i);
			TextView newStep = new TextView(context);
			newStep.setText(s.getNumber() + " " + s.getText());
			newStep.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
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

}
