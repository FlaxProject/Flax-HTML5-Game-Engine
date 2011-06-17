package ie.flax.flaxengine.client.Graphic;

import ie.flax.flaxengine.client.FLog;

import java.util.HashMap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
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
	private HashMap<String, Canvas> canvasCollection = new HashMap<String, Canvas>();



	/**
	 * Get image with URL refName
	 * @param refName
	 * @return
	 */
	public final ImageElement getImage(final String refName) {
		
		FImage temp = imageLibary.get(refName);
		
		if(temp == null)
			return null;
		
			return temp.getImage();			
	}
	


	/**
	 * The image at the give URl is loaded into the engine.
	 * @param URL
	 * @return
	 */
	public final FImage loadImage(final String URL) {
		
		FImage temp = new FImage(URL);
		imageLibary.put(URL, temp);
		return temp;
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
	public Canvas createCanvas(final String referenceName, int widthInteger, int heightInteger, String width, String height) {
		
		Canvas temp = Canvas.createIfSupported();
		temp.setHeight(height);
		temp.setWidth(width);
		 temp.setCoordinateSpaceHeight(widthInteger);
		    temp.setCoordinateSpaceWidth(heightInteger);
		canvasCollection.put(referenceName, temp);
		
		return temp;
	}
	
	/**
	 * Returns the canvas reference with name referenceName
	 * @param referenceName
	 * @return
	 */
	public final Canvas getCanvas(final String referenceName)
	{
		return canvasCollection.get(referenceName);
	}
	
	

	/**
	 * Draws an image to the current canvas object
	 * @param imagePath - This is the path to the image, which is used to reference the image lib
	 * @param x 
	 * @param y	
	*/
	public void drawImage(String imagePath, float x, float y,final Context2d context) {
			
			context.drawImage(Graphic.getSingleton().getImage(imagePath), x, y);
	
			//FLog.warn("DrawImage: Unable to drawImage as the image "+ imagePath +" is null");
	}
	 
	
	/**
	 * Draws an image to the current canvas object
	 * @param imagePath - This is the path to the image, which is used to reference the image lib
	 * @param d
	 * @param e
	 * @param width
	 * @param height
	*/
	public void drawImage(String imagePath, double x, double y, float width, float height, final Context2d context) {
	
		context.drawImage(Graphic.getSingleton().getImage(imagePath), x, y,width,height);
	
	}
	 
	
	
	/**
	 * Draws a squared grid
	 * @param width
	 * @param height
	 * @param gap
	 */
	public void drawGrid(double width, double height, int gap, final Canvas canvas)
	{
		//canvas.getContext2d().setStrokeStyleDev(Color.RED);				
		for (int x = 0; x < width; x += gap) {
			canvas.getContext2d().moveTo(x, 0);
			canvas.getContext2d().lineTo(x, height);
		}
		
		for (int y = 0; y < height; y+= gap) {
			canvas.getContext2d().moveTo(0, y);
			canvas.getContext2d().lineTo(width, y);
		}
			
		canvas.getContext2d().stroke();
	}

	
	public String[] getKeys()
	{
		return (String[]) imageLibary.keySet().toArray();
	
	}
	
}
