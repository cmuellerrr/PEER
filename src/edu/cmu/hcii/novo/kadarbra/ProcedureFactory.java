<<<<<<< HEAD
package edu.cmu.hcii.novo.kadarbra;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.util.Xml;
import edu.cmu.hcii.novo.kadarbra.structure.ExecNote;
import edu.cmu.hcii.novo.kadarbra.structure.Procedure;
import edu.cmu.hcii.novo.kadarbra.structure.Step;
import edu.cmu.hcii.novo.kadarbra.structure.StowageItem;

public class ProcedureFactory {
	private static final String TAG = "ProcedureFactory";
	
	// We don't use namespaces
    private static final String ns = null;
	
    
    
    /**
     * Generate a list of procedure objects based off of the 
     * xml definitions in the ___ directory.
     * 
     * @return a list of procedure objects
     */
    public static List<Procedure> getProcedures(Context ctx) {
    	Log.d(TAG, "Getting procedures from xml");
    	
    	List<Procedure> results = new ArrayList<Procedure>();
    	
		try {
			//yup
			AssetManager assMan = ctx.getAssets();
			String[] procs = assMan.list("procedures");
			
			Log.v(TAG, procs.length + " file(s) to parse");
			
			for (String proc : procs) {
	    		try {
	    			Log.d(TAG, "Parsing " + proc);

	    			InputStream in = assMan.open("procedures/" + proc);
	    			results.add(getProcedure(in));
	    		} catch (Exception e) {
	    			Log.e(TAG, "Error parsing procedure", e);
	    		}
	    	}
			
		} catch (IOException e) {
			Log.e(TAG, "Error gathering asset files", e);
		}
    	
    	return results;
    }
    
    
    
    /**
     * Get a procedure object based off of the given input stream.
     * Sets up the parser then calls a different parse method.
     * 
     * @param in the input stream to parse
     * @return a procedure object
     * @throws XmlPullParserException
     * @throws IOException
     */
	public static Procedure getProcedure(InputStream in) throws XmlPullParserException, IOException {		
		Log.v(TAG, "Initializing parser");
		
		try {			
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readProcedure(parser);
        } finally {
            in.close();
        }
	}
	
	
	
