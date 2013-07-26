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
 * A layout for the selection of a cycle.  This appears when navigating to
 * a step which gets repeated.  A menu is brought up asking which 
 * instance of that step to navigate to.
 * 
 * @author Chris
 *
 */
public class CycleSelectPage extends LinearLayout implements DrawerPageInterface {

	
	
	/**
	 * @param context
	 */
	public CycleSelectPage(Context context, int reps, int step) {
		super(context);
		
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
	 * Returns name of drawer type
	 */
	@Override
	public String getDrawerType() {
		return DrawerPageInterface.DRAWER_CYCLE_SELECT;
	}
}
