package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.Graphic.FCamera;
import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.Graphic.Sprite;
import ie.flax.flaxengine.client.events.CameraUpdateEvent;
import ie.flax.flaxengine.client.events.CameraUpdateEventHandler;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.ImageSelectionEvent;
import ie.flax.flaxengine.client.events.ImageSelectionEvent.Identifier;
import ie.flax.flaxengine.client.events.MapUpdateEvent;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;
import ie.flax.flaxengine.client.events.onFileLoadedEventHandler;
import ie.flax.flaxengine.client.gameobjects.Player;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Window;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;
import com.kfuntak.gwt.json.serialization.client.Serializer;

/**
 * FMap is an automatic class, which given a filePath to a JSON map file will 
 * construct itself by magic. FMap does all the work for you.
 * <br><br>
 * 
 * The map object is basically the environment, it defines the tiles, the
 * objects, the entity in the world. It is a very automated object in that it
 * takes 1 parameter for its constructor. The path to the xml file which
 * contains all the information about the map. Below is a template example of
 * the information.
 * <br><br>
 * The map file is the game information file really. It contains almost all of
 * the info about the different objects that will be created. The images for
 * each object are also loaded when this map file is read. The tiles are all
 * stored on one sheet and objects and entitys have their own sperate images due
 * to the fact they may have animations.
 * <br><br>
 * Thought if a developer would like to programmatically add in all the objects
 * and entitys they can do so. Though the map object requires the xml file for
 * at-lest the tiles of the map.
 * 
 * 
 * @author Ciaran McCann
 * 
 */
public class FMap implements JsonSerializable, onFileLoadedEventHandler, CameraUpdateEventHandler{

	private int width;
	private int height;
	
	private int tileSize;
	private String name;
	private boolean Loaded; 
	private Canvas drawingSpace;
	private ImageElement tileSheetImage;
	
	/**
	 * This holds the string which is used to reference the tileSheet image in the imageLibary
	 */
	private String tileSheet;	
	
	
	/**
	 * Tiles are stored in this list and have relative corrdinates. IE (1,0) (2,0) .... (3,3)
	 * The relative unit it TILES 
	 */
	private List<FTile> tiles = new ArrayList<FTile>();
	
	private List<FObject> objects = new ArrayList<FObject>();
	private List<FEntity> entities = new ArrayList<FEntity>();

	/**
	 * FMap object is constructed from a file by provding the path name. 
	 * @param mapPath - URL to map.json file
	 * @param drawingSpace - TODO maybe remove and just pass in in drawing loop.
	 */
	public FMap(String mapPath, Canvas drawingSpace) {		
		
		this.drawingSpace = drawingSpace;
		name = mapPath;
		
		EventBus.handlerManager.addHandler(CameraUpdateEvent.TYPE, this);
		EventBus.handlerManager.addHandler(onFileLoadedEvent.TYPE, this); //Register the obj for onFileLoaded events	
		FileHandle.readFileAsString(mapPath, this.toString());//Makes a request for the map file	
	}
	

	
	/**
	 * This calls all the draw methods of the entities in the FMap, the tiles and checks weather they are on-screen and
	 * if they are they are then drawn to the screen.
	 * @param canvas 
	 * @param cam2 
	 */
	public void draw(FCamera cam, Canvas drawingSpace) {	
		
		if (cam==null)  cam = FlaxEngine.camera; 
		if (drawingSpace==null) drawingSpace = this.drawingSpace;


		if(tileSheetImage != null)
		{		
			
			/**
			 * The below calucates and objects referencing is all done outside the loops to speed up the drawing
			 */			
			double camX = cam.getX();
			double camY = cam.getY();
			double camXWidth = camX+cam.getWidth();
			double camYHeight = camY+cam.getHeight();
			
			int camXRelative = (int) (cam.getX()/tileSize);
			int camYRelative = (int) (cam.getY()/tileSize);
			int camXAndWidth = (int) (camXRelative)+cam.getWidth()/tileSize;
			int camYAndHeight = (int) (camYRelative)+cam.getHeight()/tileSize;
		
			int camXRelativeCopy = camXRelative;
			int camYRelativeCopy = camYRelative;
			
			int camXRelativeCopyScaled = camXRelativeCopy*tileSize;
			int camYRelativeCopyScaled =  camYRelativeCopy*tileSize;
			
			Context2d ctx = drawingSpace.getContext2d();	
			
			

			
			
			// all in tiles - relative
			while(camYRelative < camYAndHeight)
			{
				// in number of tiles - relative
				while( camXRelative < camXAndWidth )
				{
					/**
					 * TODO optimize this some more later by inlining, tho its fine for the mo
					 */
					FTile t = tiles.get(camXRelative + (camYRelative *  width ));					
					t.draw(tileSheetImage, tileSize, ( camXRelative*tileSize -  camXRelativeCopyScaled ) , ( camYRelative*tileSize - camYRelativeCopyScaled ), ctx);					
					camXRelative++;
				}

				camXRelative = camXRelativeCopy; // rest the X to intial
				camYRelative++;
			}
			
	

			for(FObject temp : objects)
			{
				//check if the eneity can be seen on screen before drawing
				if(temp.getX() >= camX-tileSize && temp.getX() <= camXWidth &&temp.getY() >= camY-tileSize && temp.getY() <= camYHeight)
				temp.draw(drawingSpace);
			}
		
			for(FEntity temp : entities)
			{
				//check if the eneity can be seen on screen before drawing
				if(temp.getX() >= camX-temp.getWidth() && temp.getX() <= camXWidth &&temp.getY() >= camY-temp.getHeight() && temp.getY() <= camYHeight)
				temp.draw(drawingSpace);
			}		
			
			
			
			
		}
	}



