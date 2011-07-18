package ie.flax.flaxengine.client.Graphic;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onImageLoadedEvent;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * This wraps the GWT Image class, so that we can add isLoaded Meta data to the class
 * @author Ciarán McCann
 * @since Jun 16th 12:05AM
 * 
 *
 */
public class FImage {

	private ImageElement image;
	private boolean imageLoaded;
	private Image imageData;
	private static FlowPanel BOOT_STRAP_DIV = new FlowPanel();
	
	/**
	 * Takes the path to the image you wish to load.
	 * @param URL
	 */
	public FImage(final String URL){
		
		BOOT_STRAP_DIV.setStylePrimaryName("boot");
		
		imageLoaded = false;		
		imageData = new Image(URL);
		
		
		/**
		 * CallBack which marks the file as loaded
		 */
		imageData.addLoadHandler(new LoadHandler() {
	      public void onLoad(LoadEvent event) {
	    	  
	        imageLoaded = true;
	        image = (ImageElement)(imageData).getElement().cast();
	       
	        
	        	//FIXME Problem with IE not getting widths and heights from image. 
	        	//people say to set the containing element to visibility: hidden, though doesn't seem to work, will look into it more later
	        	//Window.alert("W " + imageData.getWidth() );
	        	//image.setWidth(imageData.getWidth());
	        	//image.setHeight(imageData.getHeight());
	        
	        FLog.info("Image " + URL + " has loaded ");
	        EventBus.handlerManager.fireEvent(new onImageLoadedEvent(URL)); //lets all listening components that a file is loaded
	       
	      }
	    });
		
		imageData.setVisible(false);		
		BOOT_STRAP_DIV.add(imageData);		
	}
	
	/**
	 * Do something when this image loads
	 * @param handler
	 */
	public void addLoadHanderl(LoadHandler handler)
	{
		imageData.addLoadHandler(handler);
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
	
	/**
	 * Gives the widget in which the images will be loaded
	 * @return
	 */
	public static Widget getBootStrapDiv()
	{
		return BOOT_STRAP_DIV;
	}
}



