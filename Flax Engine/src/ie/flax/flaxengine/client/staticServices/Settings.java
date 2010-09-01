package ie.flax.flaxengine.client.staticServices;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;

import com.allen_sauer.gwt.log.client.Log;

/**
 * @author carllange
 *
 */
public class Settings {
	private static String IMAGE_DIRECTORY_PATH = "/images/";	//TODO should be lowercase (or final)
	private static String MAP_DIRECTORY_PATH = "/maps/";		//TODO should be lowercase (or final)
	private static Boolean collisionOn = true;
	
	
	/**
	 * @param settingsXmlFile
	 */
	public void init(String settingsXmlFile) {
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
	 * @param imgDirPath
	 * @param mapDirPath
	 */
	public void init(String imgDirPath, String mapDirPath) {
		// by default, collision is true, so this constructor should be enough.
		IMAGE_DIRECTORY_PATH = imgDirPath;
		MAP_DIRECTORY_PATH = mapDirPath;
	}
	
	/**
	 * @param imgDirPath
	 * @param mapDirPath
	 * @param collision
	 */
	public void init(String imgDirPath, String mapDirPath, Boolean collision) {
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