package ie.flax.flaxengine.client;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;

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
	private static HashMap<String, ImageElement> imageLibary = new HashMap<String, ImageElement>();

	/**
	 * Manages the all the canvas elements
	 */
	private static HashMap<String, FCanvas> canvasElements = new HashMap<String, FCanvas>(1);

	/**
	 * Gets a canvas
	 * @param referenceName
	 * @return  a reference to a canvas object
	 */
	public static FCanvas getCanvas(String referenceName) {
		return canvasElements.get(referenceName);
	}

	/**
	 * Gets the default canvas, IE the zero index canvas
	 * @return - Returns a reference to a canvas object
	 */
	public static FCanvas getCanvas() {
		return canvasElements.get(0); // TODO: ecpection handling
	}

	/**
	 * Get image with URL refName
	 * @param refName
	 * @return
	 */
	public static ImageElement getImage(String refName) {
		
			return imageLibary.get(refName);
		
			
	}

	/**
	 * Loads images into the engine.
	 * @param URL - path to where the image is stored
	 */
	public static void loadImage(final String URL) {
		String[] urls = new String[] { URL };

		ImageLoader.loadImages(urls, new ImageLoader.CallBack() {

			@Override
			public void onImagesLoaded(ImageElement[] imageElements) {
	
				imageLibary.put(URL,(imageElements[0]));
			}
		});
	}

	
	/**
	 * Creates a canvas object and stores it. 
	 * @param canvasRefName - Used to reference the canvas obj that was created
	 * @param width - width of the canvas
	 * @param height - height of the canvas
	 */
	public static void createCanvas(String canvasRefName, int width, int height) {
		canvasElements.put(canvasRefName, new FCanvas(width, height));
		FLog.info("graphics component started successfully");
	}

	/**
	 * Checks all the images that where loaded into the imageLibary to make sure
	 * they are completely loaded.
	 * 
	 * @return true or false
	 */
	public static boolean isComponentReady() {

		for (String key : imageLibary.keySet()) {
			
			if (imageLibary.get(key) == null) {
				
				FLog.error("Graphics Component is not ready due to a problem with image ###########"+ key);
				return false;
			}

		}
		FLog.debug("Graphics Component is ready to go Sir!");
		return true;

	}

	public native  JavaScriptObject loadImageOffline(JavaScriptObject file)
	/*-{		
				  
   	 var img = new Image();
     img.file = file;		
		   
     var reader = new FileReader();
     reader.onload = (function(aImg) { return function(e) { aImg.src = e.target.result; }; })(img);
     reader.readAsDataURL(file);
    
    return img;
 
				
	}-*/;
}
