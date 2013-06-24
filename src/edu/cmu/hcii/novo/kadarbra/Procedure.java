/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra;

import java.io.Serializable;
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
	private String title;
	private String objective;
	private String duration;
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
	public Procedure(String title, String objective, String duration, List<Step> steps) {
		this.title = title;
		this.objective = objective;
		this.duration = duration;
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
	 * @param index
	 * @return the step at index
	 */
	public Step getStep(int index) {
		return steps.get(index);
	}

	/**
	 * @return the number of steps
	 */
	public int getNumSteps() {
		return steps.size();
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
