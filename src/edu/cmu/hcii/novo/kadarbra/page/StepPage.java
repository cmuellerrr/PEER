package edu.cmu.hcii.novo.kadarbra.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.R;
import edu.cmu.hcii.novo.kadarbra.structure.ExecNote;
import edu.cmu.hcii.novo.kadarbra.structure.Step;

public class StepPage extends LinearLayout {
	private Step parent;
	private Step step;
	
	/**
	 * constructs a step page, which is a layout object that can have children objects - 
	 * the type of layout object used will change in the future (probably a vertical ViewPager)
	 * 
	 * If there is no parent, it needs to be passed a null
	 * 
	 * @param context
	 * @param step
	 * @param parent
	 */
	public StepPage(Context context, Step step, Step parent) {
		super(context);
		this.setOrientation(VERTICAL);
		
		this.step = step;
		this.parent = parent;
		
		setupExecutionNotes();
		
		if (parent != null) {
			TextView parentView = new TextView(context);
			parentView.setText(parent.getNumber() + ": " + parent.getText());
			parentView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			this.addView(parentView);
		}
		
		TextView subView = new TextView(context);
		subView.setText(step.getNumber() + ": " + step.getText());
		subView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		this.addView(subView);
	}
	
	

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed,l,t,r,b);
		// TODO Auto-generated method stub	
	}

	/**
	 * 
	 * @return step
	 */
	public Step getStep(){
		return step;
	}
	
	/**
	 * 
	 * @return parent
	 */
	public Step getStepParent(){
		return parent;
	}

	/**
	 * Sets up the execution notes to be displayed for this step.
	 * If there is a parent step, show that one too.
	 */
	private void setupExecutionNotes() {
		if (parent != null) setupExecutionNote(parent.getExecNote());
		setupExecutionNote(step.getExecNote());
	}
	
	
	
	/**
	 * Add the given execution note to the step page.
	 * 
	 * @param note the note to display
	 */
	private void setupExecutionNote(ExecNote note) {
		if (note != null) {
			final LayoutInflater inflater = LayoutInflater.from(getContext());
			
			TextView title = (TextView) inflater.inflate(R.layout.execution_note_header, (ViewGroup) this.getParent(), false);
			this.addView(title);

			TextView noteView = (TextView) inflater.inflate(R.layout.execution_note_text, (ViewGroup) this.getParent(), false);
			noteView.setText(note.getText());
			this.addView(noteView);
		}
	}

}
