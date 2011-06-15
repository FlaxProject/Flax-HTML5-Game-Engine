package ie.flax.flaxengine.client;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;

/**
 * This wrapps the GWT Image class, so that we can add isLoaded Meta data to the class
 * @author Ciarán McCann
 * @since Jun 16th 12:05AM
 * @
 *
 */
public class FImage {

	private ImageElement image;
	private boolean imageLoaded;
	
	/**
	 * Takes the path to the image you wish to load.
	 * @param URL
	 */
	public FImage(String URL){
		
		imageLoaded = false;
		
		Image imageData = new Image(URL);
		image = (ImageElement)(imageData).getElement().cast();
		
		/**
		 * CallBack which marks the file as loaded
		 */
		imageData.addLoadHandler(new LoadHandler() {
	      public void onLoad(LoadEvent event) {
	    	  
	        imageLoaded = true;
	      }
	    });
	}
	
	/**
	 * Is the image loaded yet or not.
	 * @return
	 */
	public boolean isLoaded()
	{
		return imageLoaded;
	}
	
	/**
	 * Gives the actually image you can draw with
	 * @return
	 */
	public ImageElement getImage()
	{
		return image;
	}
}



