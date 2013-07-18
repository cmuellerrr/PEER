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
public class Step implements ProcedureItem, Serializable {
	
	/**
	 * Auto-Generated serial id
	 */
	private static final long serialVersionUID = -4348295150944687945L;
	private String number;
	private String consequent;
	private String text;
	private ExecNote execNote;
	private List<Callout> callouts; 
	private List<Reference> references;
	private List<ProcedureItem> children;
	private boolean timer;
	private boolean inputAllowed;
		
	
	/**
	 * 
	 */
	public Step(String number, String text, List<Callout> callouts, List<Reference> references, 
			List<ProcedureItem> children, boolean timer, boolean inputAllowed) {
		
		this.number = number;
		this.text = text;
		this.consequent = "";
		this.execNote = null;
		this.callouts = callouts;
		this.references = references;
		this.children = children;
		this.timer = timer;
		this.inputAllowed = inputAllowed;
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
	 * @return the children
	 */
	public List<ProcedureItem> getChildren() {
		return children;
	}



	/**
	 * @param children the children to set
	 */
	public void setChildren(List<ProcedureItem> children) {
		this.children = children;
	}



	/**
	 * @return the number of children
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
	 * Return if the step has conditional logic
	 * @return if the consequent is populated
	 */
	public boolean isConditional() {
		return !(consequent == null || consequent.equals(""));
	}



	/**
	 * @return the consequent
	 */
	public String getConsequent() {
		return consequent;
	}



	/**
	 * @param consequent the consequent to set
	 */
	public void setConsequent(String consequent) {
		this.consequent = consequent;
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



	/**
	 * @return the hasInput
	 */
	public boolean isInputAllowed() {
		return inputAllowed;
	}



	/**
	 * @param hasInput the hasInput to set
	 */
	public void setInputAllowed(boolean inputAllowed) {
		this.inputAllowed = inputAllowed;
	}



	@Override
	public boolean isCycle() {
		return false;
	}
	
	/**
	 * @return whether the step has a timer
	 */
	public boolean getTimer(){
		return timer;
	}
}
