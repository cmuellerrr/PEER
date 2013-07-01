/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.structure;

import java.io.Serializable;

import android.os.Environment;
import edu.cmu.hcii.novo.kadarbra.R;

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
	
	
	
	/**
	 * Create a new reference object with the given attributes.
	 * 
	 * @param type
	 * @param name
	 * @param description
	 * @param url
	 */
	public Reference(RType type, String name, String description, String url) {
		this.type = type;
		this.name = name;
		this.description = description;
		
		//TODO fix this.  where are we storing the media files?
		this.url = Environment.getExternalStorageDirectory().toString() + R.string.reference_directory + url;
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
}
