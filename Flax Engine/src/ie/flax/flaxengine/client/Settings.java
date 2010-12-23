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

/**
 * @author carllange
 *
 */
public class Settings implements JsonSerializable, onFileLoadedEventHandler{
	
	private String imageDirectoryPath;
	private String mapDirectoryPath;
	private Boolean collisionOn;
	private String UID;
	private String serverPath;
	private Boolean fullscreenOn;
	
	/**
	 *  This initialises the settings to their defaults.
	 *  
	 *  By default, IMAGE_DIRECTORY_PATH is "/images/", MAP_DIRECTORY_PATH is "/maps/", collisionOn is true, UID is 0, serverPath is "/server.php"
	 */
	public Settings() {
		imageDirectoryPath = "/images/";
		mapDirectoryPath = "/maps/";
		UID = "6cSNeQPq8qkcWzUBUe/LF1wPyC3iKJpO";
		collisionOn = true;
		serverPath = "/server.php";
		fullscreenOn = true;
	}
	
		
	/**
	 * This initialises the settings from a JSON file on the server.
	 * 
	 * @param settingsJSONFile The name and extension of the JSON file on the server.
	 */
	public Settings(String pathToSettingFile) {
		//TODO: Settings doesn't yet construct itself from a JSON file
		EventBus.handlerManager.addHandler(onFileLoadedEvent.TYPE, this);	
		FileHandle.readFileAsString(pathToSettingFile, this.toString());		
	}
	
	
	/**
	 * A method to programatically configure the settings. All settings are set by this method.
	 * 
	 * @param imgDirPath
	 * @param mapDirPath
	 * @param collision
	 * @param UID
	 * @param serverPath
	 */
	public Settings(String imgDirPath, String mapDirPath, Boolean collision, String UID, String serverPath) {
		imageDirectoryPath = imgDirPath;
		mapDirectoryPath = mapDirPath;
		collisionOn = collision;
		this.UID = UID;
		this.serverPath = serverPath;
	}


	@Deprecated
	public void setImageDirectoryPath(String imgDirPath) {
		imageDirectoryPath = imgDirPath;
	}

	@Deprecated
	public void setMapDirectoryPath(String mapDirPath) {
		mapDirectoryPath = mapDirPath;
	}

	public void setCollisionOn(Boolean collisionOn) {
		this.collisionOn = collisionOn;
	}

	public String getImageDirectoryPath() {
		return imageDirectoryPath;
	}

	public String getMapDirectoryPath() {
		return mapDirectoryPath;
	}

	public Boolean getCollisionOn() {
		return collisionOn;
	}
	
	public String getUID(){
		return UID;
	}
	
	@Deprecated
	public void setUID(String UID){
		this.UID = UID;
	}
	
	@Deprecated
	public void setServerPath(String serverPath){
		this.serverPath = serverPath;
	}
	
	public String getServerPath(){
		return serverPath;
	}
	
	@Deprecated
	public void setFullscreenOn(Boolean fullscreenOn) {
		this.fullscreenOn = fullscreenOn;
	}


	public Boolean getFullscreenOn() {
		return fullscreenOn;
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
		this.imageDirectoryPath = temp.imageDirectoryPath;
		this.mapDirectoryPath = temp.mapDirectoryPath;
		this.UID = temp.UID;
		this.serverPath = temp.serverPath;
		
		FLog.info("The settings object was constructed from a file sucessfully");
		}
	
	}
}