package ie.flax.flaxengine.client;


import ie.flax.flaxengine.client.weave.Weave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Event;
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
public abstract class FlaxEngine {
	
	private FocusPanel eventPanel;
	private static final String powerBy = "Powered By Flax Web Game Engine";
	private List<FMap> maps = new ArrayList<FMap>();
	private int currentMap;
	private boolean playing;
	public static Settings settings;
	private boolean engineStatus;
	private int frameCount = 0;
	private int oldMilliseconds = 0;
	private Weave editor;
	

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
					fpsUpdate();
				}
			}
			else
			{
				Log.debug("Engine is not ready yet");
			}
		}
	};
		
	
	
	/**
	 * Returns the current map which can the be used to modify the information in the map
	 * @return
	 */
	public FMap getCurrentMap()
	{
		return maps.get(currentMap);
	}
	

	/**
	 * The currentMap by setting the index 
	 * @param setIndex
	 */
	public void setCurrentMap(int setIndex)
	{
		this.currentMap = setIndex; 
	}
	
	/**
	 * Sets the current map to the given name
	 * @param mapName
	 */
	public void setCurrentMap(String mapName)
	{
		int mapIndex = 0;
		
		for(FMap map : maps)
		{
			 if(map.getName() == mapName)
			 {
				 currentMap = mapIndex;
				 break;
			 }
				 
				mapIndex++;
		}
		
	}
	
	
	/**
	 * Gets a map with given ID
	 * @param mapID
	 * @return
	 */
	public FMap getMap(int mapID)
	{
		return maps.get(mapID);
	}
	
	
	/**
	 * Searchs though the list of maps and return the one with supplied name
	 * @param mapName
	 * @return
	 */
	public FMap getMap(String mapName)
	{
		for(FMap map : maps)
		{
			 if(map.getName() == mapName)
				 return map;
		}
		return null;
	}
	
	
	/**
	 * The run method starts the game loop
	 */
	public void run()
	{
		playing = true;	
		gameTimer.scheduleRepeating(41);
								
	}
	
	
	protected native int getMilliseconds() /*-{
		var currentTime = new Date();
		return currentTime.getMilliseconds();
	}-*/;


	/**
	 * This constructor initlizes the flax engine and setup default settings. Takes in an array of strings which contain the address to map files. 
	 * @param mapPaths - array of address to maps. if the insertId is not found it will dump the canvas in the body tag
	 * @param insertId - id of element of which to insert the canvas
	 */
	public FlaxEngine(String[] mapPaths, String insertId)
	{
		setupHtmlPanel(insertId,600,300);
		setupEventHandlers();
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
		setupEventHandlers();
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
		setupEventHandlers();
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
		eventPanel = new FocusPanel();
		eventPanel.setSize(width+"px", height+"px");
		HTMLPanel panel = new HTMLPanel("<canvas id=\"FlaxEngineCanvas\" style=\"background:red;\" width= " + width +" height=" + height + " >Your browser is way out of date man, get a good one like Chrome</canvas>");
		panel.setSize(width+"px", height+"px");
		
		eventPanel.add(panel);
		RootPanel.get(insertId).add(eventPanel);
		
		
		Graphic.init(insertId,width,height);// setup the canvas	
		editor = new Weave(insertId,800,600);
		editor.display();
						
	}
	
	
	/**
	 * Registers the canvas for event handling
	 */
	private void setupEventHandlers()
	{
		eventPanel.addKeyDownHandler( new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				
			event.preventDefault();				
			onKeyDownEvent(event);
				
			}
		});
		
		
		//TODO: register all types of events
	}
	
	
	
	
	/**
	 * Defines logic for what happens when a key down event happens. 
	 * @param event
	 */
	protected  void onKeyDownEvent(KeyDownEvent event) {
		
		
		if(event.isUpArrow())
		{
			this.getCurrentMap().getEntity(0).setY(getCurrentMap().getEntity(0).getY()-3);
		}
		
		if(event.isDownArrow())
		{
		getCurrentMap().getEntity(0).setY(getCurrentMap().getEntity(0).getY()+3);
		}
		
		if(event.isLeftArrow())
		{
		getCurrentMap().getEntity(0).setX(getCurrentMap().getEntity(0).getX()-3);
		}
		
		if(event.isRightArrow())
		{
		getCurrentMap().getEntity(0).setX(getCurrentMap().getEntity(0).getX()+3);
		}
		
		
	}

	
	
	/**
	 * Checks are all the engine componets are loaded and the data in them got from the server
	 * @return
	 */
	protected boolean isEngineReady()
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


	/**
	 * This updates the fps counter, and logs the frames every second or so.
	 * Known bugs: if a frame takes longer than a second (ie >=1001 milliseconds to draw, the FPS recorded is incorrect.
	 */
	private void fpsUpdate() {
		frameCount++;
		int currentMilliseconds = getMilliseconds();
		
		if (currentMilliseconds < oldMilliseconds){
			editor.updateUIelement("fpscount", ""+frameCount);
			frameCount = 0;
		}
		
		oldMilliseconds = currentMilliseconds;
	}

	
	

}
