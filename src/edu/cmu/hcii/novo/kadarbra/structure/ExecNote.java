/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.structure;

/**
 * A class to represent execution notes.  
 * 
 * @author Chris
 *
 */
public class ExecNote {

	private String text;
	
	/**
	 * Create an execution note with the given text.
	 * 
	 * TODO: These will probably need to have knowledge 
	 * of what step they exist on.
	 */
	public ExecNote(String text) {
		this.text = text;
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
