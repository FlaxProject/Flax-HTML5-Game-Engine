package ie.flax.flaxengine.client;

import java.util.HashMap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;


/**
 * 
 * This is basically a singlton class which loads and stores references to all the images
 * 
 * 
 * @author Ciaran McCann
 *
 */

public class Graphic {

	private static Graphic instance;
	
	
	/**
	 * This class is a singleton
	 * @return
	 */
	public static Graphic getSingleton()
	{
		if(instance == null)
		{
			instance = new Graphic();
		}
		
		return instance;
	}
	
	
	/**
	 * HashMap of JS image objects, which is indexed by the name of the image.
	 * All images loaded into the engine are stored here.
	 */
	private HashMap<String, FImage> imageLibary = new HashMap<String, FImage>();
	
	
	/**
	 * HashMap of JS image objects, which is indexed by the name of the canvas.
	 * 
	 */
	private HashMap<String, FCanvas> canvasCollection = new HashMap<String, FCanvas>();



	/**
	 * Get image with URL refName
	 * @param refName
	 * @return
	 */
	public ImageElement getImage(final String refName) {
		
			return imageLibary.get(refName).getImage();			
	}

	/**
	 * Loads images into the engine.
	 * @param URL - path to where the image is stored
	 */
	public void loadImage(final String URL) {
		
		imageLibary.put(URL, new FImage(URL) );
	}

	/**
	 * Checks all the images that where loaded into the imageLibary to make sure
	 * they are completely loaded.
	 * 
	 * @return true or false
	 */
	public boolean isComponentReady() {
		
	
		if(imageLibary.size() == 0)
			return true;
		
	
		//FIXME: Currently does noting, look into the imageloaded event for answer, though atm doesnt effect the engine
		for (String key : imageLibary.keySet()) {
			
			
			if (imageLibary.get(key).isLoaded() != true) {
				
				//FLog.error("Graphics Component is not ready due to a problem with image"+ key);
				return false;
			}
		

		}
		FLog.debug("Graphics Component is ready to go Sir!");
		return true;

	}

	/**
	 * Creates a FCanvas and stores a reference for later use and returns the reference for use  now
	 * @param referenceName
	 * @param width
	 * @param height
	 * @return
	 */
	public FCanvas createCanvas(final String referenceName, int widthInteger, int heightInteger, String width, String height) {
		
		FCanvas temp = new FCanvas(Canvas.createIfSupported());
		temp.getCanvas().setHeight(height);
		temp.getCanvas().setWidth(width);
		 temp.getCanvas().setCoordinateSpaceHeight(widthInteger);
		    temp.getCanvas().setCoordinateSpaceWidth(heightInteger);
		canvasCollection.put(referenceName, temp);
		
		return temp;
	}
	
	/**
	 * Returns the canvas reference with name referenceName
	 * @param referenceName
	 * @return
	 */
	public FCanvas getCanvas(final String referenceName)
	{
		return canvasCollection.get(referenceName);
	}

	
}
