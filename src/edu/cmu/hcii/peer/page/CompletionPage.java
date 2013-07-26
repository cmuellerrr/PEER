/**
 * 
 */
package edu.cmu.hcii.peer.page;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import edu.cmu.hcii.novo.kadarbra.R;

/**
 * A layout for the procedure completion page.  It really just 
 * needs to tell them that they are done.
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
