package ie.flax.flaxengine.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;


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
public abstract class FlaxEngine extends FocusPanel {
	
	private static final String powerBy = "Powered By Flax Web Game Engine";
	private List<FMap> maps = new ArrayList<FMap>();
	private int currentMap;
	private boolean playing;
	public static Settings settings;
	private boolean engineStatus;
	
	
	/**
	 * Gets the current map
	 * @return
	 */
	public FMap getCurrentMap()
	{
		return maps.get(currentMap);
	}
	
	
	
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
					maps.get(0).draw();
				
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
		gameTimer.scheduleRepeating(41);
								
	}
	
	
	/**
	 * This constructor initlizes the flax engine and setup default settings. Takes in an array of strings which contain the address to map files. 
	 * @param mapPaths - array of address to maps. if the insertId is not found it will dump the canvas in the body tag
	 * @param insertId - id of element of which to insert the canvas
	 */
	public FlaxEngine(String[] mapPaths, String insertId)
	{
		setupHtmlPanel(insertId,600,300);	
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
		setupHtmlPanel(insertId,600,300);	
		maps.add(new FMap(mapPaths));//Loads all the maps
	}
	
	
	/**
	 * This constructor initlizes the flax engine and setup default settings. Takes in an array of strings which contain the address to map files. 
	 * @param mapPaths - array of address to maps. if the insertId is not found it will dump the canvas in the body tag
	 * @param insertId - id of element of which to insert the canvas
	 * @param CSSclass 
	 */
	public FlaxEngine(String mapPaths, String insertId, int width, int height)
	{		
		setupHtmlPanel(insertId,width,height);	
		maps.add(new FMap(mapPaths));//Loads all the maps
	}
	
	
	/**
	 * This constructor initlizes the flax engine and setup default settings. Takes in an array of strings which contain the address to map files. 
	 * @param mapPaths - array of address to maps. if the insertId is not found it will dump the canvas in the body tag
	 * @param insertId - id of element of which to insert the canvas
	 * @param settingsFile
	 */
	/*public FlaxEngine(String[] mapPaths, String insertId, String settingsFile)
	{
		Graphic.init(insertId);// setup the canvas
		
		for(String mapPath : mapPaths)
		{
			maps.add(new FMap(mapPath));//Loads all the maps
		}
		
		settings = new Settings(settingsFile);//loads the settings from file
	}
	*/
	
	
	/**
	 * This constructor initlizes the flax engine and setup default settings. Takes in an array of strings which contain the address to map files. 
	 * @param mapPaths - array of address to maps. if the insertId is not found it will dump the canvas in the body tag
	 * @param insertId - id of element of which to insert the canvas
	 * @param settings - HashMap<String, String>
	 */
	/*public FlaxEngine(String[] mapPaths, String insertId, String imgDirPath, String mapDirPath, Boolean collision)
	{
		Graphic.init(insertId);// setup the canvas
		
		for(String mapPath : mapPaths)
		{
			maps.add(new FMap(mapPath));//Loads all the maps
		}
		
		settings = new Settings(imgDirPath, mapDirPath, true, "984756", mapDirPath);
	}
	*/
	
	
	/**
	 * This method should be refractored as some stage as it talks about canvas and ignores the abstraction layer
	 * which atm is fine as a secound graphic layer will not be implemented any time soon. 
	 *<br><br> This method inserts the canvas tag inside the focus panel which catchs events.
	 * @param insertId
	 * @param width
	 * @param height
	 */
	private void setupHtmlPanel(String insertId, int width, int height)
	{
		this.setSize(width+"px", height+"px");
		HTMLPanel panel = new HTMLPanel("<canvas id=\"FlaxEngineCanvas\" style=\"background:red;\" width= " + width +" height=" + height + " >Your browser is way out of date man, get a good one like Chrome</canvas>");
		panel.setSize(width+"px", height+"px");
		
		this.add(panel);
		RootPanel.get(insertId).add(this);
		
		Graphic.init(insertId,width,height);// setup the canvas	
	}
	
	/**
	 * Checks are all the engine componets are loaded and the data in them got from the server
	 * @return
	 */
	private boolean isEngineReady()
	{
	
		/**
		 * Understand the below if() statment. The frist condtion which is check is the status of the engine
		 * which is by default to false. As this method is called in the main loop
		 * we don't want all the checks been excuted so we store the status of true once we get it
		 * In the secound condtional statement this happens -> If first checks is the first map in the map
		 * list null and then the secound check it indexes that object and asks for a memeber Loaded.
		 * Which if true will then move onto checking if the graphics componet is ready
		 * 
		 * This only checks if the first map is loaded, this is going on the idea that all other maps will
		 * load by the time they are needed
		 * 
		 * 
		 */
		if(		(engineStatus == true)||
				(maps.get(0) != null && maps.get(0).getLoaded() && Graphic.isComponentReady() && Audio.isComponentReady())//TODO add check for audio loading
			)
		{			
			engineStatus = true;
		}
		
		return engineStatus;
	}

	
	

}
