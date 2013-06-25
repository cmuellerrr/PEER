/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A class to represent a procedure.  This will hold
 * all of the necessary information for the app to 
 * display/navigate a full procedure.
 * 
 * @author Chris
 *
 */
public class Procedure implements Serializable {

	/**
	 * Auto-Generated serial id
	 */
	private static final long serialVersionUID = -5333895722021665909L;
	private String number;
	private String title;
	private String objective;
	private String duration;
	private List<ExecNote> execNotes;
	private List<StowageItem> stowageItems;
	private List<Step> steps;
	private int stepIndex;
	
	/**
	 * Create a procedure object with the given properties.  The index is then set to
	 * 0.
	 * 
	 * @param title
	 * @param objective
	 * @param duration
	 * @param steps
	 */
	public Procedure(String number, String title, String objective, String duration, List<Step> steps) {
		this.number = number;
		this.title = title;
		this.objective = objective;
		this.duration = duration;
		this.execNotes = new ArrayList<ExecNote>();
		this.stowageItems = new ArrayList<StowageItem>();
		this.steps = steps;
		this.stepIndex = 0;
	}
	
	/**
	 * Create a procedure object with the given properties.  The index is then set to
	 * 0.
	 * 
	 * @param number
	 * @param title
	 * @param objective
	 * @param duration
	 * @param execNotes
	 * @param stowageItems
	 * @param steps
	 */
	public Procedure(String number, String title, String objective, String duration, 
			List<ExecNote> execNotes, List<StowageItem> stowageItems, List<Step> steps) {
		this.number = number;
		this.title = title;
		this.objective = objective;
		this.duration = duration;
		this.execNotes = execNotes;
		this.stowageItems = stowageItems;
		this.steps = steps;
		this.stepIndex = 0;
	}
	
	/**
	 * Add the given step to the end of the procedure.
	 * 
	 * @param step the step to add
	 */
	public void addStep(Step step) {
		steps.add(step);
	}
	
	/**
	 * Add the given step to the procedure at the given index.
	 * 
	 * @param index the location to put the step
	 * @param step the step to add
	 */
	public void addStepAt(int index, Step step) {
		if (index <= steps.size()) {
			steps.add(index, step);
			if (index <= stepIndex) stepIndex++;
		}
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the objective
	 */
	public String getObjective() {
		return objective;
	}

	/**
	 * @param objective the objective to set
	 */
	public void setObjective(String objective) {
		this.objective = objective;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * @return the execNotes
	 */
	public List<ExecNote> getExecNotes() {
		return execNotes;
	}

	/**
	 * @param execNotes the execNotes to set
	 */
	public void setExecNotes(List<ExecNote> execNotes) {
		this.execNotes = execNotes;
	}

	/**
	 * 
	 * @return the number of execution notes
	 */
	public int getNumExecNotes() {
		return execNotes.size();
	}

	/**
	 * @return the stowageItems
	 */
	public List<StowageItem> getStowageItems() {
		return stowageItems;
	}

	/**
	 * @param stowageItems the stowageItems to set
	 */
	public void setStowageItems(List<StowageItem> stowageItems) {
		this.stowageItems = stowageItems;
	}

	/**
	 * 
	 * @return the number of stowage items
	 */
	public int getNumStowageItems() {
		return stowageItems.size();
	}

	/**
	 * @param index
	 * @return the step at index
	 */
	public Step getStep(int index) {
		return steps.get(index);
	}

	/**
	 * @return the steps
	 */
	public List<Step> getSteps() {
		return steps;
	}

	/**
	 * @param steps the steps to set
	 */
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	/**
	 * @return the number of steps
	 */
	public int getNumSteps() {
		return steps.size();
	}

	/**
	 * @return the stepIndex
	 */
	public int getStepIndex() {
		return stepIndex;
	}

	/**
	 * @param stepIndex the stepIndex to set
	 */
	public void setStepIndex(int stepIndex) {
		this.stepIndex = stepIndex;
	}
}
