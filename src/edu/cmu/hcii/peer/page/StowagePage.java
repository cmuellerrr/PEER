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
	private Map<String, List<StowageItem>> stowageItems;
	
	
	
	/**
	 * @param context
	 */
	public StowagePage(Context context, Map<String, List<StowageItem>> stowageItems) {
		super(context);
		
		this.stowageItems = stowageItems;
		
		LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup page = (ViewGroup)inflater.inflate(R.layout.stowage_page, this);
        
        for (String key : stowageItems.keySet()) {
        	page.addView(ViewFactory.getStowageTable2(context, key, stowageItems.get(key)));
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
	 * @return the stowageItems
	 */
	public Map<String, List<StowageItem>> getStowageItems() {
		return stowageItems;
	}



	/**
	 * @param stowageItems the stowageItems to set
	 */
	public void setStowageItems(Map<String, List<StowageItem>> stowageItems) {
		this.stowageItems = stowageItems;
	}


	/**
	 * Returns type of drawer page
	 */
	@Override
	public String getDrawerType() {
		return DrawerPageInterface.DRAWER_STOWAGE;
	}
}
