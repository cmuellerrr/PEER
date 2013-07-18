package edu.cmu.hcii.novo.kadarbra.page;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;
import edu.cmu.hcii.novo.kadarbra.R;
import edu.cmu.hcii.novo.kadarbra.structure.Callout;
import edu.cmu.hcii.novo.kadarbra.structure.ExecNote;
import edu.cmu.hcii.novo.kadarbra.structure.Reference;
import edu.cmu.hcii.novo.kadarbra.structure.Reference.RType;
import edu.cmu.hcii.novo.kadarbra.structure.Step;

public class StepPage extends LinearLayout {
	private static final String TAG = "StepPage";
	
	private int cycle;
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
	public StepPage(Context context, Step step, Step parent, int cycle) {
		super(context);
		this.setOrientation(VERTICAL);
		
		String cycleLabel = (cycle > 0 ? "Cycle " + cycle : "");
		
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
		
		//setup the parent
		if (parent != null) {			
			((TextView)page.findViewById(R.id.parentNumber)).setText(parent.getNumber());
			((TextView)page.findViewById(R.id.parentText)).setText(parent.getText().toUpperCase());
		} else {
			ViewGroup p = (ViewGroup)page.findViewById(R.id.parentContainer).getParent();
			p.removeView(page.findViewById(R.id.parentContainer));
		}

		
		//Add callout elements
		ViewGroup cont = ((ViewGroup)page.findViewById(R.id.stepTextContainer));
		setupExecutionNotes(cont);
		setupCallouts(cont);
		
		
		//Add the normal text
		((TextView)page.findViewById(R.id.stepNumber)).setText(step.getNumber());
		((TextView)page.findViewById(R.id.stepText)).setText(step.getText());

		
		//if it is a conditional
		if (step.isConditional()) {
			Log.v(TAG, "Step has conditional content");
			
			final TextView consText = ((TextView)page.findViewById(R.id.consequentText));
			consText.setText(step.getConsequent());
			
			page.findViewById(R.id.consequentTitle).setOnClickListener(new OnClickListener() {
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
		
		
		setupTimer(cont);				
		setupReferences(cont);
		if (step.isInputAllowed()) setupInput(cont);
	}
	
	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public StepPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	

	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed,l,t,r,b);
		// TODO Auto-generated method stub	
	}

	/**
	 * Set up timer
	 */
	private void setupTimer(ViewGroup container){
		if (step.getTimer()){
			LayoutInflater inflater = LayoutInflater.from(getContext());
	        View timer = (View)inflater.inflate(R.layout.timer, null);
			container.addView(timer);
		}
	}
	
	/**
	 * Sets up the execution notes to be displayed for this step.
	 * If there is a parent step, show that one too.
	 */
	private void setupExecutionNotes(ViewGroup container) {
		if (parent != null) setupExecutionNote(container, parent.getExecNote());
		setupExecutionNote(container, step.getExecNote());
	}
	
	
	
	/**
	 * Add the given execution note to the step page.
	 * 
	 * @param note the note to display
	 */
	private void setupExecutionNote(ViewGroup container, ExecNote note) {
		if (note != null) {
			Log.i(TAG, "Setting up execution note");
			LayoutInflater inflater = LayoutInflater.from(getContext());
	        View noteView = (View)inflater.inflate(R.layout.callout, null);
			
	        ((TextView)noteView.findViewById(R.id.calloutTitle)).setText(R.string.ex_note_title);
	        ((TextView)noteView.findViewById(R.id.calloutText)).setText(note.getText());

			container.addView(noteView, 0);
		}
	}
	
	
	
	/**
	 * Setup the step's callouts
	 */
	private void setupCallouts(ViewGroup container) {
		//TODO repeating code
		if (parent != null) {
			List<Callout> pcalls = parent.getCallouts();
			for (int i = 0; i < pcalls.size(); i++) {
				setupCallout(container, pcalls.get(i));
			}
		}
		
		List<Callout> calls = step.getCallouts();
		for (int i = 0; i < calls.size(); i++) {
			setupCallout(container, calls.get(i));
		}
	}
	
	
	
	/**
	 * Add the given callout object to the step
	 * @param call the callout to render
	 */
	private void setupCallout(ViewGroup container, Callout call) {
		if (call != null) {
			Log.i(TAG, "Setting up callout");
			LayoutInflater inflater = LayoutInflater.from(getContext());
	        View callView = (View)inflater.inflate(R.layout.callout, null);
	        
	        String typeName = "";
	        int bg = 0;
	        int border = 0;
	        
	        switch(call.getType()) {
	        	case NOTE:
	        		typeName = "NOTE";
	        		bg = R.drawable.dot_bg_white;
	        		border = R.drawable.border_white;
	        		break;
	        	
	        	case CAUTION:
	        		typeName = "CAUTION";
	        		bg = R.drawable.dot_bg_yellow;
	        		border = R.drawable.border_yellow;
	        		break;
	        		
	        	case WARNING:
	        		typeName = "WARNING";
	        		bg = R.drawable.dot_bg_red;
	        		border = R.drawable.border_red;
	        		break;	        	
	        		
	        	default:
	        		break;
	        }
	        
	        ((ViewGroup)callView.findViewById(R.id.calloutContainer)).setBackgroundResource(border);
	        ((ViewGroup)callView.findViewById(R.id.calloutHeader)).setBackgroundResource(bg);
	        ((TextView)callView.findViewById(R.id.calloutTitle)).setText(typeName);
	        ((TextView)callView.findViewById(R.id.calloutText)).setText(call.getText());

			container.addView(callView, 0);
		}
	}
	
	
	
	/**
	 * Setup the references for the step
	 */
	private void setupReferences(ViewGroup container) {
		List<Reference> refs = step.getReferences();
		for (int i = 0; i < refs.size(); i++) {
			RType type = refs.get(i).getType();
			
			switch(type) {
				case IMAGE:
					setupImageReference(container, refs.get(i));
					break;
					
				case VIDEO:
					setupVideoReference(container, refs.get(i));
					break;
					
				case AUDIO:
					setupAudioReference(container, refs.get(i));
					break;
					
				case TABLE:
					setupTableReference(container, refs.get(i));
					break;
					
				default:
					break;
			}
		}
	}
	
	
	
	/**
	 * Setup the given reference as an image reference
	 * @param ref the reference to render
	 */
	private void setupImageReference(ViewGroup container, Reference ref) {
		Log.v(TAG, "Setting up image view: " + ref.getUrl());
		
		try {
			LayoutInflater inflater = LayoutInflater.from(getContext());
	        View reference = (View)inflater.inflate(R.layout.reference_image, null);
			
			InputStream is = getContext().getAssets().open("procedures/references/" + ref.getUrl());
			Drawable d = Drawable.createFromStream(is, null);
			
			((ImageView)reference.findViewById(R.id.referenceImage)).setImageDrawable(d);
	        //img.setImageDrawable(Drawable.createFromPath(ref.getUrl()));
	
			((TextView)reference.findViewById(R.id.referenceCaption)).setText(ref.getName() + ": " + ref.getDescription());
	        
	        container.addView(reference);
			
		} catch (IOException e) {
			Log.e(TAG, "Error loading image", e);
		}
        
	}
	
	
	
	/**
	 * Setup the given reference as a video reference
	 * 
	 * TODO: we should make our own media controller so it's stylized
	 * 
	 * @param ref the reference to render
	 */
	private void setupVideoReference(ViewGroup container, Reference ref) {
		Log.v(TAG, "Setting up video view: " + ref.getUrl());
		
		LayoutInflater inflater = LayoutInflater.from(getContext());
        View reference = (View)inflater.inflate(R.layout.reference_video, null);
		
        final VideoView vid = ((VideoView)reference.findViewById(R.id.referenceVideo));
		
		//TODO for some reason this fucking thing doesn't work.
		//vid.setVideoURI(Uri.parse("file:///android_asset/procedures/references/" + ref.getUrl()));
		vid.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + ref.getUrl()));

