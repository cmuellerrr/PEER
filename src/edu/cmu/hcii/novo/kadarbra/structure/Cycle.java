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
public class Cycle implements ProcedureItem, Serializable {

	/**
	 * Auto-Generated serial id
	 */
	private static final long serialVersionUID = 3272545299782918475L;
	private int reps;
	private String text;
	private List<ProcedureItem> children;
	
	
	
	/**
	 * 
	 */
	public Cycle(int reps, List<ProcedureItem> children) {
		this.reps = reps;
		this.children = children;
	}

	
	
	/**
	 * Add the given step to the end of the procedure.
	 * 
	 * @param step the step to add
	 */
	public void addChild(ProcedureItem child) {
		children.add(child);
	}
	
	
	
	/**
	 * @param index
	 * @return the step at index
	 */
	public ProcedureItem getChild(int index) {
		return children.get(index);
	}
	
	
	
	/**
	 * @return the substeps
	 */
	public List<ProcedureItem> getChildren() {
		return children;
	}



	/**
	 * @param children the substeps to set
	 */
	public void setChildren(List<ProcedureItem> children) {
		this.children = children;
	}



	/**
	 * @return the number of steps
	 */
	public int getNumChildren() {
		return children.size();
	}



	/**
	 * @return the reps
	 */
	public int getReps() {
		return reps;
	}



	/**
	 * @param reps the reps to set
	 */
	public void setReps(int reps) {
		this.reps = reps;
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



	@Override
	public boolean isCycle() {
		return true;
	}

}
