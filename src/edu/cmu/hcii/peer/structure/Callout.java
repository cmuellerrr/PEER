/**
 * 
 */
package edu.cmu.hcii.peer.structure;

import java.io.Serializable;

/**
 * A class representing a callout message.  This can be a note, 
 * warning, or caution.
 * 
 * @author Chris
 *
 */
public class Callout implements Serializable {

	public enum CType {
		NOTE, WARNING, CAUTION
	}
	
	/**
	 * Auto-Generated serial id.
	 */
	private static final long serialVersionUID = 1813341131826208809L;
	private CType type;
	private String text;
	
	
	
	/**
	 * Create a new Callout object with the given attributes.
	 * 
	 * @param type
	 * @param text
	 */
	public Callout(CType type, String text) {
		this.type = type;
		this.text = text;
	}

	
	
	/**
	 * @return the type
	 */
	public CType getType() {
		return type;
	}

	
	
	/**
	 * @param type the type to set
	 */
	public void setType(CType type) {
		this.type = type;
	}

	
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
}
