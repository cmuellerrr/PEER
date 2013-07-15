/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import edu.cmu.hcii.novo.kadarbra.R;

/**
 * @author Chris
 *
 */
public class GroundPage extends LinearLayout {

	/**
	 * @param context
	 */
	public GroundPage(Context context) {
		super(context);
		
		LayoutInflater.from(context).inflate(R.layout.call_ground, this);
		
		this.setGravity(Gravity.CENTER);
	}

	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public GroundPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

}
