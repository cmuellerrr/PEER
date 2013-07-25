/**
 * 
 */
package edu.cmu.hcii.peer.page;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.R;

/**
 * A layout for stowage notes.  Displays information 
 * as a table.
 * 
 * @author Chris
 *
 */
public class CompletionPage extends FrameLayout {


	
	/**
	 * @param context
	 */
	public CompletionPage(Context context) {
		super(context);
		
		LayoutInflater.from(context).inflate(R.layout.complete_page, this);
	}

	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public CompletionPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
}