		vid.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
            	mp.setOnVideoSizeChangedListener(new OnVideoSizeChangedListener() { 
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                          /*
                           *  add media controller and set its position
                           *  TODO this still isn't laying where we want it
                           *  TODO probably should make this a custom videoview class
                           */
                          MediaController mc = new MediaController(getContext());
                          vid.setMediaController(mc);
                          mc.setAnchorView(vid);
                          
                          LayoutParams lp = new LinearLayout.LayoutParams(mp.getVideoWidth(), mp.getVideoHeight());
                          lp.gravity = Gravity.CENTER;
                          vid.setLayoutParams(lp);
                    }
                });
            	
            	mp.start();
            }
        });
			
		
		((TextView)reference.findViewById(R.id.referenceCaption)).setText(ref.getName() + ": " + ref.getDescription());
			
		container.addView(reference);
		
	}
	
	
	
	/**
	 * Setup the given reference as an audio reference
	 * @param ref the reference to render
	 */
	private void setupAudioReference(ViewGroup container, Reference ref) {
		//TODO
	}
	
	
	
	/**
	 * Setup the given reference as a table reference
	 * @param ref the reference to render
	 */
	private void setupTableReference(ViewGroup container, Reference ref) {
		Log.v(TAG, "Setting up table view");
		LayoutInflater inflater = LayoutInflater.from(getContext());
        TableLayout table = (TableLayout)inflater.inflate(R.layout.table, null);
        
        List<List<String>> cells = ref.getTable();
        
        for (int i = 0; i < cells.size(); i++) {
        	
        	if (i==0) {
        		table.addView(getRow(cells.get(i), R.layout.table_header_row, R.layout.table_header_cell));
        	
        	} else {
        		table.addView(getRow(cells.get(i), R.layout.table_row, R.layout.table_cell));
        	}
        }
        
        //TODO tables don't have captions
        //((TextView)table.findViewById(R.id.referenceCaption)).setText(ref.getName() + ": " + ref.getDescription());
        
        container.addView(table);
	}
	
	
	
	/**
	 * Set up a table row with the given values.  
	 * 
	 * @param cells
	 * @param rowId
	 * @param cellId
	 * @return
	 */
	private TableRow getRow(List<String> cells, int rowId, int cellId) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		TableRow row = (TableRow)inflater.inflate(rowId, null);
        
        for (int i = 0; i < cells.size(); i++) {
        	TextView t = (TextView)inflater.inflate(cellId, null);
        	t.setText(cells.get(i));
        	row.addView(t);
        }
        
        return row;
	}
	
	
	
	/**
	 * 
	 */
	private void setupInput(ViewGroup container) {
		Log.i(TAG, "Setting up input");
		LayoutInflater inflater = LayoutInflater.from(getContext());
        View input = (View)inflater.inflate(R.layout.input, null);

		container.addView(input);
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
	 * 
	 * @return cycle
	 */
	public int getCycle() {
		return cycle;
	}
}
