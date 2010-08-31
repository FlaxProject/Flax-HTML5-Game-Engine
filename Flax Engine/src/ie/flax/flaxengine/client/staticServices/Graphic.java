package ie.flax.flaxengine.client.staticServices;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dev.util.collect.HashMap;

/**
 *  Graphics class is a static service that works as an abstraction 
 *  layer for the graphics system in the engine or interface with non-technology
 *  specific functionality. It stores all the loaded images in the engine and 
 *  handles the basic functions of drawing images to the screen.
 * 
 * @author Ciarán McCann
 *
 */
public class Graphic {

	/**
	 * HashMap of JS image objects, which is indexed by the name of the image. All images loaded into the engine are stored here. 
	 */
	private Map<String, JavaScriptObject> imageLibary = new HashMap<String,JavaScriptObject>();
	
	/**
	 * Is the reference to the drawing object
	 */
	private static FCanvas graphicLayer;

	/**
	 * Initialises the graphics components
	 * @param insertId is the ID of the HTML element in which to insert the canvas
	 *            tag.
	 */
	public void init(String insertId) {
		
		graphicLayer = new FCanvas(insertId);

	}
	
	
	
	
}
