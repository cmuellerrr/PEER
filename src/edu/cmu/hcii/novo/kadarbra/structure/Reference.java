/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.structure;

import java.io.Serializable;
import java.util.List;

/**
 * A class representing reference objects.  Can be image, 
 * video, audio, or a table.
 * 
 * @author Chris
 *
 */
public class Reference implements Serializable {
	
	public enum RType {
		IMAGE, VIDEO, AUDIO, TABLE
	}
	
	/**
	 * Auto-Generated serial id
	 */
	private static final long serialVersionUID = 1652786417697213298L;
	private RType type;
	private String name;
	private String description;
	private String url;
	private List<List<String>> table;
	
	
	
	/**
	 * Create a new reference object with the given attributes.
	 * 
	 * @param type
	 * @param name
	 * @param description
	 * @param url
	 */
	public Reference(RType type, String name, String description, String url, List<List<String>> table) {
		this.type = type;
		this.name = name;
		this.description = description;
		//TODO right now we store them in assets
		this.url = url;
		this.table = table;
	}



	/**
	 * @return the type
	 */
	public RType getType() {
		return type;
	}



	/**
	 * @param type the type to set
	 */
	public void setType(RType type) {
		this.type = type;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}



	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}



	/**
	 * @return the table
	 */
	public List<List<String>> getTable() {
		return table;
	}



	/**
	 * @param table the table to set
	 */
	public void setTable(List<List<String>> table) {
		this.table = table;
	}
}
