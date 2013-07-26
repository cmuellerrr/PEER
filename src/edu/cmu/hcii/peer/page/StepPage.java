package edu.cmu.hcii.peer.page;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cmu.hcii.novo.kadarbra.R;
import edu.cmu.hcii.peer.structure.Callout;
import edu.cmu.hcii.peer.structure.Reference;
import edu.cmu.hcii.peer.structure.Step;
import edu.cmu.hcii.peer.util.FontManager;
import edu.cmu.hcii.peer.util.ViewFactory;
import edu.cmu.hcii.peer.util.FontManager.FontStyle;

/**
 * A layout for a normal step.  A step page includes the normal 
 * step text along with any step-specific info like execution notes
 * and cautions, any information about its parent, and 
 * optional conditional text.
 * 
 * @author Chris
 *
 */
public class StepPage extends LinearLayout {
	private static final String TAG = "StepPage";
	
	private int cycle;
	private Step parent;
	private Step step;
	
	
	
	/**
	 * Constructs a step page, which is a layout object that can have children objects - 
	 * the type of layout object used will change in the future (probably a vertical ViewPager)
	 * 
	 * NOTE: If there is no parent, it needs to be passed a null
	 * 
	 * @param context
	 * @param step
	 * @param parent
	 */
	public StepPage(Context context, Step step, Step parent, int cycle) {
		super(context);
		this.setOrientation(VERTICAL);
		
		String cycleNameUppercase = getResources().getString(R.string.cycle_display_name).substring(0, 1).toUpperCase() + 
				getResources().getString(R.string.cycle_display_name).substring(1);
		String cycleLabel = (cycle > 0 ? cycleNameUppercase + " " + cycle : "");
		
		Log.d(TAG, "Setting up step page " + step.getNumber() + " " + cycleLabel);
		
		this.step = step;
		this.parent = parent;
		this.cycle = 0;
		
		LayoutInflater inflater = LayoutInflater.from(context);
        View page = (View)inflater.inflate(R.layout.step_page, this);
		
		//Add in an indicator if in a cycle
		if (cycle > 0) {
			((TextView)page.findViewById(R.id.stepCycleNumber)).setText(cycleLabel);
			
		} else {
			ViewGroup p = (ViewGroup)page.findViewById(R.id.stepCycleNumber).getParent();
			p.removeView(page.findViewById(R.id.stepCycleNumber));
		}
		
		//Set up the parent
		if (parent != null) {			
			((TextView)page.findViewById(R.id.parentNumber)).setText(parent.getNumber());
			((TextView)page.findViewById(R.id.parentText)).setText(parent.getText().toUpperCase());
		} else {
			ViewGroup p = (ViewGroup)page.findViewById(R.id.parentContainer).getParent();
			p.removeView(page.findViewById(R.id.parentContainer));
		}

		
		//Add callout elements
		ViewGroup cont = ((ViewGroup)page.findViewById(R.id.stepTextContainer));
		setupCallouts(context, cont);
		setupExecutionNotes(context, cont);
		
		
		//Add the normal text
		((TextView)page.findViewById(R.id.stepNumber)).setText(step.getNumber());
		((TextView)page.findViewById(R.id.stepText)).setText(step.getText());

		
		//if it is a conditional
		if (step.isConditional()) {
			Log.v(TAG, "Step has conditional content");
			
			final TextView consText = ((TextView)page.findViewById(R.id.consequentText));
			consText.setText(step.getConsequent());
			
			page.findViewById(R.id.consequentMarker).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					//TODO make this a toggle?
					if (consText.getVisibility() == VISIBLE) {
						consText.setVisibility(GONE);
						((TextView)v).setText(R.string.cond_title_hidden);
					} else {
						consText.setVisibility(VISIBLE);
						((TextView)v).setText(R.string.cond_title_shown);
					}
				}
			});
			
		} else {
			ViewGroup p = (ViewGroup)page.findViewById(R.id.consequentContainer).getParent();
			p.removeView(page.findViewById(R.id.consequentContainer));
		}
		
		//Set up all the other conditional stuff
		setupInput(context, cont);
		setupTimer(context, cont);
		setupReferences(context, cont);
		
		initFonts();
	}
	
	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public StepPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * Setup the custom fonts for this view.
	 */
	private void initFonts() {
		FontManager fm = FontManager.getInstance(getContext().getAssets());
		
		if (findViewById(R.id.parentContainer) != null) {
			((TextView)findViewById(R.id.parentNumber)).setTypeface(fm.getFont(FontStyle.HEADER));
			((TextView)findViewById(R.id.parentText)).setTypeface(fm.getFont(FontStyle.HEADER));
		}
		
		((TextView)findViewById(R.id.stepNumber)).setTypeface(fm.getFont(FontStyle.BODY));
		((TextView)findViewById(R.id.stepText)).setTypeface(fm.getFont(FontStyle.BODY));
		
		if (findViewById(R.id.consequentContainer) != null) {
			((TextView)findViewById(R.id.consequentMarker)).setTypeface(fm.getFont(FontStyle.SELECTABLE));
			((TextView)findViewById(R.id.consequentText)).setTypeface(fm.getFont(FontStyle.BODY));
		}
	}

	
	
	/**
	 * Sets up the execution notes to be displayed for this step.
	 * If there is a parent step, show that one too.
	 */
	private void setupExecutionNotes(Context context, ViewGroup container) {
		if (parent != null && parent.getExecNote() != null) {
			container.addView(ViewFactory.getExecutionNote(context, parent.getExecNote()), 0);
		}
		
		if (step.getExecNote() != null) {
			container.addView(ViewFactory.getExecutionNote(context, step.getExecNote()), 0);
		}
	}
	
	
	
	/**
	 * Setup the step's callouts
	 */
	private void setupCallouts(Context context, ViewGroup container) {
		//TODO repeating code
		if (parent != null) {
			List<Callout> pcalls = parent.getCallouts();
			for (int i = 0; i < pcalls.size(); i++) {
				container.addView(ViewFactory.getCallout(context, pcalls.get(i)), 0);
			}
		}
		
		List<Callout> calls = step.getCallouts();
		for (int i = 0; i < calls.size(); i++) {
			container.addView(ViewFactory.getCallout(context, calls.get(i)), 0);
		}
	}
	
	
	
	/**
	 * Setup the input for the step
	 * 
	 * @param context
	 * @param container
	 */
	private void setupInput(Context context, ViewGroup container) {
		if (step.hasInput()) container.addView(ViewFactory.getInput(context));
	}
	
	
	
	/**
	 * Setup the timer for the step
	 * 
	 * @param context
	 * @param container
	 */
	private void setupTimer(Context context, ViewGroup container) {
		if (step.hasTimer()) container.addView(ViewFactory.getTimer(context));
	}
	
	
	
	/**
	 * Setup the references for the step
	 */
	private void setupReferences(Context context, ViewGroup container) {
		List<Reference> refs = step.getReferences();
		for (int i = 0; i < refs.size(); i++) {
			container.addView(ViewFactory.getReference(context, refs.get(i)));
		}
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
	 * 
	 * @return cycle
	 */
	public int getCycle() {
		return cycle;
	}
}
