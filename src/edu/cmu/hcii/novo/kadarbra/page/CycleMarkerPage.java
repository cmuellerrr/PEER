/**
 * 
 */
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.LinearLayout.LayoutParams;
import edu.cmu.hcii.novo.kadarbra.R;
import edu.cmu.hcii.novo.kadarbra.structure.Cycle;
import edu.cmu.hcii.novo.kadarbra.structure.CycleNote;
import edu.cmu.hcii.novo.kadarbra.structure.Reference;
import edu.cmu.hcii.novo.kadarbra.structure.Step;

/**
 * @author Chris
 *
 */
public class CycleMarkerPage extends LinearLayout {
	private static final String TAG = "CycleMarkerPage";
	
	
	private int totalReps;
	private int currentRep;
	private List<CycleNote> notes;
	
	
	
	/**
	 * 
	 */
	public CycleMarkerPage(Context context, Cycle c, int currentRep) {
		super(context);
		
		this.totalReps = c.getReps();
		this.currentRep = currentRep;
		this.notes = c.getNotes();
		
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewGroup page = (ViewGroup)inflater.inflate(R.layout.cycle_marker_page, null);
		
		if (c.getNumChildren() > 0) {
			String start = ((Step)c.getChild(0)).getNumber();
			String end = ((Step)c.getChild(c.getNumChildren()-1)).getNumber();
			
			String tense = currentRep > 1 ? "are" : "will be";
			
			((TextView)page.findViewById(R.id.cycleMarkerTitle)).setText("You " + tense + " repeating steps " + 
					start + "-" + end + " a total of " + totalReps + " times");
			
		} else {
			((TextView)page.findViewById(R.id.cycleMarkerTitle)).setText("There is a cycle with no steps in it.  Someone screwed up.");
		}
		
        ((TextView)page.findViewById(R.id.cycleMarkerText)).setText("Beginning cycle " + currentRep);
        
        ViewGroup container = (ViewGroup)page.findViewById(R.id.cycleMarkerTextContainer);
        
        for (int i = 0; i < notes.size(); i++) {
        	container.addView(getCycleNote(notes.get(i)));
        }
        
        this.addView(page);
	}
	
	
	
	/**
	 * @param context
	 * @param attrs
	 */
	public CycleMarkerPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	
	
	private ViewGroup getCycleNote(CycleNote note) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		ViewGroup newNote = (ViewGroup)inflater.inflate(R.layout.cycle_note, null);

		((TextView)newNote.findViewById(R.id.cycleNoteText)).setText(note.getText());
        
        if (note.getReference() != null) {
        	newNote.addView(getReference(note.getReference()));
        }
        
        return newNote;
	}

	
	/**
	 * Setup the reference view group corresponding to the given
	 * reference object.
	 * 
	 * @param r
	 * @return
	 */
	private ViewGroup getReference(Reference r) {		
		ViewGroup result = null;
		
		switch(r.getType()) {
			case IMAGE:
				result = setupImageReference(r);
				break;
				
			case VIDEO:
				result = setupVideoReference(r);
				break;
				
			case AUDIO:
				result = setupAudioReference(r);
				break;
				
			case TABLE:
				result = setupTableReference(r);
				break;
				
			default:
				break;
		}
		
		return result;
	}
	
	
	
	/**
	 * Setup the given reference as an image reference
	 * @param ref the reference to render
	 */
	private ViewGroup setupImageReference(Reference ref) {
		Log.v(TAG, "Setting up image view: " + ref.getUrl());
		
		LayoutInflater inflater = LayoutInflater.from(getContext());
        ViewGroup reference = (ViewGroup)inflater.inflate(R.layout.reference, null);
        ImageView img = (ImageView)inflater.inflate(R.layout.image, null);
		
		try {			
			InputStream is = getContext().getAssets().open("procedures/references/" + ref.getUrl());
			Drawable d = Drawable.createFromStream(is, null);
			
			img.setImageDrawable(d);
	        //img.setImageDrawable(Drawable.createFromPath(ref.getUrl()));
			
		} catch (IOException e) {
			Log.e(TAG, "Error loading image", e);
		}
		
		((TextView)reference.findViewById(R.id.referenceCaption)).setText(ref.getName() + ": " + ref.getDescription());
        
		reference.addView(img, 0);
		return reference;
	}
	
	
	
	/**
	 * Setup the given reference as a video reference
	 * 
	 * TODO: we should make our own media controller so it's stylized
	 * 
	 * @param ref the reference to render
	 */
	private ViewGroup setupVideoReference(Reference ref) {
		Log.v(TAG, "Setting up video view: " + ref.getUrl());
		
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //ViewGroup reference = (ViewGroup)inflater.inflate(R.layout.reference, null);
        //final VideoView vid = (VideoView)inflater.inflate(R.id.video);
        
        ViewGroup reference = (ViewGroup)inflater.inflate(R.layout.reference_video, null);
        final VideoView vid = (VideoView)reference.findViewById(R.id.referenceVideo);		
		
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
			
		//reference.addView(vid, 0);	
		return reference;		
	}
	
	
	
	/**
	 * Setup the given reference as an audio reference
	 * @param ref the reference to render
	 */
	private ViewGroup setupAudioReference(Reference ref) {
		return null;
	}
	
	
	
	/**
	 * Setup the given reference as a table reference
	 * @param ref the reference to render
	 */
	private ViewGroup setupTableReference(Reference ref) {
		Log.v(TAG, "Setting up table view");
		
		LayoutInflater inflater = LayoutInflater.from(getContext());
        ViewGroup reference = (ViewGroup)inflater.inflate(R.layout.reference, null);
        TableLayout table = (TableLayout)inflater.inflate(R.layout.table, null);
        
        List<List<String>> cells = ref.getTable();
        
        for (int i = 0; i < cells.size(); i++) {
        	if (i==0) {
        		table.addView(getRow(cells.get(i), R.layout.table_header_row, R.layout.table_header_cell));
        	
        	} else {
        		table.addView(getRow(cells.get(i), R.layout.table_row, R.layout.table_cell));
        	}
        }
        
        ((TextView)reference.findViewById(R.id.referenceCaption)).setText(ref.getName() + ": " + ref.getDescription());
        
        reference.addView(table, 0);
        return reference;
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
	 * @return the totalReps
	 */
	public int getTotalReps() {
		return totalReps;
	}



	/**
	 * @param totalReps the totalReps to set
	 */
	public void setTotalReps(int totalReps) {
		this.totalReps = totalReps;
	}



	/**
	 * @return the repetition
	 */
	public int getCurrentRep() {
		return currentRep;
	}



	/**
	 * @param repetition the repetition to set
	 */
	public void setCurrentRep(int currentRep) {
		this.currentRep = currentRep;
	}



	/**
	 * @return the notes
	 */
	public List<CycleNote> getNotes() {
		return notes;
	}



	/**
	 * @param notes the notes to set
	 */
	public void setNotes(List<CycleNote> notes) {
		this.notes = notes;
	}
}
