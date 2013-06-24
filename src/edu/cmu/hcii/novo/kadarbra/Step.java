/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chris
 *
 */
public class Step implements Serializable {
	
	/**
	 * Auto-Generated serial id
	 */
	private static final long serialVersionUID = -4348295150944687945L;
	private String number;
	private String text;
	private List<Step> substeps;
	private int substepIndex;
	
	/**
	 * 
	 */
	public Step(String number, String text, List<Step> substeps) {
		this.number = number;
		this.text = text;
		this.substeps = substeps;
		this.substepIndex = 0;
	}

	/**
	 * Add the given step to the end of the procedure.
	 * 
	 * @param step the step to add
	 */
	public void addSubstep(Step step) {
		substeps.add(step);
	}
	
	/**
	 * Add the given step to the procedure at the given index.
	 * 
	 * @param index the location to put the step
	 * @param step the step to add
	 */
	public void addSubstepAt(int index, Step step) {
		if (index <= substeps.size()) {
			substeps.add(index, step);
			if (index <= substepIndex) substepIndex ++;
		}
	}
	
	/**
	 * @param index
	 * @return the step at index
	 */
	public Step getSubstep(int index) {
		return substeps.get(index);
	}
	
	/**
	 * @return the number of steps
	 */
	public int getNumSubsteps() {
		return substeps.size();
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
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
