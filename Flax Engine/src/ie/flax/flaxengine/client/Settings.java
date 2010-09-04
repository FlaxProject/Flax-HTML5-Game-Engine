package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.onFileLoadedEvent;
import ie.flax.flaxengine.client.events.onFileLoadedEventHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;
import com.kfuntak.gwt.json.serialization.client.Serializer;

import com.allen_sauer.gwt.log.client.Log;

/**
 * @author carllange
 *
 */
public class Settings implements JsonSerializable{
	

	private String IMAGE_DIRECTORY_PATH;	//TODO should be lowercase (or final)
	private String MAP_DIRECTORY_PATH;		//TODO should be lowercase (or final)
	private Boolean collisionOn;
	
	/**
	 *  This initialises the settings to their defaults.
	 *  
	 *  By default, IMAGE_DIRECTORY_PATH is "/images/", MAP_DIRECTORY_PATH is "/maps/", and collisionOn is true.
	 */
	public Settings() {
		IMAGE_DIRECTORY_PATH = "/images/";
		MAP_DIRECTORY_PATH = "/maps/";
		collisionOn = true;
	}
	
	
	
	/**
	 * This initialises the settings from an XML file on the server.
	 * 
	 * @param settingsXmlFile The name and extension of the XML file on the server.
	 */
	public Settings(String Json) {
		Serializer serializer = (Serializer) GWT.create(Serializer.class);
		Settings temp = (Settings) serializer.deSerialize(Json,"ie.flax.flaxengine.client.Settings");
		
		this.collisionOn = temp.collisionOn;
		this.IMAGE_DIRECTORY_PATH = temp.IMAGE_DIRECTORY_PATH;
		this.MAP_DIRECTORY_PATH = temp.MAP_DIRECTORY_PATH;
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

	
	public String getIMAGE_DIRECTORY_PATH() {
		return IMAGE_DIRECTORY_PATH;
	}

	public void setIMAGE_DIRECTORY_PATH(String iMAGEDIRECTORYPATH) {
		IMAGE_DIRECTORY_PATH = iMAGEDIRECTORYPATH;
	}

	public String getMAP_DIRECTORY_PATH() {
		return MAP_DIRECTORY_PATH;
	}

	public void setMAP_DIRECTORY_PATH(String mAPDIRECTORYPATH) {
		MAP_DIRECTORY_PATH = mAPDIRECTORYPATH;
	}

	public Boolean getCollisionOn() {
		return collisionOn;
	}

	public void setCollisionOn(Boolean collisionOn) {
		this.collisionOn = collisionOn;
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