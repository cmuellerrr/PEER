/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
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
	private List<StowageItem> stowageItems;
	
	
	
	/**
	 * @param context
	 */
	public StowagePage(Context context, List<StowageItem> stowageItems) {
		super(context);
		
		this.stowageItems = stowageItems;
		
		LayoutInflater inflater = LayoutInflater.from(context);
        View page = (View)inflater.inflate(R.layout.stow_notes_page, null);
        
        TableLayout notesList = (TableLayout) page.findViewById(R.id.stowNotesList);
		
		for (int i = 0; i < stowageItems.size(); i++) {
			StowageItem s = stowageItems.get(i);
						
			View newNote = (View) inflater.inflate(R.layout.stow_note, null);
			
			((TextView)newNote.findViewById(R.id.stowNoteBinCode)).setText(s.getBinCode());
			((TextView)newNote.findViewById(R.id.stowNoteItem)).setText(s.getName());
			((TextView)newNote.findViewById(R.id.stowNoteQuantity)).setText(String.valueOf(s.getQuantity()));
			((TextView)newNote.findViewById(R.id.stowNoteItemCode)).setText(s.getItemCode());
			((TextView)newNote.findViewById(R.id.stowNoteNotes)).setText(s.getText());
			
			try {
				InputStream is = getContext().getAssets().open("procedures/references/" + s.getUrl());
				Drawable d = Drawable.createFromStream(is, null);
				((ImageView)newNote.findViewById(R.id.stowNoteImage)).setImageDrawable(d);
				
			} catch(Exception e) {
				Log.e(TAG, "Error adding reference image to stowage note", e);
			}
			
			notesList.addView(newNote);
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



	/**
	 * @return the stowageItems
	 */
	public List<StowageItem> getStowageItems() {
		return stowageItems;
	}



	/**
	 * @param stowageItems the stowageItems to set
	 */
	public void setStowageItems(List<StowageItem> stowageItems) {
		this.stowageItems = stowageItems;
	}
}
