/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
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
		
		for (int i = 0; i < stowageItems.size(); i++) {
			StowageItem s = stowageItems.get(i);
			
			TableRow newRow = new TableRow(context);
			
			TextView bin = new TextView(context);
		    bin.setText(s.getBinCode());
		    
		    TextView item = new TextView(context);
		    item.setText(s.getName());
		    
		    TextView quant = new TextView(context);
		    quant.setText(s.getQuantity());
		    
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

}
