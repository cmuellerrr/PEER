package edu.cmu.hcii.peer.page;


/**
 * All drawer pages implement this interface.  It helps to see
 * what content is within the drawer.
 * 
 */
public interface DrawerPageInterface {
	
	public static final String DRAWER_GROUND = "_drawerGround";
	public static final String DRAWER_NAVIGATION = "_drawerNavigation";
	public static final String DRAWER_STOWAGE = "_drawerStowage";
	public static final String DRAWER_ANNOTATIONS = "_drawerAnnotations";
	public static final String DRAWER_CYCLE_SELECT = "_drawerCycleSelect";
	public static final String DRAWER_NONE = "_drawerNone";

	
	/**
	 * 
	 * @return name of drawer page
	 */
	public String getDrawerType();

}
