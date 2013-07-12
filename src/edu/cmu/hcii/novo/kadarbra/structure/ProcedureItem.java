/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.structure;

import java.util.List;

/**
 * An interface for anything that can be shown as a main 
 * item on a procedure. Mainly just steps and cycles.
 * 
 * @author Chris
 *
 */
public interface ProcedureItem {

	public List<ProcedureItem> getChildren();
	
	public void setChildren(List<ProcedureItem> children);
	
	public ProcedureItem getChild(int index);
	
	public void addChild(ProcedureItem child);
	
	public int getNumChildren();
	
	//Oh god I hate myself for this one
	public boolean isCycle();
}
