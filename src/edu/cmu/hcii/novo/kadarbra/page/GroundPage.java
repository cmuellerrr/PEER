/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

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
		
		
		TextView temp = new TextView(context);
		temp.setText("This feature is not yet implemented");
		temp.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		
		this.addView(temp);
				

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
