package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.EventBus;
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
public class Settings implements JsonSerializable, onFileLoadedEventHandler{
	
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
	public Settings(String pathToSettingFile) {
		
		EventBus.handlerManager.addHandler(onFileLoadedEvent.TYPE, this);	
		FileHandle.readFileAsString(pathToSettingFile,this.toString());
		
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
	
	/**
	 * Pass this method JSON and it gives you back an FMap object which you can
	 * then assign to your object via FMap myMap = JsonToFMap(String Json);
	 * 
	 * @param JSON
	 * @return
	 */
	public  Settings JsonToSettings(String Json) {
		Serializer serializer = (Serializer) GWT.create(Serializer.class);
		return (Settings) serializer.deSerialize(Json,"ie.flax.flaxengine.client.Settings");
	}

	/**
	 * Creates a JSON string from the current FMap object
	 * 
	 * @return String of JSON
	 */
	public String settingsToJson() {
		Serializer serializer = (Serializer) GWT.create(Serializer.class);
		return serializer.serialize(this);
	}
	



	@Override
	public void onFileLoaded(onFileLoadedEvent e) {
	
		if(this.toString().equalsIgnoreCase(e.getId()))
		{
		Settings temp = JsonToSettings(e.getDataLoadedFromFile());
		
		this.collisionOn = temp.collisionOn;
		this.IMAGE_DIRECTORY_PATH = temp.IMAGE_DIRECTORY_PATH;
		this.MAP_DIRECTORY_PATH = temp.MAP_DIRECTORY_PATH;
		
		Log.info("The settings object was constructed from a file sucessfully");
		}
	
	}
}