package ie.flax.flaxengine.client;


import ie.flax.flaxengine.client.Graphic.FCamera;
import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.weave.Weave;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
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
	
	private Canvas drawingSpace;
	
	
	private static final String powerBy = "Powered By Flax Web Game Engine";
	private List<FMap> maps = new ArrayList<FMap>();
	private int currentMap;
	private boolean playing;
	public static Settings settings; //TODO CARL - Convert to singleton
	
	private boolean engineStatus;
	private int frameCount = 0;
	private int oldMilliseconds = 0;
	
	private Weave editor;
	public static FCamera camera;

	/**
	 * This timer implements the game loop. The timer loops every 500
	 * millsecounds It checks is the engineReady and then if the game is been
	 * played and then
	 */
	private Timer gameTimer = new Timer() {

		@Override
		public void run() {

			if (isEngineReady()) {
				if (playing == true) {
					// TODO Game Loop
					// Log.info("Game Loop is looping");

					//d
					maps.get(0).draw(drawingSpace); //FIXME very very weird bug - some how at some stage the map canvas member gets set to null, so I did this as a quick fix, look into it when you have time.
					if (editor.isRunning()) {
						editor.drawGrid();
					}
					fpsUpdate();

				}
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
	public void setCurrentMap(String mapName) {
		int mapIndex = 0;

		for (FMap map : maps) {
			if (map.getName() == mapName) {
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
	
	/**
	 * For FPS counter
	 * @return
	 */
	protected native int getMilliseconds() /*-{
		var currentTime = new Date();
		return currentTime.getMilliseconds();
	}-*/;
	
	
	/**
	 * This constructor initlizes the flax engine and setup default settings. Takes in an array of strings which contain the address to map files. 
	 * @param mapPaths - array of address to maps. if the insertId is not found it will dump the canvas in the body tag
	 * @param insertId - id of element of which to insert the canvas
	 * @param CSSclass 
	 */
	public FlaxEngine(String mapPaths, String insertId, int width, int height)
	{	
		Graphic.getSingleton().loadImage("http://flax.ie/test/p.png");					
		initEngine(insertId,width,height);	
		editor = new Weave(insertId);
				
		maps.add(new FMap(mapPaths,drawingSpace));//Loads all the maps
	
	}
	
	/**
	 * This method initialises many different components of the engine, events,
	 * rendering, weave
	 * 
	 * @param insertId
	 * @param width
	 * @param height
	 */
	protected void initEngine(String insertId, int width, int height) {
		if (settings == null) {
			settings = new Settings();
		}

		if (settings.getFullscreenOn() == true) {
			width = Window.getClientWidth();
			height = Window.getClientHeight();
			Window.enableScrolling(false);
		}

		drawingSpace = Canvas.createIfSupported();//(Graphic.getSingleton().createCanvas("Flax",width,height, width+ "px", height + "px"));
		camera = new FCamera(new FVector(0, 0), width, height);

	
		bindEvents(); // sets the event handlers for canvas tag
		RootPanel.get(insertId).add(drawingSpace);//inser into doc
	}

	
	/**
	 * Registers the main game canvas for Events
	 */
	private void bindEvents() {

		drawingSpace.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {


				if (editor.isRunning()) {
					editor.keyboardControls(event);
				}
			}
		});

		drawingSpace.addMouseMoveHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {

				if (editor.isRunning()) {
					editor.onMouseMove(event);
				}
			}
		});

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
				(maps.get(0) != null && maps.get(0).getLoaded() && Graphic.getSingleton().isComponentReady() && Audio.isComponentReady())//TODO add check for audio loading
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
			//editor.updateElement(WeaveUiManager.FPS_COUNTER, "FPS: "+frameCount);
			frameCount = 0;
		}
		
		oldMilliseconds = currentMilliseconds;
	}

	
	

}
