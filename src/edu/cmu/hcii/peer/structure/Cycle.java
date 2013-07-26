/**
 * 
 */
package edu.cmu.hcii.peer.structure;

import java.io.Serializable;
import java.util.List;

/**
 * An object representig a cycle, or, a repetition of steps.
 * Contains a list of steps, the number of repetitions, and
 * any notes related to the group.
 * 
 * @author Chris
 *
 */
public class Cycle implements ProcedureItem, Serializable {

	/**
	 * Auto-Generated serial id
	 */
	private static final long serialVersionUID = 3272545299782918475L;
	private int reps;
	private List<CycleNote> notes;
	private List<ProcedureItem> children;
	
	
	
	/**
	 * Create a new cycle object with the given reps, notes, and 
	 * children.
	 */
	public Cycle(int reps, List<CycleNote> notes, List<ProcedureItem> children) {
		this.reps = reps;
		this.notes = notes;
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
	 * @return the deep number of children
	 */
	public int getNumChildrenDeep() {
		int result = children.size();
		for (int i = 0; i < children.size(); i++) {
			result += children.get(i).getNumChildrenDeep();
		}
		return result;
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
	 * @return the notes
	 */
	public List<CycleNote> getNotes() {
		return notes;
	}



	/**
	 * @param notes the notes to set
	 */
	public void setNotes(List<CycleNote> notes) {
		this.notes = notes;
	}



	@Override
	public boolean isCycle() {
		return true;
	}

}
