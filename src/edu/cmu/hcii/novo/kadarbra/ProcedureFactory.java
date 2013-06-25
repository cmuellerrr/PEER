package edu.cmu.hcii.novo.kadarbra;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import edu.cmu.hcii.novo.kadarbra.structure.Procedure;
import edu.cmu.hcii.novo.kadarbra.structure.Step;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.util.Xml;

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
		String subsection = null;
		String sub_subsection = null;
		String title = null;
		String objective = null;
		String duration = null;	
		List<Step> steps = new ArrayList<Step>();		
		
		//This is the tag we are looking for
	    parser.require(XmlPullParser.START_TAG, ns, "procedure");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	       
	        //Get the attributes
	        if (name.equals("section")) {
	        	section = readTag(parser, "section");
	        } else if (name.equals("subsection")) {
	        	subsection = readTag(parser, "subsection");
	        } else if (name.equals("sub_subsection")) {
	        	sub_subsection = readTag(parser, "sub_subsection");
	        } else if (name.equals("title")) {
	        	title = readTag(parser, "title");
	        } else if (name.equals("objective")) {
	        	objective = readTag(parser, "objective");
	        } else if (name.equals("duration")) {
	        	duration = readTag(parser, "duration");
	        } else if (name.equals("steps")) {
	            steps = readSteps(parser);
	        } else {
	            skip(parser);
	        }
	    }  
	    return new Procedure(section + "." + subsection + "." + sub_subsection, title, objective, duration, steps);
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
	        String name = parser.getName();
	        
	        //Get the attributes
	        if (name.equals("parent_step")) {
	        	steps.add(readParentStep(parser));
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
	private static Step readParentStep(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing parent step");
		
		String number = null;
	    String text = null;
	    List<Step> substeps = new ArrayList<Step>();
	    
	    //This is the tag we are looking for
  		parser.require(XmlPullParser.START_TAG, ns, "parent_step");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	        
	        //Get the attributes
	        if (name.equals("number")) {
	            number = readTag(parser, "number");
	        } else if (name.equals("text")) {
	            text = readTag(parser, "text");
	        } else if (name.equals("substep")) {
	            substeps.add(readSubstep(parser));
	        } else {
	            skip(parser);
	        }
	    }
	    return new Step(number, text, substeps);
	}
	
	/**
	 * Parse an xml document to create a substep.
	 * 
	 * TODO right now this doesn't look for further substeps
	 * 
	 * @param parser the xml to parse
	 * @return a step object
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	private static Step readSubstep(XmlPullParser parser)  throws XmlPullParserException, IOException {
		Log.d(TAG, "Parsing substep");
		
		String number = null;
	    String text = null;
	    List<Step> substeps = new ArrayList<Step>();

	    //This is the tag we are looking for
	    parser.require(XmlPullParser.START_TAG, ns, "substep");
	    
	    //Until we get to the closing tag
	    while (parser.next() != XmlPullParser.END_TAG) {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            continue;
	        }
	        String name = parser.getName();
	        
	        //Get the attributes
	        if (name.equals("number")) {
	            number = readTag(parser, "number");
	        } else if (name.equals("text")) {
	            text = readTag(parser, "text");
	        } else {
	            skip(parser);
	        }
	    }
	    
	    return new Step(number, text, substeps);
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
