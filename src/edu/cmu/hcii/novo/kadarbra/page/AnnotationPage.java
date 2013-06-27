/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A layout for stowage notes.  Displays information 
 * as a table.
 * 
 * @author Chris
 *
 */
public class AnnotationPage extends LinearLayout {


	
	/**
	 * @param context
	 */
	public AnnotationPage(Context context) {
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
	public AnnotationPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	
	
	
}
