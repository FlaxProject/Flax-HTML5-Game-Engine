package ie.flax.flaxengine.client;

import java.util.ArrayList;
import java.util.List;

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
public class FMap {

	private int width;
	private int height;
	private static int tileSize;
	private String tileSheet;
	private FTile[][] tiles;
	private List<FObject> objects = new ArrayList<FObject>();

	// private List<FEntity> entities = new ArrayList<FEntity>();

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTileSheet() {
		return tileSheet;
	}

}