	/**
	 * Parse an xml document to create a procedure object.
	 * 
	 * TODO: these methods could definitely be more flexible, maybe keep a 
	 * map of the corresponding tags?
	 * 
	 * @param parser the xml to parse
	 * @return the resulting procedure object
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static Procedure readProcedure(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing procedure");
		
		String section = null;
		String title = null;
		String objective = null;
		String duration = null;	
		List<ExecNote> execNotes = null;
		List<StowageItem> stowageItems = null;
		List<Step> steps = null;		
		
		//This is the tag we are looking for
	    parser.require(XmlPullParser.START_TAG, ns, "procedure");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	       
	        //Get the attributes
	        if (tag.equals("section")) {
	        	section = readTag(parser, tag);
	        	
	        } else if (tag.equals("subsection")) {
	        	section += "." + readTag(parser, tag);
	        	
	        } else if (tag.equals("sub_subsection")) {
	        	section += "." +  readTag(parser, tag);
	        	
	        } else if (tag.equals("title")) {
	        	title = readTag(parser, tag);
	        	
	        } else if (tag.equals("objective")) {
	        	objective = readTag(parser, tag);
	        	
	        } else if (tag.equals("duration")) {
	        	duration = readTag(parser, tag);
	        	
	        } else if (tag.equals("execution_notes")) {
	        	execNotes = readExecNotes(parser);
	        	
	        } else if (tag.equals("stowage_notes")) {
	        	stowageItems = readStowageNotes(parser);
	        	
	    	} else if (tag.equals("steps")) {
	            steps = readSteps(parser);
	            
	        } else {
	            skip(parser);
	        }
	    }  
	    return new Procedure(section, title, objective, duration, execNotes, stowageItems, steps);
	}
	
	
	
	/**
	 * Parse the xml for a list of overall execution notes
	 * 
	 * @param parser the xml to parse
	 * @return the resulting list of execution notes
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static List<ExecNote> readExecNotes(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing execution notes");
		
		List<ExecNote> execNotes = new ArrayList<ExecNote>();
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "execution_notes");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("note")) {
	        	execNotes.add(readExecNote(parser));
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    return execNotes;
	}
	
	
	
	/**
	 * Parse xml for an execution note
	 * 
	 * @param parser the xml to parse
	 * @return the resulting execution note
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static ExecNote readExecNote(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing execution note");
		
		String step = null;
		String text = null;
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "note");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("step")) {
	        	step = readTag(parser, tag);
	        	
	        } else if (tag.equals("substep")) {
	        	step += "." + readTag(parser, tag);
	        	
	        } else if (tag.equals("text")) {
	        	text = readTag(parser, tag);
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    return new ExecNote(step, text);
	}
	
	
	
	/**
	 * Parse the xml for a list of overall stowage items
	 * 
	 * @param parser the xml to parse
	 * @return the resulting list of stowage items
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static List<StowageItem> readStowageNotes(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing stowage notes");
		
		List<StowageItem> stowageItems = new ArrayList<StowageItem>();
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "stowage_notes");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("note")) {
	        	stowageItems.add(readStowageNote(parser));
	        } else {
	            skip(parser);
	        }
	    }
	    return stowageItems;
	}
	
	
	
	/**
	 * Parse xml for a stowage note
	 * 
	 * @param parser the xml to parse
	 * @return the resulting stowage item from the note
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static StowageItem readStowageNote(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing stowage note");
		
		StowageItem item = null;
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "note");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("item")) {
	        	item = readStowageItem(parser);
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    return item;
	}
	
	
	
	/**
	 * Parse xml for an execution note
	 * 
	 * @param parser the xml to parse
	 * @return the resultin execution note
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static StowageItem readStowageItem(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.v(TAG, "Parsing stowage item");
		
		String name = null;
		int quantity = 0;
		String itemCode = null;
		String binCode = null;
		String text = null;
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "item");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("name")) {
	        	name = readTag(parser, tag);
	        	
	        } else if (tag.equals("quantity")) {
	        	quantity = Integer.parseInt(readTag(parser, tag));
	        	
	        } else if (tag.equals("item_code")) {
	        	itemCode = readTag(parser, tag);
	        	
	        } else if (tag.equals("bin_code")) {
	        	binCode = readTag(parser, tag);
	        	
	        } else if (tag.equals("text")) {
	        	text = readTag(parser, tag);
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    return new StowageItem(name, quantity, itemCode, binCode, text);
	}
	
	
	
	/**
	 * Parse the xml for the list of overall procedure steps.
	 * 
	 * 
	 * @param parser the xml to parse
	 * @return a list of steps
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static List<Step> readSteps(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing steps");
		
	    List<Step> steps = new ArrayList<Step>();
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "steps");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("step")) {
	        	steps.add(readStep(parser));
	        	
	        } else if (tag.equals("cycle")) {
	        	steps.addAll(readCycle(parser));
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    return steps;
	}
	
	
	
	/**
	 * Parse an xml document to create a parent step.
	 * 
	 * @param parser the xml to parse
	 * @return a step object
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static Step readStep(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing step");
		
		String number = null;
	    String text = null;
	    List<Step> substeps = new ArrayList<Step>();
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "step");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("number")) {
	            number = readTag(parser, tag);
	        
	        } else if (tag.equals("text")) {
	            text = readTag(parser, tag);
	            
	        } else if (tag.equals("step")) {
	            substeps.add(readStep(parser));
	            
	        } else {
	            skip(parser);
	        }
	    }
	    return new Step(number, text, substeps);
	}
	
	
	private static List<Step> readCycle(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing cycle");
		
		int repetitions = 0;
	    List<Step> steps = new ArrayList<Step>();
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "cycle");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("repetitions")) {
	        	repetitions = Integer.parseInt(readTag(parser, tag));
	        	
	        } else if (tag.equals("step")) {
	        	steps.add(readStep(parser));
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    Log.v(TAG, "adding steps " + repetitions + " times");
	    
	    List<Step> result = new ArrayList<Step>();
	    
	    for (int i = 0; i < repetitions; i++) {
	    	result.addAll(new ArrayList<Step>(steps));
	    }
	    
	    return result;
	}
	
	
	
	/**
	 * Read the text value of the given tag from the xml.
	 * 
	 * @param parser the xml to parse
	 * @param tag the tag to look for
	 * @return the string value of the tag
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static String readTag(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
		Log.v(TAG, "Reading tag " + tag);
		
		parser.require(XmlPullParser.START_TAG, ns, tag);
	    String result = readTextValue(parser);
	    parser.require(XmlPullParser.END_TAG, ns, tag);
	    return result;
	}
	
	
	
	/**
	 * Extracts a tag's textual value.
	 * 
	 * @param parser the xml to parse
	 * @return the string value contained within a tag
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private static String readTextValue(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}
	
	
	
	/**
	 * Skip the tag currently pointed to by the parser.  This lets us 
	 * ignore any tags we don't currently care about.
	 * 
	 * @param parser the xml to parse
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    
	    //Move through the xml until we get to the close
	    //of the current tag.
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
}
=======
package edu.cmu.hcii.novo.kadarbra;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.util.Xml;
import edu.cmu.hcii.novo.kadarbra.structure.ExecNote;
import edu.cmu.hcii.novo.kadarbra.structure.Procedure;
import edu.cmu.hcii.novo.kadarbra.structure.Step;
import edu.cmu.hcii.novo.kadarbra.structure.StowageItem;

public class ProcedureFactory {
	private static final String TAG = "ProcedureFactory";
	
	// We don't use namespaces
    private static final String ns = null;
	
    
    
    /**
     * Generate a list of procedure objects based off of the 
     * xml definitions in the ___ directory.
     * 
     * @return a list of procedure objects
     */
    public static List<Procedure> getProcedures(Context ctx) {
    	Log.d(TAG, "Getting procedures from xml");
    	
    	List<Procedure> results = new ArrayList<Procedure>();
    	
		try {
			//yup
			AssetManager assMan = ctx.getAssets();
			String[] procs = assMan.list("procedures");
			
			Log.v(TAG, procs.length + " file(s) to parse");
			
			for (String proc : procs) {
	    		try {
	    			Log.d(TAG, "Parsing " + proc);

	    			InputStream in = assMan.open("procedures/" + proc);
	    			results.add(getProcedure(in));
	    		} catch (Exception e) {
	    			Log.e(TAG, "Error parsing procedure", e);
	    		}
	    	}
			
		} catch (IOException e) {
			Log.e(TAG, "Error gathering asset files", e);
		}
    	
    	return results;
    }
    
    
    
