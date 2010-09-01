package ie.flax.flaxengine.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;

import com.allen_sauer.gwt.log.client.Log;

/**
 * @author carllange
 *
 */
public class Settings {
	private static String IMAGE_DIRECTORY_PATH;	//TODO should be lowercase (or final)
	private static String MAP_DIRECTORY_PATH;		//TODO should be lowercase (or final)
	private static Boolean collisionOn;
	
	/**
	 *  This initialises the settings to their defaults.
	 *  
	 *  By default, IMAGE_DIRECTORY_PATH is "/images/", MAP_DIRECTORY_PATH is "/maps/", and collisionOn is true.
	 */
	public static void init() {
		IMAGE_DIRECTORY_PATH = "/images/";
		MAP_DIRECTORY_PATH = "/maps/";
		collisionOn = true;
	}
	
	/**
	 * This initialises the settings from an XML file on the server.
	 * 
	 * @param settingsXmlFile The name and extension of the XML file on the server.
	 */
	public static void init(String settingsXmlFile) {
		FileHandleServiceAsync FileHandle = (FileHandleServiceAsync) GWT.create(FileHandleService.class);
		
		FileHandle.readFileAsXml(settingsXmlFile, new AsyncCallback<Document>() {
			
			@Override
			public void onSuccess(Document xml) {
				// get imgdir path
				Text imgPathNode = (Text)xml.getElementsByTagName("image directory path").item(0).getFirstChild();
				IMAGE_DIRECTORY_PATH = imgPathNode.getData();
			
				// gwt mapdir path
				Text mapPathNode = (Text)xml.getElementsByTagName("map directory path").item(0).getFirstChild();
				MAP_DIRECTORY_PATH = mapPathNode.getData();
				
				// collision
				Text collisionNode = (Text)xml.getElementsByTagName("collision").item(0).getFirstChild();
				if ((collisionNode.getData() == "true") || (collisionNode.getData() == "on") || (collisionNode.getData() == "yes")) {
					collisionOn = true;
				}
				else {
					collisionOn = false;
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Log.error("Could not read settings. This may be because your connection to the server isn't working. Try using another constructor.");
			}
		});
		
	}
	
	/**
	 * A method to programatically configure the settings. Collision is on by default.
	 * 
	 * @param imgDirPath The directory path to the images
	 * @param mapDirPath The directory path to the images
	 */
	public static void init(String imgDirPath, String mapDirPath) {
		IMAGE_DIRECTORY_PATH = imgDirPath;
		MAP_DIRECTORY_PATH = mapDirPath;
		collisionOn = true;
	}
	
	/**
	 * A method to programatically configure the settings. All settings are set by this method.
	 * 
	 * @param imgDirPath
	 * @param mapDirPath
	 * @param collision
	 */
	public static void init(String imgDirPath, String mapDirPath, Boolean collision) {
		IMAGE_DIRECTORY_PATH = imgDirPath;
		MAP_DIRECTORY_PATH = mapDirPath;
		collisionOn = collision;
	}

	public static String getImageDirectoryPath() {
		return IMAGE_DIRECTORY_PATH;
	}

	public static String getMapDirectoryPath() {
		return MAP_DIRECTORY_PATH;
	}

	public static void setCollision(Boolean collision) {
		Settings.collisionOn = collision;
	}

	public static Boolean getCollision() {
		return collisionOn;
	}
}