/**
 * 
 */
package edu.cmu.hcii.peer.page;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.R;
import edu.cmu.hcii.peer.util.FontManager;
import edu.cmu.hcii.peer.util.ViewFactory;
import edu.cmu.hcii.peer.util.FontManager.FontStyle;

/**
 * @author Chris
 *
 */
public class CycleSelectPage extends LinearLayout implements DrawerPageInterface {

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
        	page.addView(ViewFactory.getCycleSelect(context, i+1));
        }
        
        //TODO hmm, specifying the parent on inflate isn't working. 
        //But it does appear to center correctly
        this.addView(page);
        this.setGravity(Gravity.CENTER);
        
        initFonts();
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
	 * Setup the custom fonts for this view.
	 */
	private void initFonts() {
		FontManager fm = FontManager.getInstance(getContext().getAssets());

		((TextView)findViewById(R.id.cycleSelectTitle)).setTypeface(fm.getFont(FontStyle.BODY));
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


	/**
	 * Returns name of drawer type
	 */
	@Override
	public String getDrawerType() {
		return DrawerPageInterface.DRAWER_CYCLE_SELECT;
	}
}
