/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra;

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
import edu.cmu.hcii.novo.kadarbra.structure.Callout;
import edu.cmu.hcii.novo.kadarbra.structure.CycleNote;
import edu.cmu.hcii.novo.kadarbra.structure.ExecNote;
import edu.cmu.hcii.novo.kadarbra.structure.Reference;
import edu.cmu.hcii.novo.kadarbra.structure.StowageItem;

/**
 * @author Chris
 *
 */
public class ViewFactory {
	
	private static final String TAG = "ViewFactory";
	
	
	public static View getCallGroundPage(Context context) {
		return LayoutInflater.from(context).inflate(R.layout.call_ground, null);
	}
	
	
	
	/**
	 * 
	 * @param module
	 * @param items
	 * @return
	 */
	public static ViewGroup getStowageTable(Context context, String module, List<StowageItem> items) {
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewGroup container = (ViewGroup)inflater.inflate(R.layout.stow_notes_table, null);
		TableLayout table = (TableLayout)container.findViewById(R.id.stow_table);
		
		//set table title
		((TextView)container.findViewById(R.id.stow_table_title)).setText(module);
		
		for (int i = 0; i < items.size(); i++) {
			StowageItem s = items.get(i);
						
			TableRow row = (TableRow) inflater.inflate(R.layout.stow_note, null);
			
			((TextView)row.findViewById(R.id.stowNoteBinCode)).setText(s.getBinCode());
			((TextView)row.findViewById(R.id.stowNoteItem)).setText(s.getName());
			((TextView)row.findViewById(R.id.stowNoteQuantity)).setText(String.valueOf(s.getQuantity()));
			((TextView)row.findViewById(R.id.stowNoteItemCode)).setText(s.getItemCode());
			((TextView)row.findViewById(R.id.stowNoteNotes)).setText(s.getText());
			
			try {
				InputStream is = context.getAssets().open("procedures/references/" + s.getUrl());
				Drawable d = Drawable.createFromStream(is, null);
				((ImageView)row.findViewById(R.id.stowNoteImage)).setImageDrawable(d);
				
			} catch(Exception e) {
				Log.e(TAG, "Error adding reference image to stowage note", e);
			}
			
			table.addView(row);
		}
		
		return container;
	}
	
	
	/**
	 * 
	 * @param context
	 * @param note
	 * @return
	 */
	public static ViewGroup getExecutionNoteOverview(Context context, ExecNote note) {
		if (note != null) {
			Log.v(TAG, "Setting up overview execution note");
			LayoutInflater inflater = LayoutInflater.from(context);
			ViewGroup noteView = (ViewGroup)inflater.inflate(R.layout.ex_note_overall, null);
			
			((TextView)noteView.findViewById(R.id.exNoteNumber)).setText("Step " + note.getNumber());
			((TextView)noteView.findViewById(R.id.exNoteText)).setText(note.getText());

			return noteView;
		}
		
		return null;
	}
	
	
	
	/**
	 * Add the given execution note to the step page.
	 * 
	 * @param note the note to display
	 */
	public static ViewGroup getExecutionNote(Context context, ExecNote note) {
		if (note != null) {
			Log.v(TAG, "Setting up execution note");
			LayoutInflater inflater = LayoutInflater.from(context);
			ViewGroup noteView = (ViewGroup)inflater.inflate(R.layout.callout, null);
			
	        ((TextView)noteView.findViewById(R.id.calloutTitle)).setText(R.string.ex_note_title);
	        ((TextView)noteView.findViewById(R.id.calloutText)).setText(note.getText());

			return noteView;
		}
		
		return null;
	}	
	
	
	
