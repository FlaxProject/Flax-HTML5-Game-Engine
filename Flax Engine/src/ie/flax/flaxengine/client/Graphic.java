package ie.flax.flaxengine.client;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import java.util.HashMap;

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
	private static HashMap<String, JavaScriptObject> imageLibary = new HashMap<String, JavaScriptObject>();
	

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
		Log.info("graphics component started successfully");
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
				
				//TODO sort this loading problem and remove this debug code
				if(Graphic.isLoaded(imageLibary.get(imagePath)))
				{
					Log.info(" Double check and I think " + imagePath + " loaded ");
					graphicLayer.drawImage(imageLibary.get(imagePath), x, y, width,height);
				}
				else
				{
					Log.error("On secound thoughts " + imagePath + " is not loaded");
				}
				
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
			
			graphicLayer.drawImage(imageLibary.get(imagePath), xSrc, ySrc, widthSrc, heightSrc, xDes, yDes, widthDes, heightDes);
		} catch (Exception e) {
			Log.error("Graphic.drawImage - error drawing image object width index key of "+ imagePath);
		
		}

	}

	/**
	 * Loads images into the engine
	 * @param imagePath - pass in the path to the image you wish to load and then the path is used
	 */
	public static void loadImage(String imagePath, String nameToReferenceBy) {
			
		try {
			imageLibary.put(nameToReferenceBy, graphicLayer.loadImage(imagePath)); //TODO fix onLoad bug, need to check are imageLoaded before drawing
							
			Log.info("Image " + imagePath + " Loaded sucessfully " );
		} catch (Exception e) {
			Log.error("Graphic.LoadImage - error loading image with name "+ imagePath  + e);
		}
	}
	
	public native static boolean isLoaded(JavaScriptObject c)	
	/*-{	 									 	
					return c.complete;
	}-*/;
	

	
}
