/**
 * 
 */
package edu.cmu.hcii.novo.kadarbra.structure;

import java.io.Serializable;

/**
 * A class to represent stowage items.  This is the
 * stuff that gets put into the stowage table.
 * 
 * @author Chris
 *
 */
public class StowageItem implements Serializable {

	/**
	 * Auto-Generated serial id
	 */
	private static final long serialVersionUID = -5599609840863259369L;
	//TODO location?
	private String name;
	private int quantity;
	private String itemCode;
	private String binCode;
	private String text;
	
	/**
	 * Create a stowage item with the given parameters
	 * 
	 * @param name
	 * @param quantity
	 * @param itemCode
	 * @param binCode
	 * @param text
	 */
	public StowageItem(String  name, int quantity, String itemCode, String binCode,
			String text) {
		this.name = name;
		this.quantity = quantity;
		this.itemCode = itemCode;
		this.binCode = binCode;
		this.text = text;
	}
	
	/**
	 * Create a stowage item with the given parameters
	 * 
	 * @param name
	 * @param itemCode
	 * @param binCode
	 */
	public StowageItem(String name, String itemCode, String binCode) {
		this.name = name;
		this.itemCode = itemCode;
		this.binCode = binCode;
		
		this.quantity = 1;
		this.text = "";
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
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the binCode
	 */
	public String getBinCode() {
		return binCode;
	}

	/**
	 * @param binCode the binCode to set
	 */
	public void setBinCode(String binCode) {
		this.binCode = binCode;
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
