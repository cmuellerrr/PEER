package edu.cmu.hcii.novo.kadarbra.page;


/**
 * All drawer pages implement this iterface
 * 
 */
public interface DrawerPageInterface {
	
	public static String DRAWER_GROUND = "_drawerGround";
	public static String DRAWER_NAVIGATION = "_drawerNavigation";
	public static String DRAWER_STOWAGE = "_drawerStowage";
	public static String DRAWER_ANNOTATIONS = "_drawerAnnotations";
	public static String DRAWER_CYCLE_SELECT = "_drawerCycleSelect";
	public static String DRAWER_NONE = "_drawerNone";

	
	/**
	 * 
	 * @return name of drawer page
	 */
	public String getDrawerType();

}
