package ie.flax.flaxengine.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
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
public class FMap implements JsonSerializable{

	private int width;
	private int height;
	private final int tileSize;
	private String tileSheet;
	private List< List<FTile> > tiles = new ArrayList<FTile ArrayList<FTile>>(); //TODO working on readying for serialization
	private List<FObject> objects = new ArrayList<FObject>();
	// private List<FEntity> entities = new ArrayList<FEntity>();
	
	public FMap()
	{
		//TODO code to be removed when JSON serialisation works.
		this.width = this.height = 100;
		this.tileSize = 32;
		this.tileSheet = "c";
		tiles = new FTile[width][height];
		
		  for (int y = 0; y < height; y++)
	         {
	             for (int x = 0; x < width; x++)
	             {                
	                tiles[x][y] =  new FTile(x*this.tileSize, y*this.tileSize, 32, 32, 1);
	             }
	         }
	}
	
	
	/**
	 * Pass this method JSON and it gives you back an FMap object which you can then assign to your object via FMap myMap = JsonToFMap(String Json);
	 * @param JSON
	 * @return
	 */
	public static FMap JsonToFMap(String Json)
	{
		Serializer serializer = (Serializer)GWT.create( Serializer.class );		
		return (FMap)serializer.deSerialize(Json, "ie.flax.flaxengine.client.FMap");	
	}
	
	/**
	 * Creates a JSON string from the current FMap object
	 * @return String of JSON
	 */
	public String FMapToJson()
	{
		Serializer serializer = (Serializer)GWT.create( Serializer.class );		
		return serializer.serialize(this);
	}
		
	
	

	public FTile[][] getTiles() {
		return tiles;
	}


	public void setTiles(FTile[][] tiles) {
		this.tiles = tiles;
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
	 * This is used to reference the imageLibary with the name of the tileSheet eg. imageLibary.get("myTileSheet")
	 * @return tileSheet name 
	 */
	public String getTileSheet() {
		return tileSheet;
	}
	

	/**
	 * Draws the map
	 */
	 public void draw()
     {
		 //TODO implement camera scrolling in the map drawing
         for (int y = 0; y < height; y++)
         {
             for (int x = 0; x < width; x++)
             {                
                Graphic.drawTile(tileSheet, tiles[x][y].getTexture(), this.tileSize, tiles[x][y].getX(), tiles[x][y].getY());
             }
         }
     }


	public void setObjects(List<FObject> objects) {
		this.objects = objects;
	}


	public List<FObject> getObjects() {
		return objects;
	}
}
