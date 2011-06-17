package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;
import ie.flax.flaxengine.client.events.onFileLoadedEventHandler;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Window;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;
import com.kfuntak.gwt.json.serialization.client.SerializationException;
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
 * @author Ciaran McCann
 * 
 */
public class FMap implements JsonSerializable, onFileLoadedEventHandler {

	private int width;
	private int height;
	private int tileSize;
	private String name;
	private boolean Loaded; 
	private Canvas drawingSpace;
	
	/**
	 * This holds the string which is used to reference the tileSheet image in the imageLibary
	 */
	private String tileSheet;	
	
	private List<FTile> tiles = new ArrayList<FTile>();
	private List<FObject> objects = new ArrayList<FObject>();
	private List<FEntity> entities = new ArrayList<FEntity>();

	/**
	 * FMap constructor takes in the map path and use the static service file
	 * handle to read the map file which is has the JSON of the map. The JSON
	 * string is then returned to the FMap class via an event due to
	 * asynchronous
	 * 
	 * @param mapPath
	 *            address to the map file to be loaded.
	 */
	@SuppressWarnings("deprecation")
	public FMap(String mapPath, Canvas drawingSpace) {		
		name = mapPath;
			tileSheet = "http://flax.ie/test/p.png";
		EventBus.handlerManager.addHandler(onFileLoadedEvent.TYPE, this); //Register the obj for onFileLoaded events
		FileHandle.readFileAsString(mapPath, this.toString());//Makes a request for the map file			
	}
	
	//TODO: fix this later, testing
	public FMap(String mapName, int tileSize, int unitWidth, int unitHeight)
	{
				
	}

	/**
	 * DO NOT USE THIS Constructor -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated	
	public FMap() {
		//this.addTile(new FTile(20, 20, true, 1));
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
	 * Sets the tileSheet of the engine
	 * @param tileSheet
	 */
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
	 * This is used to reference the imageLibary with the name of the tileSheet
	 * eg. imageLibary.get("myTileSheet")
	 * 
	 * @return tileSheet name
	 */
	public String getTileSheet() {
		return tileSheet;
	}

	/**
	 * This calls all the draw methods of the entities in the FMap, the tiles and checks weather they are on-screen and
	 * if they are they are then drawn to the screen.
	 */
	public void draw(Canvas canvas) {
		
		this.drawingSpace = canvas;
	
		/**
		 * The below calucates and objects referencing is all done outside the loops to speed up the drawing
		 */
		//Context2d canvasRef = Graphic.getSingleton().getCanvas("Flax").getContext2d();
		FCamera cam = FlaxEngine.camera;
		/**
		 * Maybe remove below line when game is been played, that line is only for the editor.It may increase in-game preforamnce 
		 */
		drawingSpace.getContext2d().fillRect(0, 0, cam.getWidth(), cam.getHeight()); 
		double camX = cam.getX();
		double camY = cam.getY();
		double camXWidth = camX+cam.getWidth();
		double camYHeight = camY+cam.getHeight();
		ImageElement tileSheetImage = Graphic.getSingleton().getImage(tileSheet);

		if(tileSheetImage != null)
		{
		
		for(FTile temp : tiles)
		{
			//check if the tile can be seen on screen before drawing
			if(temp.getX() >= camX-tileSize && temp.getX() <= camXWidth &&temp.getY() >= camY-tileSize && temp.getY() <= camYHeight)
			temp.draw(tileSheetImage, this.tileSize, temp.getX()-camX, temp.getY()-camY,drawingSpace.getContext2d());		
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
	 * This adds the given FEntity object or objects that are derived from FEntity
	 * to the map object.
	 * @param entity
	 */
	public void addEntity(FEntity entity)
	{
		//TODO: add a log to tell if an enetity was added and also load entity sprite
		if(entity.getX() >= 0&&entity.getX() <= width+tileSize&&entity.getY() >= 0&&entity.getY() <= height-tileSize)
		{
			entities.add(entity); 
			FLog.info("FEntity Object loaded into map");
		}
		
	}
	
	/**
	 * Adds the given tile to the map
	 * @param tile
	 */
	public void addTile(FTile tile)
	{
		if(tile.getX() >= 0&&tile.getX() <= width+tileSize&&tile.getY() >= 0&&tile.getY() <= height-tileSize)
		tiles.add(tile);
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
	 * Checks the tile from the tile array based on the given x and y 
	 * Currently only to be used for mouse clicks but could easly be changed
	 * @param x
	 * @param y
	 * @return
	 */
	public FTile getTile(int xClick, int yClick)
	{
		/**
		 * Both the camera and click values are absolute pixel values
		 * aadding them togtheier and divding them by the tilesize and then dropping the decimal 
		 * will get you the tile x and y such as (2,2)
		 */
		int clickX = (int) ((xClick+FlaxEngine.camera.getX())/tileSize);
		int clickY = (int) ((yClick+FlaxEngine.camera.getY())/tileSize);
		
		clickX *= tileSize;
		clickY *= tileSize;
		
		for(FTile obj : tiles)
		{
			if(obj.getX() == clickX && obj.getY() == clickY)
				return obj;		
			
		}
		return null;
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
	public FMap fromJson(String Json) {
		
		FMap temp = new FMap();
		try {
			
			
			Serializer serializer = (Serializer) GWT.create(Serializer.class);
		
			
			//if(serializer != null)
				//temp = (FMap) serializer.deSerialize(Json,"ie.flax.flaxengine.client.FMap");
			////else
			//	FLog.error("fromJSON - serializer was null");
			
			
		} catch (SerializationException e) {
			// TODO: handle exception
			FLog.error("fuckkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk   " + e.getCause() + "          " + e.getMessage());
	
		}
		
			//temp.addTile(new FTile(0, 0, true, 3));
		return temp;
	}

	/**
	 * Creates a JSON string from the current FMap object
	 * 
	 * @return String of JSON
	 */
	public String toJson() {
		Serializer serializer = (Serializer) GWT.create(Serializer.class);
		return serializer.serialize(this);
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
		this.tiles = newMapObj.tiles;
		this.objects = newMapObj.objects;
		this.tileSheet = newMapObj.tileSheet;
		this.tileSize = newMapObj.tileSize;
		this.width = newMapObj.width;
		this.height = newMapObj.height;
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
			FMap temp = fromJson(e.getDataLoadedFromFile()); 
						
			replaceMap(temp); //op code : this = temp;
			
			
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
				//}
				
				//get the image file assoiated with said object and loads them.
				
				
				//Graphic.getSingleton().loadImage(obj.getSprite());
				
			}
					
			//Graphic.getSingleton().loadImage( tileSheet);			
			FLog.info("An FMap object of name [" + this.name + "]; was constructed from a file sucessfully");
			
			Loaded = true;
		}
	}
	
	/**
	 * @return the drawingSpace
	 */
	public final Canvas getDrawingSpace() {
		return drawingSpace;
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


	
}
