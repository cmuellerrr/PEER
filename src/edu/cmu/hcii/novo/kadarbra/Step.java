/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra;

import java.util.List;

/**
 * @author Chris
 *
 */
public class Step {
	
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
}
