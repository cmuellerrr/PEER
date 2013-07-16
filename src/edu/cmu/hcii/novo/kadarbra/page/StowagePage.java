/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.R;
import edu.cmu.hcii.novo.kadarbra.structure.StowageItem;

/**
 * A layout for stowage notes.  Displays information 
 * as a table.
 * 
 * @author Chris
 *
 */
public class StowagePage extends TableLayout {
	private static final String TAG = "StowagePage";
	private Map<String, List<StowageItem>> stowageItems;
	
	
	
	/**
	 * @param context
	 */
	public StowagePage(Context context, Map<String, List<StowageItem>> stowageItems) {
		super(context);
		
		this.stowageItems = stowageItems;
		
		LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup page = (ViewGroup)inflater.inflate(R.layout.stow_notes_page, null);
        
        for (String key : stowageItems.keySet()) {
        	page.addView(setupStowageTable(key, stowageItems.get(key)));
        }
		
		this.addView(page);
	}

	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public StowagePage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	
	
	private ViewGroup setupStowageTable(String module, List<StowageItem> items) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		LinearLayout container = (LinearLayout)inflater.inflate(R.layout.stow_notes_table, null);
		TableLayout table = (TableLayout)container.findViewById(R.id.stow_table);
		
		//set table title
		((TextView)container.findViewById(R.id.stow_table_title)).setText(module);
		
		for (int i = 0; i < items.size(); i++) {
			StowageItem s = items.get(i);
						
			TableRow row = (TableRow) inflater.inflate(R.layout.stow_note, null);
			
			((TextView)row.findViewById(R.id.stowNoteBinCode)).setText(s.getBinCode());
			((TextView)row.findViewById(R.id.stowNoteItem)).setText(s.getName());
			((TextView)row.findViewById(R.id.stowNoteQuantity)).setText(String.valueOf(s.getQuantity()));
			((TextView)row.findViewById(R.id.stowNoteItemCode)).setText(s.getItemCode());
			((TextView)row.findViewById(R.id.stowNoteNotes)).setText(s.getText());
			
			try {
				InputStream is = getContext().getAssets().open("procedures/references/" + s.getUrl());
				Drawable d = Drawable.createFromStream(is, null);
				((ImageView)row.findViewById(R.id.stowNoteImage)).setImageDrawable(d);
				
			} catch(Exception e) {
				Log.e(TAG, "Error adding reference image to stowage note", e);
			}
			
			table.addView(row);
		}
		
		return container;
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
}