	/**
	 * Gets the tile at the location which was clicked
	 * 
	 * @param xClick - absolute click values of the mouse
	 * @param yClick - absolute click values of the mouse
	 * @return
	 */
	public FTile getTile(int xClick, int yClick) 
	{		
		/**
		 * Both the camera and click values are absolute pixel values
		 * adding them togtheier and divding them by the tilesize and then dropping the decimal 
		 * will get you the tile x and y such as (2,2)
		 */
		int clickX = (int) ((xClick+FlaxEngine.camera.getX())/tileSize);
		int clickY = (int) ((yClick+FlaxEngine.camera.getY())/tileSize);

		return tiles.get( clickX + (clickY*width) );
	}
	
	
	
	/**
	 * If true this FMap object has finished loading its data
	 * @return
	 */
	public boolean getLoaded() {
		return Loaded;
	}
	
	/**
	 * Pass this method JSON and it gives you back an FMap object which you can
	 * then assign to your object via FMap myMap = JsonToFMap(String Json);
	 * 
	 * @param JSON
	 * @return
	 */
	public static FMap fromJson(String Json) {
		FMap temp = null;		
		try {
			Serializer serializer = (Serializer) GWT.create(Serializer.class);		
			 temp = (FMap) serializer.deSerialize(Json,"ie.flax.flaxengine.client.FMap");			
		} catch (Exception e) {
			Window.alert(e + "\n\n" + e.getCause() + "\n\n" + "Map data corrupt");		
		}	
		return temp;
	}

	/**
	 * Creates a JSON string from the current FMap object
	 * 
	 * @return String of JSON
	 */
	public static String toJson(FMap map) {
		Serializer serializer = (Serializer) GWT.create(Serializer.class);
		return serializer.serialize(map);
	}
	
	/**
	 * basiclly does the following operation on the object
	 * FMap x = new ("map.json);
	 * FMap y = x;  //For some reaosn doing this does not work
	 * <br><br>
	 * This method replace the current object with another
	 * @param newMapObj
	 */
	public void replaceMap(FMap newMapObj)
	{
		/**
		 * It would be nicer to go this =
		 * FMap.JsonToFMap(e.getDataLoadedFromFile()); though we can't do
		 * that. It would have to be outside the class which wouldn't work as
		 * well with the ID's etc.
		 */
		this.entities = newMapObj.entities;
		//this.tiles = newMapObj.tiles;
		this.objects = newMapObj.objects;
		this.tileSheet = newMapObj.tileSheet;
		this.tileSize = newMapObj.tileSize;
		this.width = newMapObj.width;
		this.height = newMapObj.height;
		
		int x  =0;
		int y =0;
		
		while ( y < height)
		{
			while( x < width)
			{
				tiles.add( new FTile( 21, Graphic.getSingleton().getImage(tileSheet), tileSize));
				x++;
			}
			
			x = 0;
			
			y++;
		}

		
		//this.addEntity(new FEntity(100, 100, 32, 32, new Sprite("http://flax.ie/test/tiles.png", 32, 32), "audio") );
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
			
		//Checks it was this object that requested the file		
		if(this.toString().equalsIgnoreCase(e.getId()))
		{

			/**
			 * Creates a temp FMap object from the JSON string which is stored
			 * in the event object which was pulled from the server
			 */
			final FMap temp = fromJson(e.getDataLoadedFromFile()); 
		
			
			
			//Loops though all objects from map
			for(FObject obj : objects)
			{
				//get the list of audio files assoiated with said object and loads them.
				//for(String audioPath : obj.getAudio())
				//{
				//	Audio.loadHtml(audioPath);
				//}
				
				//get the image file assoiated with said object and loads them.
				//Graphic.getSingleton().loadImage(obj.getSprite());
			}
			
			
			//Loops though all entites from map
			for(FEntity obj : entities)
			{
				//get the list of audio files assoiated with said object and loads them.
				//for(String audioPath : obj.getAudio())
				//{
				//	Audio.loadHtml(audioPath);
				//}\\
				
				//get the image file assoiated with said object and loads them.
				
				
				//Graphic.getSingleton().loadImage(obj.getSprite());			
			}
				
			
			Graphic.getSingleton().loadImage("http://www.allacrost.org/media/art/sprites_map_claudius.png");
			
			/**
			 * Loads the tilesheet of the map, waits for the onLoad call back and fires a ImageSelection 
			 * event which will load the tilesheet into the tileMenuView
			 */
			Graphic.getSingleton().loadImage(temp.getTileSheet()).addLoadHanderl( new LoadHandler() {
				
				@Override
				public void onLoad(LoadEvent event) {
					
					/**
					 * Only load in the new map data once all the images have loaded for the map.
					 * So that the calucations for which texture to pick from an image can be done at load and not during frame 
					 */
					replaceMap(temp); //op code : this = temp;
					EventBus.handlerManager.fireEvent(new ImageSelectionEvent(tileSheet, Identifier.TILE_SHEET));
					addEntity(new Player());	

				}
			});	
			
			
			FLog.info("An FMap object of name [" + this.name + "]; was constructed from a file sucessfully");
			
			Loaded = true;
		}
	}
	
	
	/**
	 * This adds the given FEntity object or objects that are derived from FEntity
	 * to the map object.
	 * @param entity
	 */
	public void addEntity(FEntity entity)
	{
		if(entity.getX() >= 0 && entity.getX() <= width*tileSize+tileSize&&entity.getY() >= 0&&entity.getY() <= height*tileSize-tileSize)
		{
			entities.add(entity); 
			FLog.trace(entity + " was created and added to " + this);
		}		
	}
	

