package ie.flax.flaxengine.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;


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
	
	private static final String powerBy = "Powered By Flax Web Game Engine";
	private List<FMap> maps = new ArrayList<FMap>();
	private int currentMap;
	private boolean playing;
	
	/**
	 * This timer implements the game loop. The timer loops every 500 millsecounds
	 * It checks is the engineReady and then if the game is been played and then 
	 */
	private Timer gameTimer = new Timer()
	{

		@Override
		public void run() {
							
			if(isEngineReady())
			{
			
				if(playing == true)
				{		
					//TODO Game Loop
					//Log.info("Game Loop is looping");					
				}
			}
		}
			
	};
		
	
	
	/**
	 * The run method starts the game loop
	 */
	public void run()
	{
		playing = true;	
		gameTimer.scheduleRepeating(500);
								
	}
	
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
	 */
	public FlaxEngine(String mapPaths, String insertId)
	{
		Graphic.init(insertId);// setup the canvas		
		maps.add(new FMap(mapPaths));//Loads all the maps
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
	 * Checks are all the engine componets are loaded and the data in them got from the server
	 * @return
	 */
	private boolean isEngineReady()
	{
		boolean result = false;
		
		if(Graphic.isComponentReady())//TODO add check for FMaps loaded and also audio
		{
			result = true;
		}
		
		return result;
	}

	

}
