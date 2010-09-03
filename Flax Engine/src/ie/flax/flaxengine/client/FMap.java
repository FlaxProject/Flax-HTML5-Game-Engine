package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;
import ie.flax.flaxengine.client.events.onFileLoadedEventHandler;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;
import com.kfuntak.gwt.json.serialization.client.Serializer;

/**
 * The map object is basically the environment, it defines the tiles, the
 * objects, the entity in the world. It is a very automated object in that it
 * takes 1 parameter for its constructor. The path to the xml file which
 * contains all the information about the map. Below is a template example of
 * the information.
 * 
 * The map file is the game information file really. It contains almost all of
 * the info about the different objects that will be created. The images for
 * each object are also loaded when this map file is read. The tiles are all
 * stored on one sheet and objects and entitys have their own sperate images due
 * to the fact they may have animations.
 * 
 * Thought if a developer would like to programmatically add in all the objects
 * and entitys they can do so. Though the map object requires the xml file for
 * at-lest the tiles of the map.
 * 
 * @author Ciarán McCann
 * 
 */
public class FMap implements JsonSerializable, onFileLoadedEventHandler {

	private int width;
	private int height;
	private int tileSize;
	private String name;

	private String tileSheet;
	private List<FTile> tiles;
	private List<FObject> objects = new ArrayList<FObject>();
	private List<FEntity> entities = new ArrayList<FEntity>();

	
	public FMap(String mapPath) {
		
		FileHandle.readFileAsString(mapPath, mapPath);
		
		// TODO code to be removed when JSON serialisation works.
		
		name = mapPath;
		/*this.width = this.height = 1000;
		this.tileSize = 32;
		this.tileSheet = "c";
		tiles = new ArrayList<FTile>(width * height);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles.add(new FTile(x * this.tileSize, y * this.tileSize, 32,32, 1));
				objects.add(new FObject(23, 23, 23, 23));
			}
		}*/
		
		EventBus.handlerManager.addHandler(onFileLoadedEvent.TYPE, this);
	}
	

	public FMap() {
		
	}


	/**
	 * Pass this method JSON and it gives you back an FMap object which you can
	 * then assign to your object via FMap myMap = JsonToFMap(String Json);
	 * 
	 * @param JSON
	 * @return
	 */
	public static FMap JsonToFMap(String Json) {
		Serializer serializer = (Serializer) GWT.create(Serializer.class);
		return (FMap) serializer.deSerialize(Json,
				"ie.flax.flaxengine.client.FMap");
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
	
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public List<FTile> getTiles() {
		return tiles;
	}

	public void setTiles(List<FTile> tiles) {
		this.tiles = tiles;
	}

	public List<FEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<FEntity> entities) {
		this.entities = entities;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

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
	}

	public void setObjects(List<FObject> objects) {
		this.objects = objects;
	}

	public List<FObject> getObjects() {
		return objects;
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
				
		//if(this class called for the file)
		if(e.getId() == this.name)
		{
			/**
			 * Creates a temp FMap object from the JSON string which is stored
			 * in the event object which was pulled from the server
			 */
			FMap temp = FMap.JsonToFMap(e.getDataLoadedFromFile()); 
						
			/**
			 * It would be nicer to go this =
			 * FMap.JsonToFMap(e.getDataLoadedFromFile()); though we can't do
			 * that. It would have to be outside the class which wouldnt work as
			 * well with the ID's etc.
			 */
			this.entities = temp.entities;
			this.tiles = temp.tiles;
			this.objects = temp.objects;
			this.tileSheet = temp.tileSheet;
			this.tileSize = temp.tileSize;
			this.width = temp.width;
		}
	}
}
