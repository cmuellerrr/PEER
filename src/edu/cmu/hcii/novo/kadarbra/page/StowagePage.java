/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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
		
		TextView title = new TextView(context);
		title.setText("Stowage Instructions");
		title.setTextSize(40);
		this.addView(title);
		
		this.addView(getHeaderRow(context));
		
		for (int i = 0; i < stowageItems.size(); i++) {
			StowageItem s = stowageItems.get(i);
			
			TableRow newRow = new TableRow(context);
			
			TextView bin = new TextView(context);
		    bin.setText(s.getBinCode());
		    
		    TextView item = new TextView(context);
		    item.setText(s.getName());
		    
		    TextView quant = new TextView(context);
		    quant.setText(String.valueOf(s.getQuantity()));
		    
		    TextView code = new TextView(context);
		    code.setText(s.getItemCode());
		    
		    TextView notes = new TextView(context);
		    notes.setText(s.getName());
		    
		    newRow.addView(bin);
		    newRow.addView(item);
		    newRow.addView(quant);
		    newRow.addView(code);
		    newRow.addView(notes);
		    this.addView(newRow);
		}
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
}
