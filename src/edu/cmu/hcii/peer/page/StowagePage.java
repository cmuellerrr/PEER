/**
 * 
 */
package edu.cmu.hcii.peer.page;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.R;
import edu.cmu.hcii.peer.structure.StowageItem;
import edu.cmu.hcii.peer.util.FontManager;
import edu.cmu.hcii.peer.util.ViewFactory;
import edu.cmu.hcii.peer.util.FontManager.FontStyle;

/**
 * A layout for stowage notes.  Displays information 
 * as a table.
 * 
 * @author Chris
 *
 */
public class StowagePage extends TableLayout implements DrawerPageInterface {

		
	
	/**
	 * @param context
	 */
	public StowagePage(Context context, Map<String, List<StowageItem>> stowageItems) {
		super(context);
				
		LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup page = (ViewGroup)inflater.inflate(R.layout.stowage_page, this);
        
        for (String key : stowageItems.keySet()) {
        	page.addView(ViewFactory.getLinearLayoutStowageTable(context, key, stowageItems.get(key)));
        }
		
		initFonts();
	}

	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public StowagePage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * Setup the custom fonts for this view.
	 */
	private void initFonts() {
		FontManager fm = FontManager.getInstance(getContext().getAssets());
		
		((TextView)findViewById(R.id.stowNotesTitle)).setTypeface(fm.getFont(FontStyle.HEADER));
	}

	

	/**
	 * Returns type of drawer page
	 */
	@Override
	public String getDrawerType() {
		return DrawerPageInterface.DRAWER_STOWAGE;
	}
}
