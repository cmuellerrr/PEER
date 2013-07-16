/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.page;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.R;
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
		
		this.execNotes = execNotes;
		
		LayoutInflater inflater = LayoutInflater.from(context);
        View page = (View)inflater.inflate(R.layout.ex_notes_page, null);
        
        LinearLayout notesList = (LinearLayout) page.findViewById(R.id.exNotesList);

		for (int i = 0; i < execNotes.size(); i++) {
			ExecNote curNote = execNotes.get(i);
			
			View newNote = (View) inflater.inflate(R.layout.ex_note_overall, null);
			
			((TextView)newNote.findViewById(R.id.exNoteNumber)).setText("Step " + curNote.getNumber());
			((TextView)newNote.findViewById(R.id.exNoteText)).setText(curNote.getText());
			
			notesList.addView(newNote);
		}
		
		this.addView(page);
	}

	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public ExecNotesPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the execNotes
	 */
	public List<ExecNote> getExecNotes() {
		return execNotes;
	}



	/**
	 * @param execNotes the execNotes to set
	 */
	public void setExecNotes(List<ExecNote> execNotes) {
		this.execNotes = execNotes;
	}
}
