package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.Graphic.FCamera;
import ie.flax.flaxengine.client.Graphic.FImage;
import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.Graphic.TimerCallback;
import ie.flax.flaxengine.client.gamewidgets.SplashScreen;
import ie.flax.flaxengine.client.weave.Weave;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This class is the engine itself, all the other class are the components and
 * will have objects or arrays of objects of these components inside this class.
 * This is the main class which the user will be interfacing with. <br>
 * <br>
 * This class is abstract so it most be extended by the developers main game
 * class
 * 
 * @author Ciaran McCann
 */
public abstract class FlaxEngine {

	private Canvas drawingSpace;
	private Canvas editorOverLay;

	private final List<FMap> maps = new ArrayList<FMap>();
	private int currentMap;
	private boolean playing;
	public static Settings settings;

	private boolean engineStatus;
	private int frameCount = 0;
	private double oldMilliseconds = 0;

	private final Weave editor;
	private final SplashScreen splashScreen;
	public static FCamera camera;
	
	private final String insertId;

	/**
	 * This is technically the game loop. It tells requestAnimationFrame to call
	 * it. Draw, update, etc should be in here.
	 */
	private final TimerCallback gameTimer = new TimerCallback() {

		@Override
		public void fire() {
			Graphic.getSingleton().requestAnimationFrame(this);

			frameCount++;
			double currentMilliseconds = getMilliseconds();
			
			
			if (isEngineReady()) {
				if (playing == true) {
					maps.get(0).draw(null, null);
				}
			}
			

			if (currentMilliseconds < oldMilliseconds) {
				FLog.updateFps(frameCount);
				frameCount = 0;
			}

			oldMilliseconds = currentMilliseconds;

		}
	};

	/**
	 * This constructor initlizes the flax engine and setup default settings.
	 * Takes in an array of strings which contain the address to map files.
	 * 
	 * @param mapPaths
	 *            - array of address to maps. if the insertId is not found it
	 *            will dump the canvas in the body tag
	 * @param insertId
	 *            - id of element of which to insert the canvas
	 * @param CSSclass
	 */
	public FlaxEngine(final String mapPaths, final String insertId) {
		
		this.insertId = insertId;
		splashScreen = new SplashScreen();
		RootPanel.get(insertId).add(splashScreen, 0, 0);
		
		FLog.init();
		initEngine(insertId);
		maps.add(new FMap(mapPaths, drawingSpace));// Loads all the maps
		editor = new Weave(insertId, drawingSpace, editorOverLay, getCurrentMap());
		
	}


