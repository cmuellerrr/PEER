/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.structure;

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
	private ExecNote execNote;
	private List<Callout> callouts;
	private List<Reference> references;
	private List<Step> substeps;
	
	
	
	/**
	 * 
	 */
	public Step(String number, String text, List<Callout> callouts, List<Reference> references, List<Step> substeps) {
		this.number = number;
		this.text = text;
		this.execNote = null;
		this.callouts = callouts;
		this.references = references;
		this.substeps = substeps;
	}

	
	
	/**
	 * @return the execNote
	 */
	public ExecNote getExecNote() {
		return execNote;
	}



	/**
	 * @param execNote the execNote to set
	 */
	public void setExecNote(ExecNote execNote) {
		this.execNote = execNote;
	}



	/**
	 * @return the callouts
	 */
	public List<Callout> getCallouts() {
		return callouts;
	}



	/**
	 * @param callouts the callouts to set
	 */
	public void setCallouts(List<Callout> callouts) {
		this.callouts = callouts;
	}



	/**
	 * @return the references
	 */
	public List<Reference> getReferences() {
		return references;
	}



	/**
	 * @param references the references to set
	 */
	public void setReferences(List<Reference> references) {
		this.references = references;
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
	 * @return the substeps
	 */
	public List<Step> getSubsteps() {
		return substeps;
	}



	/**
	 * @param substeps the substeps to set
	 */
	public void setSubsteps(List<Step> substeps) {
		this.substeps = substeps;
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
