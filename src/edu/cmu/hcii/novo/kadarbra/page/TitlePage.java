<<<<<<< HEAD
/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A layout to represent a procedure title page.  
 * Displays basic info like objective and duration.
 * 
 * @author Chris
 *
 */
public class TitlePage extends LinearLayout {

	private String number;
	private String title;
	private String objective;
	private String duration;
	
	/**
	 * @param context
	 */
	public TitlePage(Context context, String number, String title, String objective, String duration) {
		super(context);
		this.setOrientation(VERTICAL);

		this.number = number;
		this.title = title;
		this.objective = objective;
		this.duration = duration;
		
		TextView titleView = new TextView(context);
		titleView.setText(number + " " + title);
		titleView.setTextSize(40);
		titleView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(titleView);
		
		TextView objView = new TextView(context);
		objView.setText(objective);
		objView.setTextSize(30);
		objView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(objView);
		
		TextView durView = new TextView(context);
		durView.setText(duration);
		durView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(durView);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public TitlePage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

}
=======
/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A layout to represent a procedure title page.  
 * Displays basic info like objective and duration.
 * 
 * @author Chris
 *
 */
public class TitlePage extends LinearLayout {

	private String number;
	private String title;
	private String objective;
	private String duration;
	
	/**
	 * @param context
	 */
	public TitlePage(Context context, String number, String title, String objective, String duration) {
		super(context);
		this.setOrientation(VERTICAL);

		this.number = number;
		this.title = title;
		this.objective = objective;
		this.duration = duration;
		
		TextView titleView = new TextView(context);
		titleView.setText(number + " " + title);
		titleView.setTextSize(40);
		titleView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(titleView);
		
		TextView objView = new TextView(context);
		objView.setText(objective);
		objView.setTextSize(30);
		objView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(objView);
		
		TextView durView = new TextView(context);
		durView.setText(duration);
		durView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(durView);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public TitlePage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

}
>>>>>>> 79203f87352fca5faf8be084e09769bdcff1a318