	/**
	 * Registers the main game canvas for Events
	 */
	private void bind() {

		// TODO Very strange bug which will not allow the attaching of these
		// handlers
		// inside the weave object. The drawingSpace reference is passed in, but
		// crazy stuff happens like stackover flow. Leaving them here for the
		// meantime as its no bigy
		editorOverLay.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {

				if (editor.isRunning()) {
					editor.keyboardControls(event);
				}
			}
		});

		editorOverLay.addMouseMoveHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {

				if (editor.isRunning()) {
					editor.onMouseMove(event);
				}
			}
		});

		editorOverLay.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {

				if (editor.isRunning()) {
					editor.onMouseDown(event);
				}

			}
		});

		editorOverLay.addMouseUpHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				if (editor.isRunning()) {
					editor.onMouseUp(event);
				}

			}
		});

	}

	
	/**
	 * This method initialises many different components of the engine, events,
	 * rendering, weave
	 * 
	 * @param insertId
	 * @param width
	 * @param height
	 */
	protected void initEngine(String insertId) {

		if (settings == null) {
			settings = new Settings();
		}

		int width = settings.getWidth();
		int height = settings.getHeight();

		if (settings.getFullscreen() == true) {
			Window.enableScrolling(false);
		} else {
			// TODO CARL make this work from settings
			// width = RootPanel.get(insertId).getOffsetWidth();
			// height = RootPanel.get(insertId).getOffsetHeight();
		}

		drawingSpace = Canvas.createIfSupported();
		drawingSpace.setWidth(width + "px");
		drawingSpace.setHeight(height + "px");
		drawingSpace.setCoordinateSpaceWidth(width);
		drawingSpace.setCoordinateSpaceHeight(height);
		
		editorOverLay = Canvas.createIfSupported();
		editorOverLay.setWidth(width + "px");
		editorOverLay.setHeight(height + "px");
		editorOverLay.setCoordinateSpaceWidth(width);
		editorOverLay.setCoordinateSpaceHeight(height);
		
		if (drawingSpace == null) {
		      Window.alert("Sorry, your browser doesn't support the HTML5 Canvas element\n\n" + "Please upgrade to a new version of Chrome");
		   }

		camera = new FCamera(new FVector(0, 0), width, height);

		bind(); // sets the event handlers for canvas tag
		
		RootPanel.get(insertId).add(drawingSpace);// inser into doc		
		RootPanel.get(insertId).add(editorOverLay, 0,0); 
		

		/**
		 * This is the boot strap loader for images in the engine. When an image
		 * is loaded by an FImage object, it inserts the image into this div
		 * which is display none and this triggers a DOM load image
		 */
		RootPanel.get(insertId).add(FImage.getBootStrapDiv());
	}

	/**
	 * Checks are all the engine componets are loaded and the data in them got
	 * from the server
	 * 
	 * @return
	 */
	public boolean isEngineReady() {

		/**
		 * Understand the below if() statment. The first condtion which is
		 * checked is the status of the engine which is, by default, false. As
		 * this method is called in the main loop we don't want all the checks
		 * been excuted so we store the status of true once we get it In the
		 * secound condtional statement this happens -> If first checks is the
		 * first map in the map list null and then the secound check it indexes
		 * that object and asks for a member Loaded. Which if true will then
		 * move onto checking if the graphics componet is ready This only checks
		 * if the first map is loaded, this is going on the idea that all other
		 * maps will load by the time they are needed
		 */
		if (engineStatus == true)
				return true;
	
		if ((maps.get(0) != null) && maps.get(0).getLoaded()
		&& Graphic.getSingleton().isComponentReady() && Audio.isComponentReady()) {
			
			engineStatus = true;
			RootPanel.get(insertId).remove(splashScreen);
			/*
			 * Rudimentary check to see if the client's resolution is high enough to actually use weave
			 * only for debugging purposes (mobile etc) so remove this when there's a better way.
			 */
			if ((Window.getClientHeight() > 768) || (Window.getClientWidth() > 1024)){
				editor.toggle();
			}
		}

		return engineStatus;
	}
	

	/**
	 * Returns the current map which can the be used to modify the information
	 * in the map
	 * 
	 * @return
	 */
	public FMap getCurrentMap() {
		return maps.get(currentMap);
	}

	public Weave getEditor() {
		return editor;
	}


	/**
	 * Gets a map with given ID
	 * 
	 * @param mapID
	 * @return
	 */
	public FMap getMap(int mapID) {
		return maps.get(mapID);
	}

	/**
	 * Searchs though the list of maps and return the one with supplied name
	 * 
	 * @param mapName
	 * @return
	 */
	public FMap getMap(String mapName) {
		for (FMap map : maps) {
			if (map.getName() == mapName)
				return map;
		}
		return null;
	}

	/**
	 * The run method starts the game loop
	 */
	public void run() {
		playing = true;
		Graphic.getSingleton().requestAnimationFrame(gameTimer);
	}

	/**
	 * The currentMap by setting the index
	 * 
	 * @param setIndex
	 */
	public void setCurrentMap(int setIndex) {
		currentMap = setIndex;
	}

	/**
	 * Sets the current map to the given name
	 * 
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
	 * For FPS counter
	 * 
	 * @return
	 */
	protected native double getMilliseconds() /*-{
		var currentTime = new Date();
		return currentTime.getMilliseconds();
	}-*/;


}
