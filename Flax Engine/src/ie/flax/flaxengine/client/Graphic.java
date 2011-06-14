package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onImageLoadedEvent;

import java.util.HashMap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Window;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;


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
	private HashMap<String, ImageElement> imageLibary = new HashMap<String, ImageElement>();
	
	
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
		
			return imageLibary.get(refName);			
	}

	/**
	 * Loads images into the engine.
	 * @param URL - path to where the image is stored
	 */
	public void loadImage(final String URL) {
		
		String[] urls = new String[] { URL };
		FLog.info("Currently loading image " + URL  +" - Loading...");
		ImageLoader.loadImages(urls, new ImageLoader.CallBack() {

			@Override
			public void onImagesLoaded(ImageElement[] imageElements) { //TODO: take the imageLoader class and mod it for 404 callbacks
	
				imageLibary.put(URL,(imageElements[0]));
				EventBus.handlerManager.fireEvent( new onImageLoadedEvent(URL));
				FLog.info("Image " + URL + " is now loaded!");
			}
		});		
		
	}

	/**
	 * Checks all the images that where loaded into the imageLibary to make sure
	 * they are completely loaded.
	 * 
	 * @return true or false
	 */
	public boolean isComponentReady() {

		//FIXME: Currently does noting, look into the imageloaded event for answer, though atm doesnt effect the engine
		for (String key : imageLibary.keySet()) {
			
			if (imageLibary.get(key) == null) {
				
				FLog.error("Graphics Component is not ready due to a problem with image ###########"+ key);
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
	public FCanvas createCanvas(final String referenceName, final String width, final String height) {
		
		FCanvas temp = new FCanvas(Canvas.createIfSupported());
		temp.getCanvas().setHeight(height);
		temp.getCanvas().setWidth(width);
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