    /**
     * Get a procedure object based off of the given input stream.
     * Sets up the parser then calls a different parse method.
     * 
     * @param in the input stream to parse
     * @return a procedure object
     * @throws XmlPullParserException
     * @throws IOException
     */
	public static Procedure getProcedure(InputStream in) throws XmlPullParserException, IOException {		
		Log.v(TAG, "Initializing parser");
		
		try {			
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readProcedure(parser);
        } finally {
            in.close();
        }
	}
	
	
	
	/**
	 * Parse an xml document to create a procedure object.
	 * 
	 * TODO: these methods could definitely be more flexible, maybe keep a 
	 * map of the corresponding tags?
	 * 
	 * @param parser the xml to parse
	 * @return the resulting procedure object
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static Procedure readProcedure(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing procedure");
		
		String section = null;
		String title = null;
		String objective = null;
		String duration = null;	
		List<ExecNote> execNotes = null;
		List<StowageItem> stowageItems = null;
		List<Step> steps = null;		
		
		//This is the tag we are looking for
	    parser.require(XmlPullParser.START_TAG, ns, "procedure");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	       
	        //Get the attributes
	        if (tag.equals("section")) {
	        	section = readTag(parser, tag);
	        	
	        } else if (tag.equals("subsection")) {
	        	section += "." + readTag(parser, tag);
	        	
	        } else if (tag.equals("sub_subsection")) {
	        	section += "." +  readTag(parser, tag);
	        	
	        } else if (tag.equals("title")) {
	        	title = readTag(parser, tag);
	        	
	        } else if (tag.equals("objective")) {
	        	objective = readTag(parser, tag);
	        	
	        } else if (tag.equals("duration")) {
	        	duration = readTag(parser, tag);
	        	
	        } else if (tag.equals("execution_notes")) {
	        	execNotes = readExecNotes(parser);
	        	
	        } else if (tag.equals("stowage_notes")) {
	        	stowageItems = readStowageNotes(parser);
	        	
	    	} else if (tag.equals("steps")) {
	            steps = readSteps(parser);
	            
	        } else {
	            skip(parser);
	        }
	    }  
	    return new Procedure(section, title, objective, duration, execNotes, stowageItems, steps);
	}
	
	
	
	/**
	 * Parse the xml for a list of overall execution notes
	 * 
	 * @param parser the xml to parse
	 * @return the resulting list of execution notes
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static List<ExecNote> readExecNotes(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing execution notes");
		
		List<ExecNote> execNotes = new ArrayList<ExecNote>();
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "execution_notes");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("note")) {
	        	execNotes.add(readExecNote(parser));
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    return execNotes;
	}
	
	
	
	/**
	 * Parse xml for an execution note
	 * 
	 * @param parser the xml to parse
	 * @return the resulting execution note
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static ExecNote readExecNote(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing execution note");
		
		String step = null;
		String text = null;
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "note");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("step")) {
	        	step = readTag(parser, tag);
	        	
	        } else if (tag.equals("substep")) {
	        	step += "." + readTag(parser, tag);
	        	
	        } else if (tag.equals("text")) {
	        	text = readTag(parser, tag);
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    return new ExecNote(step, text);
	}
	
	
	
	/**
	 * Parse the xml for a list of overall stowage items
	 * 
	 * @param parser the xml to parse
	 * @return the resulting list of stowage items
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static List<StowageItem> readStowageNotes(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing stowage notes");
		
		List<StowageItem> stowageItems = new ArrayList<StowageItem>();
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "stowage_notes");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("note")) {
	        	stowageItems.add(readStowageNote(parser));
	        } else {
	            skip(parser);
	        }
	    }
	    return stowageItems;
	}
	
	
	
	/**
	 * Parse xml for a stowage note
	 * 
	 * @param parser the xml to parse
	 * @return the resulting stowage item from the note
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static StowageItem readStowageNote(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing stowage note");
		
		StowageItem item = null;
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "note");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("item")) {
	        	item = readStowageItem(parser);
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    return item;
	}
	
	
	
	/**
	 * Parse xml for an execution note
	 * 
	 * @param parser the xml to parse
	 * @return the resultin execution note
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static StowageItem readStowageItem(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.v(TAG, "Parsing stowage item");
		
		String name = null;
		int quantity = 0;
		String itemCode = null;
		String binCode = null;
		String text = null;
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "item");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("name")) {
	        	name = readTag(parser, tag);
	        	
	        } else if (tag.equals("quantity")) {
	        	quantity = Integer.parseInt(readTag(parser, tag));
	        	
	        } else if (tag.equals("item_code")) {
	        	itemCode = readTag(parser, tag);
	        	
	        } else if (tag.equals("bin_code")) {
	        	binCode = readTag(parser, tag);
	        	
	        } else if (tag.equals("text")) {
	        	text = readTag(parser, tag);
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    return new StowageItem(name, quantity, itemCode, binCode, text);
	}
	
	
	
	/**
	 * Parse the xml for the list of overall procedure steps.
	 * 
	 * 
	 * @param parser the xml to parse
	 * @return a list of steps
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static List<Step> readSteps(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing steps");
		
	    List<Step> steps = new ArrayList<Step>();
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "steps");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("step")) {
	        	steps.add(readStep(parser));
	        	
	        } else if (tag.equals("cycle")) {
	        	steps.addAll(readCycle(parser));
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    return steps;
	}
	
	
	
	/**
	 * Parse an xml document to create a parent step.
	 * 
	 * @param parser the xml to parse
	 * @return a step object
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static Step readStep(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing step");
		
		String number = null;
	    String text = null;
	    List<Step> substeps = new ArrayList<Step>();
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "step");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("number")) {
	            number = readTag(parser, tag);
	        
	        } else if (tag.equals("text")) {
	            text = readTag(parser, tag);
	            
	        } else if (tag.equals("step")) {
	            substeps.add(readStep(parser));
	            
	        } else {
	            skip(parser);
	        }
	    }
	    return new Step(number, text, substeps);
	}
	
	
	private static List<Step> readCycle(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing cycle");
		
		int repetitions = 0;
	    List<Step> steps = new ArrayList<Step>();
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "cycle");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String tag = parser.getName();
	        
	        //Get the attributes
	        if (tag.equals("repetitions")) {
	        	repetitions = Integer.parseInt(readTag(parser, tag));
	        	
	        } else if (tag.equals("step")) {
	        	steps.add(readStep(parser));
	        	
	        } else {
	            skip(parser);
	        }
	    }
	    Log.v(TAG, "adding steps " + repetitions + " times");
	    
	    List<Step> result = new ArrayList<Step>();
	    
	    for (int i = 0; i < repetitions; i++) {
	    	result.addAll(new ArrayList<Step>(steps));
	    }
	    
	    return result;
	}
	
	
	
	/**
	 * Read the text value of the given tag from the xml.
	 * 
	 * @param parser the xml to parse
	 * @param tag the tag to look for
	 * @return the string value of the tag
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static String readTag(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
		Log.v(TAG, "Reading tag " + tag);
		
		parser.require(XmlPullParser.START_TAG, ns, tag);
	    String result = readTextValue(parser);
	    parser.require(XmlPullParser.END_TAG, ns, tag);
	    return result;
	}
	
	
	
	/**
	 * Extracts a tag's textual value.
	 * 
	 * @param parser the xml to parse
	 * @return the string value contained within a tag
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private static String readTextValue(XmlPullParser parser) throws IOException, XmlPullParserException {
	    String result = "";
	    if (parser.next() == XmlPullParser.TEXT) {
	        result = parser.getText();
	        parser.nextTag();
	    }
	    return result;
	}
	
	
	
	/**
	 * Skip the tag currently pointed to by the parser.  This lets us 
	 * ignore any tags we don't currently care about.
	 * 
	 * @param parser the xml to parse
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    
	    //Move through the xml until we get to the close
	    //of the current tag.
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
}
>>>>>>> 79203f87352fca5faf8be084e09769bdcff1a318
