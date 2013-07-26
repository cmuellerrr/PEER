/**
 * 
 */
package edu.cmu.hcii.peer.page;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import edu.cmu.hcii.peer.structure.Cycle;
import edu.cmu.hcii.peer.structure.ProcedureItem;
import edu.cmu.hcii.peer.structure.Step;
import edu.cmu.hcii.peer.util.ViewFactory;

/**
 * A layout for a navigation page.  Displays all parent 
 * steps.
 * 
 * @author Chris
 *
 */
public class NavigationPage extends LinearLayout implements DrawerPageInterface {

	
	
	/**
	 * @param context
	 */
	public NavigationPage(final Context context, List<ProcedureItem> steps, int curStepIndex) {
		super(context);
		this.setOrientation(VERTICAL);

		//The list is 1 based
		//TODO this whole indexing is kind of f-ed.  Shouldn't we just use the 
		//actual step number strings?
		int stepNumber = 1;
		for (int i = 0; i < steps.size(); i++) {
			if (steps.get(i).isCycle()) {
				Cycle c = (Cycle)steps.get(i);
				this.addView(ViewFactory.getNavigationCycle(context, curStepIndex, stepNumber, c));
				stepNumber += c.getNumChildren();
				
			} else {
				this.addView(ViewFactory.getNavigationStep(context, curStepIndex, stepNumber, (Step)steps.get(i), 1));
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
	 * Returns type of interface
	 */
	@Override
	public String getDrawerType() {
		return DrawerPageInterface.DRAWER_NAVIGATION;
	}
}
