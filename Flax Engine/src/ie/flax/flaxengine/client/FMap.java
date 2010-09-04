package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;
import ie.flax.flaxengine.client.events.onFileLoadedEventHandler;

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;
import com.kfuntak.gwt.json.serialization.client.Serializer;

/**
 * FMap is an automatic class, which given a filePath to a JSON map file will 
 * construct itself by magic. FMap does all the work for you.
 * <br><br>
 * 
 * The map object is basically the environment, it defines the tiles, the
 * objects, the entity in the world. It is a very automated object in that it
 * takes 1 parameter for its constructor. The path to the xml file which
 * contains all the information about the map. Below is a template example of
 * the information.
 * <br><br>
 * The map file is the game information file really. It contains almost all of
 * the info about the different objects that will be created. The images for
 * each object are also loaded when this map file is read. The tiles are all
 * stored on one sheet and objects and entitys have their own sperate images due
 * to the fact they may have animations.
 * <br><br>
 * Thought if a developer would like to programmatically add in all the objects
 * and entitys they can do so. Though the map object requires the xml file for
 * at-lest the tiles of the map.
 * 
 * @author Ciaran McCann
 * 
 */
public class FMap implements JsonSerializable, onFileLoadedEventHandler {

	private int width;
	private int height;
	private int tileSize;
	private String name;
	private boolean Loaded; 
	
	/**
	 * This holds the string which is used to reference the tileSheet image in the imageLibary
	 */
	private String tileSheet;	
	
	private List<FTile> tiles;
	private List<FObject> objects = new ArrayList<FObject>();
	private List<FEntity> entities = new ArrayList<FEntity>();

	/**
	 * FMap constructor takes in the map path and use the static service file
	 * handle to read the map file which is has the JSON of the map. The JSON
	 * string is then returned to the FMap class via an event due to
	 * asynchronous
	 * 
	 * @param mapPath
	 *            address to the map file to be loaded.
	 */
	public FMap(String mapPath) {
		
		name = mapPath;
			
		EventBus.handlerManager.addHandler(onFileLoadedEvent.TYPE, this);
		FileHandle.readFileAsString(mapPath, this.toString());//Makes a request for the map file
			
	}
	
	/**
	 * DO NOT USE THIS Constructor -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public FMap() {
		
	}
		
	/**
	 * Gets the name of the map file which this map object was created from
	 * @return String name of map
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Sets the tileSize of the map 
	 * @param tileSize
	 */
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}


	/**
	 * Gets the tileSize used by the current map
	 * @return int tilesize
	 */
	public int getTileSize() {
		return tileSize;
	}

	
	/**
	 * Sets the tileSheet of the engine
	 * @param tileSheet
	 */
	public void setTileSheet(String tileSheet) {
		this.tileSheet = tileSheet;
	}

	/**
	 * 
	 * @return width (int)
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 
	 * @return height (int)
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * This is used to reference the imageLibary with the name of the tileSheet
	 * eg. imageLibary.get("myTileSheet")
	 * 
	 * @return tileSheet name
	 */
	public String getTileSheet() {
		return tileSheet;
	}

	/**
	 * Draws the map
	 */
	public void draw() {
		// TODO implement camera scrolling in the map drawing		
		for (int y = 0; y < height; y++) 
		{
			for (int x = 0; x < width; x++)
			{
				FTile temp = tiles.get(x + (y * this.width));
				Graphic.drawTile(tileSheet, temp.getTexture(), this.tileSize, temp.getX(), temp.getY());
			}
		}
		
		Log.info("Map was drawn sucessfully");
	}

	/**
	 * This adds the given FEntity object or objects that are derived from FEntity
	 * to the map object.
	 * @param entity
	 */
	public void addEntity(FEntity entity)
	{
		entities.add(entity);
	}
	
	/**
	 * This adds the given FObject object or objects that are derived from FObject
	 * to the map object.
	 * @param FObject 
	 */
	public void addObjects(FObject object)
	{
		objects.add(object);
	}
	
	/**
	 * Pass this method JSON and it gives you back an FMap object which you can
	 * then assign to your object via FMap myMap = JsonToFMap(String Json);
	 * 
	 * @param JSON
	 * @return
	 */
	public  FMap JsonToFMap(String Json) {
		Serializer serializer = (Serializer) GWT.create(Serializer.class);
		return (FMap) serializer.deSerialize(Json,"ie.flax.flaxengine.client.FMap");
	}

	/**
	 * Creates a JSON string from the current FMap object
	 * 
	 * @return String of JSON
	 */
	public String FMapToJson() {
		Serializer serializer = (Serializer) GWT.create(Serializer.class);
		return serializer.serialize(this);
	}
	
	

	/**
	 * This method is run when a onFileLoaded event is fired. It then checks was
	 * the file loaded request by itself. Which it does so by the ID. It then constructs the object
	 * 
	 * @param e
	 *            event object
	 */
	@Override
	public void onFileLoaded(onFileLoadedEvent e) {
			
					
		if(this.toString().equalsIgnoreCase(e.getId()))
		{

			/**
			 * Creates a temp FMap object from the JSON string which is stored
			 * in the event object which was pulled from the server
			 */
			FMap temp = JsonToFMap(e.getDataLoadedFromFile()); 
						
			/**
			 * It would be nicer to go this =
			 * FMap.JsonToFMap(e.getDataLoadedFromFile()); though we can't do
			 * that. It would have to be outside the class which wouldn't work as
			 * well with the ID's etc.
			 */
			this.entities = temp.entities;
			this.tiles = temp.tiles;
			this.objects = temp.objects;
			this.tileSheet = temp.tileSheet;
			this.tileSize = temp.tileSize;
			this.width = temp.width;
			//TODO check if the image (tileSheet) are loaded if not load them
			
			Log.info("An FMap object of name [" + this.name + "]; was constructed from a file sucessfully");
		}
	}
	
	
	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public List<FTile> getTiles() {
		return tiles;
	}
	
	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public void setObjects(List<FObject> objects) {
		this.objects = objects;
	}

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public List<FObject> getObjects() {
		return objects;
	}
	
	
	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public void setHeight(int height) {
		this.height = height;
	}
	

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public void setTiles(List<FTile> tiles) {
		this.tiles = tiles;
	}

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public List<FEntity> getEntities() {
		return entities;
	}

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public void setEntities(List<FEntity> entities) {
		this.entities = entities;
	}

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public boolean getLoaded() {
		return Loaded;
	}

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public void setLoaded(boolean loaded) {
		Loaded = loaded;
	}


	
}
