package ie.flax.flaxengine.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;

/**
 * This class is the engine itself, all the other class are the components and
 * will have objects or arrays of objects of these components inside this class.
 * This is the main class which the user will be interfacing with.
 * <br><br>
 * This class is abstract so it most be extended by the developers main game class
 * 
 * @author Ciarán McCann
 * 
 */
public abstract class FlaxEngine {
	
	private List<FMap> maps = new ArrayList<FMap>();
	private int currentMap;
	
	/**
	 * This constructor initlizes the flax engine and setup default settings. Takes in an array of strings which contain the address to map files. 
	 * @param mapPaths - array of address to maps. if the insertId is not found it will dump the canvas in the body tag
	 * @param insertId - id of element of which to insert the canvas
	 */
	public FlaxEngine(String[] mapPaths, String insertId)
	{
		Graphic.init(insertId);// setup the canvas
		
		for(String mapPath : mapPaths)
		{
			maps.add(new FMap(mapPath));//Loads all the maps
		}
	}
	
	
	/**
	 * This constructor initlizes the flax engine and setup default settings. Takes in an array of strings which contain the address to map files. 
	 * @param mapPaths - array of address to maps. if the insertId is not found it will dump the canvas in the body tag
	 * @param insertId - id of element of which to insert the canvas
	 * @param settingsFile
	 */
	public FlaxEngine(String[] mapPaths, String insertId, String settingsFile)
	{
		Graphic.init(insertId);// setup the canvas
		
		for(String mapPath : mapPaths)
		{
			maps.add(new FMap(mapPath));//Loads all the maps
		}
		
		//TODO read the settings file and populate the varibles
	}
	
	
	/**
	 * This constructor initlizes the flax engine and setup default settings. Takes in an array of strings which contain the address to map files. 
	 * @param mapPaths - array of address to maps. if the insertId is not found it will dump the canvas in the body tag
	 * @param insertId - id of element of which to insert the canvas
	 * @param settings - HashMap<String, String>
	 */
	public FlaxEngine(String[] mapPaths, String insertId, HashMap<String, String> settings)
	{
		Graphic.init(insertId);// setup the canvas
		
		for(String mapPath : mapPaths)
		{
			maps.add(new FMap(mapPath));//Loads all the maps
		}
		
		//TODO read the settings from the HasMap and populate the members
	}

	/**
	 * Game Loop
	 */
	public void run()
	{
		
	}

}
