/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import edu.cmu.hcii.novo.kadarbra.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
		
		LayoutInflater inflater = LayoutInflater.from(context);
        View page = (View)inflater.inflate(R.layout.call_ground, null);
		
		this.addView(page);
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
