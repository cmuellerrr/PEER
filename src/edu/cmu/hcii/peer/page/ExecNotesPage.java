/**
 * 
 */
package edu.cmu.hcii.peer.page;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.R;
import edu.cmu.hcii.peer.structure.ExecNote;
import edu.cmu.hcii.peer.util.FontManager;
import edu.cmu.hcii.peer.util.ViewFactory;
import edu.cmu.hcii.peer.util.FontManager.FontStyle;

/**
 * A layout for the execution notes page. Displays all
 * execution notes for a procedure.
 * 
 * @author Chris
 *
 */
public class ExecNotesPage extends LinearLayout {
	
	
	
	/**
	 * @param context
	 */
	public ExecNotesPage(Context context, List<ExecNote> execNotes) {
		super(context);
		
		LayoutInflater inflater = LayoutInflater.from(context);
        View page = (View)inflater.inflate(R.layout.ex_notes_page, this);
        
        LinearLayout notesList = (LinearLayout) page.findViewById(R.id.exNotesList);

		for (int i = 0; i < execNotes.size(); i++) {
			notesList.addView(ViewFactory.getExecutionNoteOverview(context, execNotes.get(i)));
		}
		
		initFonts();
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
	 * Setup the custom fonts for this view.
	 */
	private void initFonts() {
		FontManager fm = FontManager.getInstance(getContext().getAssets());
		
		((TextView)findViewById(R.id.exNotesAside)).setTypeface(fm.getFont(FontStyle.BODY));
		((TextView)findViewById(R.id.exNotesTitle)).setTypeface(fm.getFont(FontStyle.HEADER));
	}
}
