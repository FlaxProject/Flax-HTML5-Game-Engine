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
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
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
			
			
			if (isEngineReady() && playing) {			
					maps.get(0).draw(camera, drawingSpace);
			}
			

			if (currentMilliseconds < oldMilliseconds) {
				editor.updateFps(frameCount);
				frameCount = 0;
			}

			oldMilliseconds = currentMilliseconds;

		}
	};

	/**
	 * This is the engine constructor - 
	 * @param mapPaths - URL to the map you wish to load with the engine
	 * @param insertId - the div which the engine/game will be inserted into
	 */
	public FlaxEngine(final String mapPaths, final String insertId) {

		this.insertId = insertId;
		
		/**
		 * Construct the splash screen
		 */
		splashScreen = new SplashScreen();
		RootPanel.get(insertId).add(splashScreen);		
		splashScreen.getElement().getStyle().setPosition(Position.ABSOLUTE);
				
		FLog.init();		
		initEngine(insertId);		
		maps.add(new FMap(mapPaths));// Loads all the maps
		
		/**
		 * Constructs the editor weave
		 */
		editor = new Weave(insertId, drawingSpace, editorOverLay, getCurrentMap());		
	}


	/**
	 * Registers the main game canvas for Events
	 */
	private void bind() {
		
		
		/**
		 * Just a quick key to start the rendering. As now by default the system
		 * doesn't render anything. As hosted mode gets killed by the rendering
		 */
		RootPanel.get().addDomHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				
				if(event.getCharCode() == 'z')
				{
					playing = !playing;
				}
				
			}
		}, KeyPressEvent.getType());

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
				event.stopPropagation();
			}
		});

		editorOverLay.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				event.preventDefault();
				
				if (editor.isRunning()) {
					editor.onMouseDown(event);
				}

			}
		});

		editorOverLay.addMouseUpHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				event.preventDefault();
				
				if (editor.isRunning()) {
					editor.onMouseUp(event);
				}

			}
		});

		editorOverLay.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				event.preventDefault();
			}
		});
	}

	
	/**
	 * This constructs the settings, constructs the game drawingcanvas and also the editoroverlay canvas
	 * @param insertId
	 */
	protected void initEngine(String insertId) {
		RootPanel.get().getAbsoluteLeft();
		if (settings == null) {
			settings = new Settings();
			settings.setContainer(insertId);
		}

		int width = settings.getWidth();
		int height = settings.getHeight();

		//basically check if it's fullscreen. Better than a use-once variable in Settings.
		if (width == Window.getClientWidth() && height == Window.getClientHeight() &&
				settings.getContainer().getAbsoluteLeft() == 0 && settings.getContainer().getAbsoluteTop() == 0){
			Window.enableScrolling(false);
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
		
		settings.getContainer().add(drawingSpace,0,0);// insert into doc		
		settings.getContainer().add(editorOverLay,0,0); 

		

		/**
		 * This is the boot strap loader for images in the engine. When an image
		 * is loaded by an FImage object, it inserts the image into this div
		 * which is display none and this triggers a DOM load image
		 */
		settings.getContainer().add(FImage.getBootStrapDiv());
		
		settings.getContainer().addHandler(new ResizeHandler() {
			
			@Override
			public void onResize(ResizeEvent event) {
				settings.setWidth(event.getWidth());
				settings.setHeight(event.getHeight());
			}
		}, ResizeEvent.getType());
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
			if ((settings.getHeight() > 768) || (settings.getWidth() > 1024)){
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

	/**
	 * Gets a reference to the editor
	 * @return
	 */
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
