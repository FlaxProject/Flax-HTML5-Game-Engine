package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.onFileLoadedEvent;
import ie.flax.flaxengine.client.events.onFileLoadedEventHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

import com.allen_sauer.gwt.log.client.Log;

/**
 * @author carllange
 *
 */
public class Settings {
	
	private String name;
	
	private String IMAGE_DIRECTORY_PATH;	//TODO should be lowercase (or final)
	private String MAP_DIRECTORY_PATH;		//TODO should be lowercase (or final)
	private Boolean collisionOn;
	
	/**
	 *  This initialises the settings to their defaults.
	 *  
	 *  By default, IMAGE_DIRECTORY_PATH is "/images/", MAP_DIRECTORY_PATH is "/maps/", and collisionOn is true.
	 */
	public void init() {
		IMAGE_DIRECTORY_PATH = "/images/";
		MAP_DIRECTORY_PATH = "/maps/";
		collisionOn = true;
	}
	
	/**
	 * This initialises the settings from an XML file on the server.
	 * 
	 * @param settingsXmlFile The name and extension of the XML file on the server.
	 */
	public Settings(String settingsXmlFile) {
		
	}
	
	/**
	 * A method to programatically configure the settings. Collision is on by default.
	 * 
	 * @param imgDirPath The directory path to the images
	 * @param mapDirPath The directory path to the images
	 */
	public Settings(String imgDirPath, String mapDirPath) {
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
	public Settings(String imgDirPath, String mapDirPath, Boolean collision) {
		IMAGE_DIRECTORY_PATH = imgDirPath;
		MAP_DIRECTORY_PATH = mapDirPath;
		collisionOn = collision;
	}

	public String getImageDirectoryPath() {
		return IMAGE_DIRECTORY_PATH;
	}

	public String getMapDirectoryPath() {
		return MAP_DIRECTORY_PATH;
	}

	public void setCollision(Boolean collision) {
		this.collisionOn = collision;
	}

	public Boolean getCollision() {
		return collisionOn;
	}
}