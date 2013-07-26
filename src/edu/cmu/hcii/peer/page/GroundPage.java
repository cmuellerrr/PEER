/**
 * 
 */
package edu.cmu.hcii.peer.page;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import edu.cmu.hcii.peer.util.ViewFactory;

/**
 * A layout for the page when users are calling ground. 
 * 
 * Just a static image for now.
 * 
 * @author Chris
 *
 */
public class GroundPage extends LinearLayout implements DrawerPageInterface{

	
	
	/**
	 * @param context
	 */
	public GroundPage(Context context) {
		super(context);
		
		this.addView(ViewFactory.getCallGround(context));
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}

	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public GroundPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}



	/**
	 * Returns name of drawer type
	 */
	@Override
	public String getDrawerType() {
		return DrawerPageInterface.DRAWER_GROUND;
	}
}
