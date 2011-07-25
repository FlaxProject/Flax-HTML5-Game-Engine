package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;
import ie.flax.flaxengine.client.events.onFileLoadedEventHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;
import com.kfuntak.gwt.json.serialization.client.Serializer;

/**
 * @author carllange
 */
public class Settings implements JsonSerializable, onFileLoadedEventHandler {

    private String imageDirectoryPath;
    private String mapDirectoryPath;
    private boolean collisionOn;
    private String UID;
    private String serverPath;
    private int width, height;
	private String insertId;

    /**
     * This initialises the settings to their defaults. By default,
     * IMAGE_DIRECTORY_PATH is "/images/", MAP_DIRECTORY_PATH is "/maps/",
     * collisionOn is true, UID is 0, serverPath is "/server.php" Fullscreen is
     * on by default.
     */
    public Settings() {
        imageDirectoryPath = "/images/";
        mapDirectoryPath = "/maps/";
        UID = "6cSNeQPq8qkcWzUBUe/LF1wPyC3iKJpO";
        collisionOn = true;
        serverPath = "/server.php";
        width = (Window.getClientWidth());
        height = (Window.getClientHeight());
    }

    /**
     * This initialises the settings from a JSON file on the server.
     * 
     * @param settingsJSONFile
     *            The name and extension of the JSON file on the server.
     */
    public Settings(String pathToSettingFile) {
        // TODO: Settings doesn't yet construct itself from a JSON file
        EventBus.handlerManager.addHandler(onFileLoadedEvent.TYPE, this);
        FileHandle.readFileAsString(pathToSettingFile, toString());
    }

    /**
     * A method to programatically configure the settings. All settings are set
     * by this method.
     * 
     * @param imgDirPath
     * @param mapDirPath
     * @param collision
     * @param UID
     * @param serverPath
     * @param insertID This is needed for width and height.
     */
    public Settings(String imgDirPath, String mapDirPath, boolean collision,
            String UID, String serverPath, String insertID) {
        imageDirectoryPath = imgDirPath;
        mapDirectoryPath = mapDirPath;
        collisionOn = collision;
        this.UID = UID;
        this.serverPath = serverPath;
        setContainer(insertID);
    }

    public boolean getCollisionOn() {
        return collisionOn;
    }

    public int getHeight() {
        return height;
    }

    public String getImageDirectoryPath() {
        return imageDirectoryPath;
    }

    public String getMapDirectoryPath() {
        return mapDirectoryPath;
    }

    public String getServerPath() {
        return serverPath;
    }

    public String getUID() {
        return UID;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Pass this method JSON and it gives you back an FMap object which you can
     * then assign to your object via FMap myMap = JsonToFMap(String Json);
     * 
     * @param JSON
     * @return
     */
    public Settings jsonToSettings(String Json) {
        Serializer serializer = (Serializer) GWT.create(Serializer.class);
        return (Settings) serializer.deSerialize(Json,
                "ie.flax.flaxengine.client.Settings");
    }

    @Override
    public void onFileLoaded(onFileLoadedEvent e) {

        if (toString().equalsIgnoreCase(e.getId())) {
            Settings temp = jsonToSettings(e.getDataLoadedFromFile());

            collisionOn = temp.collisionOn;
            imageDirectoryPath = temp.imageDirectoryPath;
            mapDirectoryPath = temp.mapDirectoryPath;
            UID = temp.UID;
            serverPath = temp.serverPath;

            FLog.info("The settings object was constructed from a file sucessfully");
        }

    }

    @Deprecated
    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    @Deprecated
    public void setHeight(int canvasHeight) {
        height = canvasHeight;
    }

    @Deprecated
    public void setImageDirectoryPath(String imgDirPath) {
        imageDirectoryPath = imgDirPath;
    }

    @Deprecated
    public void setMapDirectoryPath(String mapDirPath) {
        mapDirectoryPath = mapDirPath;
    }

    @Deprecated
    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
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

    @Deprecated
    public void setUID(String UID) {
        this.UID = UID;
    }

    @Deprecated
    public void setWidth(int canvasWidth) {
        width = canvasWidth;
    }

	public void setContainer(String insertId) {
		this.insertId = insertId;
		 width = RootPanel.get(insertId).getOffsetWidth();
	     height = RootPanel.get(insertId).getOffsetHeight();
	}

	/**
	 * This returns a reference to the panel in which all engine HTML most be inserted into and event hanlders registered with
	 * @return
	 */
	public RootPanel getContainer() {
		return RootPanel.get(insertId);
	}
	
	@Deprecated
	public String getInsertId() {
		return this.insertId;
	}
	
	@Deprecated
	public void setInsertId(String insertId) {
		this.insertId = insertId;
	}
}