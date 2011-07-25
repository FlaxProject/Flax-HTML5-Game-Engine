package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.Graphic.FCamera;
import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.ImageSelectionEvent;
import ie.flax.flaxengine.client.events.ImageSelectionEvent.Identifier;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;
import ie.flax.flaxengine.client.events.onFileLoadedEventHandler;
import ie.flax.flaxengine.client.exception.MapDataCorrupt;

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
public class FMap implements JsonSerializable, onFileLoadedEventHandler{

	private int width;
	private int height;
	
	private int tileSize;
	private String name;
	private transient boolean Loaded; 
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
	 */
	public FMap(final String mapPath) {		
		
		name = mapPath;
		EventBus.handlerManager.addHandler(onFileLoadedEvent.TYPE, this); //Register the obj for onFileLoaded events	
		FileHandle.readFileAsString(mapPath, this.toString());//Makes a request for the map file	
	}
	

	
	/**
	 * This calls all the draw methods of the entities in the FMap, the tiles and checks weather they are on-screen and
	 * if they are they are then drawn to the screen.
	 * @param canvas 
	 * @param cam2 
	 */
	public void draw(
			final FCamera cam, 
			final Canvas drawingSpace, 						
			final double deltaTime		//Ok this shouldn't be here, need to have a thing about how going to do update and drawing.
										//No piont looping though all the elements twice for update and draw, so why not at same time, but anyway need to sleep on it
			) 
	{	

		/**
			 * The below calucates and objects referencing is all done outside the loops to speed up the drawing
			 * TODO Ciaran - at some piont slim down these varibles. Wont save much but could be done some time
			 */			
			final double camX = cam.getX();
			final double camY = cam.getY();
			final double camXWidth = camX+cam.getWidth();
			final double camYHeight = camY+cam.getHeight();
			
			int camXRelative = (int) (camX/tileSize);//plus one gives a border tile
			int camYRelative = (int) (camY/tileSize);
			final int camXAndWidth = (int) ( (camXRelative)+cam.getWidth()/tileSize ) + 1;
			final int camYAndHeight = (int) ( (camYRelative)+cam.getHeight()/tileSize ) + 1;
		
			final int camXRelativeCopy = camXRelative;
			final int camYRelativeCopy = camYRelative;
			
			final int camXRelativeCopyScaled = camXRelativeCopy*tileSize;
			final int camYRelativeCopyScaled =  camYRelativeCopy*tileSize;
			
			final Context2d ctx = drawingSpace.getContext2d();	
			FTile t  = null;
		
			/**
			 * currentYValue varibles is here to save on a multication and a subtraction every row item ( x corrdinate or coloum). 
			 */
			int currentYValue = 0; 
			
			/**
			 * Again like the currentYValue this varible exists to remove a multication  for every row item
			 */
			int currentYindexValue = 0;
				
			ctx.save();
			ctx.translate(cam.getInterpolation().x, cam.getInterpolation().y);
			
			// all in tiles - relative
			while(camYRelative <= camYAndHeight)
			{
				
				/**
				 * -1 to allow for the panning, read large comment below
				 */
				currentYValue = ( (camYRelative-1)*tileSize - camYRelativeCopyScaled ); // get next Y value
			
				currentYindexValue = (camYRelative) *  width; // get the next Y index value
				
				// in number of tiles - relatived
				while( camXRelative <= camXAndWidth)
				{

					t = tiles.get(camXRelative + currentYindexValue );		
					
					/**
					 * below is the call for the image to be drawn, note the -1 on the camXRelative, this is to facilate the panning.
					 * Basically it makes the map render with -1 positon and thus there will always a border tile colum, so when the camera is panning
					 * you don't notice anything wiered. 
					 * 
					 * I'm sure there is a better way to do this, tho it will do for the mo
					 * 
					 */
					ctx.drawImage(tileSheetImage, t.getTextureX(), t.getTextureY(), tileSize, tileSize, ( (camXRelative-1)*tileSize -  camXRelativeCopyScaled ) , currentYValue, tileSize, tileSize); 
					
					camXRelative++;
				}

				camXRelative = camXRelativeCopy; // rest the X to intial
				camYRelative++;
 
			}
			
			ctx.restore();
			
			for(FObject temp : objects)
			{
				//check if the eneity can be seen on screen before drawing
				if(temp.getX() >= camX-tileSize && temp.getX() <= camXWidth &&temp.getY() >= camY-tileSize && temp.getY() <= camYHeight)
				{
					temp.update(deltaTime);
					temp.draw(drawingSpace);
				}
			}
		
			for(FEntity temp : entities)
			{
				//check if the eneity can be seen on screen before drawing
				if(temp.getX() >= camX-temp.getWidth() && temp.getX() <= camXWidth &&temp.getY() >= camY-temp.getHeight() && temp.getY() <= camYHeight)
				{
					
						temp.update(deltaTime);
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
	public final FTile getTile(int xClick, int yClick) 
	{		
		/**
		 * Both the camera and click values are absolute pixel values
		 * adding them togtheier and divding them by the tilesize and then dropping the decimal 
		 * will get you the tile x and y such as (2,2)
		 */
		int clickX = (int) (xClick/tileSize);
		int clickY = (int) (yClick/tileSize);
		
		clickX += (int) FlaxEngine.camera.getX()/tileSize;
		clickY += (int) +FlaxEngine.camera.getY()/tileSize;

		
		/**
		 * Read the comment in the draw method, you will then understand why there is a +1 here
		 */
		int index = (clickX+1) + ((clickY+1)*width);
		
		if(index < 0)
			index = 0;
		
		return tiles.get( index );
	}
	
	
	
	/**
	 * If true this FMap object has finished loading its data
	 * @return
	 */
	public final boolean getLoaded() {
		return Loaded;
	}
	
	/**
	 * Pass this method JSON and it gives you back an FMap object which you can
	 * then assign to your object via FMap myMap = JsonToFMap(String Json);
	 * 
	 * @param JSON
	 * @return
	 * @throws MapDataCorrupt - if the JSON map string passed in has an error in it this expection will be throw
	 */
	public static final FMap fromJson(String Json) throws MapDataCorrupt {
		
		/**
		 * TODO : http://code.google.com/p/gwt-lzma/
		 */
		
		FMap temp = null;		
		try {
			Serializer serializer = (Serializer) GWT.create(Serializer.class);		
			 temp = (FMap) serializer.deSerialize(Json,"ie.flax.flaxengine.client.FMap");			
		} catch (Exception e) {
			
			throw new MapDataCorrupt();
		}	
		return temp;
	}

	/**
	 * Creates a JSON string from the current FMap object
	 * 
	 * @return String of JSON
	 */
	public static final String toJson(FMap map) {
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
		this.tiles = newMapObj.tiles;
		this.objects = newMapObj.objects;
		this.tileSheet = newMapObj.tileSheet;
		this.tileSize = newMapObj.tileSize;
		this.width = newMapObj.width;
		this.height = newMapObj.height;
		
		/**
		 * Code to generate a map, very handy
		 */
//		int x  =0;
//		int y =0;
//		
//		while ( y < height)
//		{
//			while( x < width)
//			{
//				tiles.add( new FTile( 21, Graphic.getSingleton().getImage(tileSheet), tileSize));
//				x++;
//			}
//			
//			x = 0;
//			
//			y++;
//		}

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
			 FMap temp = null;
			try {
				temp = fromJson(e.getDataLoadedFromFile());
			} catch (MapDataCorrupt e1) {
				// TODO Auto-generated catch block
				Window.alert(e1.getError());
			} 

			final FMap temp2 = temp;
			
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
					replaceMap(temp2); //op code : this = temp;
					EventBus.handlerManager.fireEvent(new ImageSelectionEvent(tileSheet, Identifier.TILE_SHEET));
					
				//	Player p = new Player(new FVector(300, 300));
				//	p.attachCamera(FlaxEngine.camera);
				//	addEntity(p);	
							
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
		}else
		{
			FLog.warn("Unable to add " + entity + " to " + this);
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
