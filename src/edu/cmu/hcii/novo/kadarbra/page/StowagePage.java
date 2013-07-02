/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.R;
import edu.cmu.hcii.novo.kadarbra.structure.ExecNote;
import edu.cmu.hcii.novo.kadarbra.structure.StowageItem;

/**
 * A layout for stowage notes.  Displays information 
 * as a table.
 * 
 * @author Chris
 *
 */
public class StowagePage extends TableLayout {

	private List<StowageItem> stowageItems;
	
	
	
	/**
	 * @param context
	 */
	public StowagePage(Context context, List<StowageItem> stowageItems) {
		super(context);
		
		this.stowageItems = stowageItems;
		
		LayoutInflater inflater = LayoutInflater.from(context);
        View page = (View)inflater.inflate(R.layout.stow_notes_page, null);
        
        LinearLayout notesList = (LinearLayout) page.findViewById(R.id.stowNotesList);
		
		for (int i = 0; i < stowageItems.size(); i++) {
			StowageItem s = stowageItems.get(i);
			
			
			View newNote = (View) inflater.inflate(R.layout.stow_note, null);
			
			((TextView)newNote.findViewById(R.id.stowNoteBinCode)).setText(s.getBinCode());
			((TextView)newNote.findViewById(R.id.stowNoteItem)).setText(s.getName());
			((TextView)newNote.findViewById(R.id.stowNoteQuantity)).setText(String.valueOf(s.getQuantity()));
			((TextView)newNote.findViewById(R.id.stowNoteItemCode)).setText(s.getItemCode());
			((TextView)newNote.findViewById(R.id.stowNoteNotes)).setText(s.getText());
			
			//TODO need to add image
			
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
	 * Set up the header row for the stowage page.
	 * 
	 * @param context
	 * @return the header row
	 */
	private static TableRow getHeaderRow(Context context) {
		TableRow header = new TableRow(context);
		
		TextView binHeader = new TextView(context);
		binHeader.setText("Bin Code");
	
		TextView itemHeader = new TextView(context);
		itemHeader.setText("Item");
		
		TextView qtyHeader = new TextView(context);
		qtyHeader.setText("Qty");
		
		TextView codeHeader = new TextView(context);
		codeHeader.setText("Code");
		
		TextView notesHeader = new TextView(context);
		notesHeader.setText("Notes");
		
		header.addView(binHeader);
		header.addView(itemHeader);
		header.addView(qtyHeader);
		header.addView(codeHeader);
		header.addView(notesHeader);
		return header;
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
