package ie.flax.flaxengine.client.Graphic;

import ie.flax.flaxengine.client.FLog;

import java.util.HashMap;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;

/**
 * This is basically a singleton class which loads and stores references to all
 * the images. It also has some useful utility drawing classes
 * 
 * @author Ciaran McCann
 */
public class Graphic {

    private static Graphic instance;

    /**
     * This class is a singleton
     * 
     * @return
     */
    public static Graphic getSingleton() {
        if (instance == null) {
            instance = new Graphic();
        }

        return instance;
    }

    /**
     * HashMap of JS image objects, which is indexed by the name of the image.
     * All images loaded into the engine are stored here.
     */
    private final HashMap<String, FImage> imageLibary = new HashMap<String, FImage>();

    /**
     * Draws a squared grid
     * 
     * @param width
     * @param height
     * @param gap
     */
    public void drawGrid(double width, double height, int gap,final Canvas canvas) {
       
    	
    	//TODO fix this for better optimisation
    	// canvas.getContext2d().setStrokeStyleDev(Color.RED);
        for (int x = 0; x < width; x += gap) {
            canvas.getContext2d().moveTo(x, 0);
            canvas.getContext2d().lineTo(x, height);
        }

        for (int y = 0; y < height; y += gap) {
            canvas.getContext2d().moveTo(0, y);
            canvas.getContext2d().lineTo(width, y);
        }

        canvas.getContext2d().stroke();
    }

    /**
     * Draws an image to the current canvas object
     * 
     * @param imagePath
     *            - This is the path to the image, which is used to reference
     *            the image lib
     * @param d
     * @param e
     * @param width
     * @param height
     */
    public void drawImage(String imagePath, double x, double y, float width, float height, final Context2d context) { 
    	context.drawImage(Graphic.getSingleton().getImage(imagePath), x, y,width, height);
    }

    /**
     * Draws an image to the current canvas object
     * 
     * @param imagePath
     *            - This is the path to the image, which is used to reference
     *            the image lib
     * @param x
     * @param y
     */
    public void drawImage(String imagePath, float x, float y,final Context2d context) {
        context.drawImage(Graphic.getSingleton().getImage(imagePath), x, y);
    }

    /**
     * Get image with URL refName
     * 
     * @param refName
     * @return
     */
    public final FImage getFImage(final String refName) {
         return imageLibary.get(refName);
    }

    /**
     * Get image with URL refName
     * 
     * @param refName
     * @return
     */
    public final ImageElement getImage(final String refName) {

        FImage temp = imageLibary.get(refName);
        if (temp == null) return null;

        return temp.getImage();
    }

    /**
     * Gets all the FImage objects in the library.
     * 
     * @return
     */
    public HashMap<String, FImage> getImagesHashMap() {
        return imageLibary;

    }

    /**
     * Checks all the images that where loaded into the imageLibary to make sure
     * they are completely loaded.
     * 
     * @return true or false
     */
    public boolean isComponentReady() {

        if (imageLibary.size() == 0) return true;

        // FIXME: Currently does noting, look into the imageloaded event for
        // answer, though atm doesn't effect the engine
        for (String key : imageLibary.keySet()) {

            if (imageLibary.get(key).isLoaded() != true) 
                return false;

        }
        FLog.debug("Graphics Component is ready to go Sir!");
        return true;

    }

    /**
     * The image at the give URL is loaded into the engine.
     * 
     * @param URL
     * @return
     */
    public final FImage loadImage(final String URL) {
  
    	FImage temp = new FImage(URL);
		imageLibary.put(URL, temp);
    	
		
		//TODO In furture look into checking if the image is already in the lib. Though I am pretty sure it just loads from cache so no bigy
    	/**
    	 * Check if image is already in libary
    	 
    	temp = imageLibary.get(URL);
   	   	
    	if( temp == null )
    	{  	
    		temp = new FImage(URL);
    		imageLibary.put(URL, temp);
    	}
    	*/
    	
        return temp;
    }

    /*
     * This code snippet taken from the ForPlay source, under Apache.
     * 
     * We're not using requestAnimationFrame for the moment. We will use
     * it later, but currently it's just too spotty for performance.
     * 
     * FIXME: this is taking more CPU than it should.
     */
    /*
    public native void requestAnimationFrame(TimerCallback callback) /*-{
		var fn = function() {
			callback.@ie.flax.flaxengine.client.Graphic.TimerCallback::fire()();
		};
//		if ($wnd.requestAnimationFrame) {
//			$wnd.requestAnimationFrame(fn);
//		} else if ($wnd.mozRequestAnimationFrame) {
//			$wnd.mozRequestAnimationFrame(fn);
//		} else if ($wnd.webkitRequestAnimationFrame) {
//			$wnd.webkitRequestAnimationFrame(fn);
//		} else {
			$wnd.setTimeout(fn, 1000 / 24); //24fps if fallback necessary
//		}
    }-*/;
    
    public void requestAnimationFrame(TimerCallback callback) {
    	
    }
}