	/**
	 * This adds the given FObject object or objects that are derived from FObject
	 * to the map object.
	 * @param FObject 
	 */
	public void addObjects(FObject object)
	{
		objects.add(object);
	}
	
	/**
	 * This allows the user to select and modify the enitiy
	 * @param id
	 * @return
	 */
	public FEntity getEntity(int id)
	{
		return entities.get(id);
	}
	
	
	/**
	 * This allows the user to select and modify the enitiy
	 * @param id
	 * @return
	 */
	public FEntity getEntity(int x, int y)
	{
		return entities.get(0); //TODO: fix this
	}
	
	
	/**
	 * @return the drawingSpace
	 */
	public final Canvas getDrawingSpace() {
		return drawingSpace;
	}
	

	/**
	 * DO NOT USE THIS Constructor -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public FMap() {
		
	}
		
	/**
	 * Gets the name of the map file which this map object was created from
	 * @return String name of map
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * Sets the tileSize of the map 
	 * @param tileSize
	 */
	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}


	/**
	 * Gets the tileSize used by the current map
	 * @return int tilesize
	 */
	public int getTileSize() {
		return tileSize;
	}

	
	/**
	 * Sets the tileSheet of the engine and also sets the FMaps tilesheet ImageEelement to that message
	 * @param tileSheet
	 */
	public void setTileSheet(String tileSheet) {
		this.tileSheet = tileSheet;
		this.tileSheetImage = Graphic.getSingleton().getImage(tileSheet);
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
	
	//TODO - CARL comment
	@Override
	public void onCameraUpdate(CameraUpdateEvent e) {
		drawingSpace.getContext2d().fillRect(0, 0, drawingSpace.getCoordinateSpaceWidth(), drawingSpace.getCoordinateSpaceHeight()); 
		draw(null, null);
	}
	
	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public List<FTile> getTiles() {
		return tiles;
	}
	
	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public void setObjects(List<FObject> objects) {
		this.objects = objects;
	}

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public List<FObject> getObjects() {
		return objects;
	}
	
	
	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public void setWidth(int width) {
		this.width = width;
		FlaxEngine.camera.setMapWidth(width);
	}

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public void setHeight(int height) {
		this.height = height;
		FlaxEngine.camera.setMapHeight(height);
	}
	

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public void setTiles(List<FTile> tiles) {
		this.tiles = tiles;
	}

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public List<FEntity> getEntities() {
		return entities;
	}

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public void setEntities(List<FEntity> entities) {
		this.entities = entities;
	}


	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public void setLoaded(boolean loaded) {
		Loaded = loaded;
	}
	

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public ImageElement getTileSheetImage() {
		return tileSheetImage;
	}



	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public void setTileSheetImage(ImageElement tileSheetImage) {
		this.tileSheetImage = tileSheetImage;
	}


}
