/**
 * 
 */
package edu.cmu.hcii.peer.util;

import java.util.HashMap;
import java.util.Map;

import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * A singleton which manages custom fonts.  This is needed
 * because Android apparently allocates memory for the full 
 * font every time it is used.  This way, we load it up 
 * front, then use the same reference when needed.
 * 
 * @author Chris
 *
 */
public class FontManager {
	
	public enum FontStyle {
	    HEADER, BODY, SELECTABLE, TIMER
	}

    private static FontManager self;
    private Map<FontStyle, Typeface> fonts;
     
    // an instance of the android's asset manager. 
    //We need it so we can get the Font Types within the asset folder
    private AssetManager assMan;
    
    
    
    /**
     * Private constructor to keep it a singleton
     * @param assMan
     */
    private FontManager(AssetManager assMan) {
        this.assMan = assMan;
        this.fonts = new HashMap<FontStyle, Typeface>();
        
        addFont(FontStyle.HEADER, "Trebuchet MS Bold.ttf");
        addFont(FontStyle.BODY, "Trebuchet MS Regular.ttf");
        addFont(FontStyle.SELECTABLE, "Orbitron-Regular.ttf");
        addFont(FontStyle.TIMER, "Lifeline.ttf");
    }
    
    
   
    /**
     * Get the instance of the font manager.  Instantiate it on
     * the first call.
     * 
     * @param assetManager
     * @return
     */
    public static FontManager getInstance(AssetManager assetManager) {
        if(self == null) self = new FontManager(assetManager);
        return self;
    }
    
    
    
    /**
     * Add a font to the manager.  The typeface is loaded based on the given
     * file path. Font names are identified by the enum FONT_STYLES.
     * 
     * @param name
     * @param fileName
     */
    public void addFont(FontStyle name, String fileName) {
        Typeface face = Typeface.createFromAsset(assMan, "fonts/" + fileName);
        fonts.put(name, face);
    }
    
    
   
    /**
     * Get the font specified by the given FONT_STYLE name.
     * @param name
     * @return
     */
    public Typeface getFont(FontStyle name) {
        return fonts.get(name);
    }
}