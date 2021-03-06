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
import edu.cmu.hcii.peer.util.FontManager;
import edu.cmu.hcii.peer.util.FontManager.FontStyle;

/**
 * A layout to represent a procedure title page.  
 * Displays basic info like objective and duration.
 * 
 * @author Chris
 *
 */
public class CoverPage extends FrameLayout {
	
	
	
	/**
	 * @param context
	 */
	public CoverPage(Context context, String number, String title, String objective, String duration) {
		super(context);

		LayoutInflater inflater = LayoutInflater.from(context);
        View page = (View)inflater.inflate(R.layout.cover_page, this);
        
        ((TextView)page.findViewById(R.id.procedureName)).setText(title.toUpperCase());
        ((TextView)page.findViewById(R.id.objective)).setText(objective);
        ((TextView)page.findViewById(R.id.duration)).setText(duration);
        
        initFonts();
	}

	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public CoverPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * Setup the custom fonts for this view.
	 */
	private void initFonts() {
		FontManager fm = FontManager.getInstance(getContext().getAssets());
		
		((TextView)findViewById(R.id.procedureName)).setTypeface(fm.getFont(FontStyle.HEADER));
        ((TextView)findViewById(R.id.objectiveTitle)).setTypeface(fm.getFont(FontStyle.HEADER));
        ((TextView)findViewById(R.id.durationTitle)).setTypeface(fm.getFont(FontStyle.HEADER));
        ((TextView)findViewById(R.id.objective)).setTypeface(fm.getFont(FontStyle.BODY));
        ((TextView)findViewById(R.id.duration)).setTypeface(fm.getFont(FontStyle.BODY));
	}
}
