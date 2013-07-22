/**
 * 
 */
package edu.cmu.hcii.peer.util;

import java.util.HashMap;
import java.util.Map;

import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * @author Chris
 *
 */
public class FontManager {
	
	public enum FontStyle {
	    HEADER, BODY, SELECTABLE, TIMER
	}

    private static FontManager self; // the singleton instance
    private Map<FontStyle, Typeface> fonts; //collection of font styles
     
    // an instance of the android's asset manager. 
    //We need it so we can get the Font Types within the asset folder
    private AssetManager assMan;
    
    
    
    //For the singleton implementation the constructor must be private
    private FontManager(AssetManager assMan) {
        this.assMan = assMan;
        this.fonts = new HashMap<FontStyle, Typeface>();
        
        addFont(FontStyle.HEADER, "Trebuchet MS Bold.ttf");
        addFont(FontStyle.BODY, "Trebuchet MS Regular.ttf");
        addFont(FontStyle.SELECTABLE, "Orbitron-Regular.ttf");
        addFont(FontStyle.TIMER, "Lifeline.ttf");
    }
    
    
   
    //The method that returns the instance of the Font Manager.
    //The first call instantiates the Font Manager itself
    public static FontManager getInstance(AssetManager assetManager) {
        if(self == null) self = new FontManager(assetManager);
        return self;
    }
   
    
    
    //This methods adds Font Type and a Paint object to the collection. They are identified
    //by an enum object (FONT_STYLES)
    public void addFont(FontStyle name, String fileName) {
        Typeface face = Typeface.createFromAsset(assMan, "fonts/" + fileName);
        fonts.put(name, face);
    }
    
    
   
    //This method returns the Paint object for the given style
    public Typeface getFont(FontStyle name) {
        return fonts.get(name);
    }
}