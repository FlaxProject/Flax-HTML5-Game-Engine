package ie.flax.flaxengine.client;


import java.util.HashMap;
import com.allen_sauer.gwt.log.client.Log;

/**
 * Graphics class is a static service that works as an abstraction layer for the
 * graphics system in the engine or interface with non-technology specific
 * functionality. It stores all the loaded images in the engine and handles the
 * basic functions of drawing images to the screen.
 * 
 * @author Ciarán McCann
 * 
 */
public class Graphic {

	/**
	 * HashMap of JS image objects, which is indexed by the name of the image.
	 * All images loaded into the engine are stored here.
	 */
	public static HashMap<String, FImage> imageLibary = new HashMap<String, FImage>();
	

	/**
	 * Is the reference to the drawing object
	 */
	private static FCanvas graphicLayer;

	/**
	 * Initialises the graphics components
	 * 
	 * @param insertId
	 *            is the ID of the HTML element in which to insert the canvas
	 *            tag.
	 */
	public static void init(String insertId) {
		graphicLayer = new FCanvas(insertId);
		Log.info("Graphics component started successfully");
	}

	/**
	 * Initialises the graphics components
	 * 
	 * @param insertId
	 *            is the ID of the HTML element in which to insert the canvas
	 *            tag.
	 * @param width
	 *            of the canvas
	 * @param height
	 *            of the canvas
	 */
	public static void init(String insertId, int width, int height) {
		graphicLayer = new FCanvas(insertId, width, height);
		Log.info("graphics component started successfully");
	}

	/**
	 * Checks all the images that where loaded into the imageLibary to make sure
	 * they are completely loaded.
	 * 
	 * @return true or false
	 */
	public static boolean isComponentReady() {
		
		for (String key : imageLibary.keySet()) 
		{
			if (imageLibary.get(key).getHeight() == 0) //TODO: Change imageLib to private and use getter methods
			{
				Log.error("Graphics Component is not ready due to a problem with image " + key );
				return false;
			}
		}
		
		Log.debug("Graphics Component is ready to go Sir!");
		return true;
	
	}

	/**
	 * Draws an image to the canvas
	 * 
	 * @param imageObj
	 *            This is the actual image which will be drawn
	 * @param x
	 * @param y
	 * @param height
	 * @param width
	 */
	public static void drawImage(String imagePath, float x, float y,float height, float width) {
		
			try {			
					graphicLayer.drawImage(imageLibary.get(imagePath).imageData, x, y, width,height);
			
			} catch (Exception e) {
				Log.error("Graphic.drawImage - error drawing image object width index key of "+ imagePath);
			}
	}
	
	
	/**
	 * 
	 * @param imagePath
	 * @param Texture
	 * @param tileSize
	 * @param x
	 * @param y
	 */
	public static void drawTile(String imagePath, int Texture, int tileSize, float x, float y)
	{	
		int numTilesWidth = (imageLibary.get(imagePath).getWidth()/tileSize);
	
		int ySrc = (int)(Texture/numTilesWidth);
		float xSrc = Texture%numTilesWidth;
	
		try {
			
			graphicLayer.drawImage(imageLibary.get(imagePath).imageData, (float)xSrc*tileSize, (float)ySrc*tileSize, tileSize, tileSize, x, y, tileSize, tileSize);
		} catch (Exception e) {
			Log.error("Graphic.drawImage - error drawing image object width index key of "+ imagePath);
		
		}
		
	}
	
			
	
	/**
	 * Draws an segment of an image to a segment of the canvas
	 * 
	 * @param imageObj
	 * @param xSrc
	 * @param ySrc
	 * @param widthSrc
	 * @param heightSrc
	 * @param xDes
	 * @param yDes
	 * @param widthDes
	 * @param heightDes
	 */
	public static void drawImage(String imagePath, float xSrc,float ySrc, float widthSrc, float heightSrc, float xDes,float yDes, float widthDes, float heightDes)
	{
		try {
			
			graphicLayer.drawImage(imageLibary.get(imagePath).imageData, xSrc, ySrc, widthSrc, heightSrc, xDes, yDes, widthDes, heightDes);
		} catch (Exception e) {
			Log.error("Graphic.drawImage - error drawing image object width index key of "+ imagePath);
		
		}

	}

	/**
	 * Loads images into the engine
	 * @param imagePath - pass in the path to the image you wish to load and then the path is used
	 */
	public static void loadImage(String imagePath, String nameToReferenceBy) {
		
		if(imageLibary.get(nameToReferenceBy) == null)//Makes sure there are no duplicates
		{
		
		try {
			imageLibary.put(nameToReferenceBy, new FImage(graphicLayer.loadImage(imagePath))); //TODO fix onLoad bug, need to check are imageLoaded before drawing
			
			if(imageLibary.get(nameToReferenceBy).isLoaded())
			{
				Log.info("Image " + imagePath + " Loaded sucessfully " );
			}
			else
			{
				Log.error("Image " + imagePath + " didn't loaded - looks like a 404");
			}
			
		} catch (Exception e) {
			Log.error("Graphic.LoadImage - error loading image with name "+ imagePath  + e);
		}
		}
	}
	
	
	

	
}
