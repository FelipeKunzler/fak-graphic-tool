package fak.graphicTool;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	public static final String BUNDLE_NAME_EN = "messages_en";
	public static final String BUNDLE_NAME_PT = "messages_pt";
	
	public static String BUNDLE_NAME = BUNDLE_NAME_EN;
	
	private static ResourceBundle RecouceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}
	
	public static void setResourceBundle(String bundleName){
		RecouceBundle = ResourceBundle.getBundle(bundleName);
		
	}

	public static String getString(String key) {
		try {
			return RecouceBundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
