package ie.flax.flaxengine.client.staticServices;

import java.util.Map;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dev.util.collect.HashMap;

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
	private static Map<String, JavaScriptObject> imageLibary = new HashMap<String, JavaScriptObject>();

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
	public static void drawImage(String imagePath, float x, float y,
			float height, float width) {
		
			try {
				graphicLayer.drawImage(imageLibary.get(imagePath), x, y,
						 width,height);
			} catch (Exception e) {
				Log.error(
						"Graphic.drawImage - error drawing image object width index key of "
								+ imagePath + "<br>" + e, imageLibary
								.get(imagePath));
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
			Log.error(
					"Graphic.drawImage - error drawing image object width index key of "
							+ imagePath + "<br>" + e, imageLibary
							.get(imagePath));
		}

	}

	/**
	 * Loads images into the engine
	 * @param imagePath - simply pass in the string name of the image you wish to load and make sure the image is in the image folder or the speficed location in the settings.IMAGE_DIR
	 */
	public static void loadImage(String imagePath) {
		
		try {
			imageLibary.put(imagePath, graphicLayer.loadImage(imagePath));
		} catch (Exception e) {
			Log.error("Graphic.LoadImage - error loading image with name "
					+ imagePath + "<br>" + e);
		}
	}

}