	/**
	 * Add the given callout object to the step
	 * @param call the callout to render
	 */
	public static ViewGroup getCallout(Context context, Callout call) {
		if (call != null) {
			Log.v(TAG, "Setting up callout");
			LayoutInflater inflater = LayoutInflater.from(context);
			ViewGroup callView = (ViewGroup)inflater.inflate(R.layout.callout, null);
	        
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

			return callView;
		}
		
		return null;
	}
	
	
	/**
	 * Setup the give cycle note
	 * 
	 * @param context
	 * @param note
	 * @return
	 */
	public static ViewGroup getCycleNote(Context context, CycleNote note) {
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewGroup newNote = (ViewGroup)inflater.inflate(R.layout.cycle_note, null);

		((TextView)newNote.findViewById(R.id.cycleNoteText)).setText(note.getText());
        
        if (note.getReference() != null) {
        	newNote.addView(getReference(context, note.getReference()));
        }
        
        return newNote;
	}
	
	
	
	/**
	 * Setup the reference view group corresponding to the given
	 * reference object.
	 * 
	 * @param ref
	 * @return
	 */
	public static ViewGroup getReference(Context context, Reference ref) {		
		switch(ref.getType()) {
			case IMAGE:
				return getImageReference(context, ref);
				
			case VIDEO:
				return getVideoReference(context, ref);
				
			case AUDIO:
				return getAudioReference(context, ref);
				
			case TABLE:
				return getTableReference(context, ref);
				
			default:
				break;
		}
		
		return null;
	}
	
	
	
	/**
	 * Setup the given reference as an image reference
	 * @param ref the reference to render
	 */
	public static ViewGroup getImageReference(Context context, Reference ref) {
		Log.v(TAG, "Setting up image view: " + ref.getUrl());
		
		LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup reference = (ViewGroup)inflater.inflate(R.layout.reference, null);
        ImageView img = (ImageView)inflater.inflate(R.layout.image, null);
		
		try {			
			InputStream is = context.getAssets().open("procedures/references/" + ref.getUrl());
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
	public static ViewGroup getVideoReference(final Context context, Reference ref) {
		Log.v(TAG, "Setting up video view: " + ref.getUrl());
		
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup reference = (ViewGroup)inflater.inflate(R.layout.reference, null);
        final VideoView vid = (VideoView)inflater.inflate(R.layout.video, null);		
		
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
                          MediaController mc = new MediaController(context);
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
			
		reference.addView(vid, 0);	
		return reference;
	}
	
	
	
	/**
	 * Setup the given reference as an audio reference
	 * @param ref the reference to render
	 */
	public static ViewGroup getAudioReference(Context context, Reference ref) {
		//TODO
		return null;
	}
	
	
	
	/**
	 * Setup the given reference as a table reference
	 * @param ref the reference to render
	 */
	public static ViewGroup getTableReference(Context context, Reference ref) {
		Log.v(TAG, "Setting up table view");
		
		LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup reference = (ViewGroup)inflater.inflate(R.layout.reference, null);
        TableLayout table = (TableLayout)inflater.inflate(R.layout.table, null);
        
        List<List<String>> cells = ref.getTable();
        
        for (int i = 0; i < cells.size(); i++) {
        	if (i==0) {
        		table.addView(getRow(context, cells.get(i), R.layout.table_header_row, R.layout.table_header_cell));
        	
        	} else {
        		table.addView(getRow(context, cells.get(i), R.layout.table_row, R.layout.table_cell));
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
	public static TableRow getRow(Context context, List<String> cells, int rowId, int cellId) {
		LayoutInflater inflater = LayoutInflater.from(context);
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
	public static ViewGroup getInput(Context context) {
		Log.v(TAG, "Setting up input");
		LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup input = (ViewGroup)inflater.inflate(R.layout.input, null);

		return input;
	}
	
	
	/**
	 * 
	 * @param container
	 */
	public static ViewGroup getTimer(Context context){
		Log.v(TAG, "Setting up timer");
		LayoutInflater inflater = LayoutInflater.from(context);
	    ViewGroup timer = (ViewGroup)inflater.inflate(R.layout.timer, null);
		
	    return timer;
	}
}
