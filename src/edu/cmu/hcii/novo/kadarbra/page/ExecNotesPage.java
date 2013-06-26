/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import edu.cmu.hcii.novo.kadarbra.structure.ExecNote;

/**
 * A layout for execution notes. Displays all
 * execution notes for a procedure.
 * 
 * @author Chris
 *
 */
public class ExecNotesPage extends LinearLayout {

	private List<ExecNote> execNotes;
	
	
	
	/**
	 * @param context
	 */
	public ExecNotesPage(Context context, List<ExecNote> execNotes) {
		super(context);
		this.setOrientation(VERTICAL);
		
		this.execNotes = execNotes;

		for (int i = 0; i < execNotes.size(); i++) {
			ExecNote curNote = execNotes.get(i);
			
			TextView temp = new TextView(context);			
			temp.setText(curNote.getNumber() + ": " + curNote.getText());
			temp.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			this.addView(temp);
		}
	}

	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public ExecNotesPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

}
