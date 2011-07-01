package ie.flax.flaxengine.client.Graphic;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

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
	private final Image imageData;
	private final static FlowPanel BOOT_STRAP_DIV = new FlowPanel();
	
	/**
	 * Takes the path to the image you wish to load.
	 * @param URL
	 */
	public FImage(final String URL){
		
		imageLoaded = false;		
		imageData = new Image(URL);
		
		
		/**
		 * CallBack which marks the file as loaded
		 */
		imageData.addLoadHandler(new LoadHandler() {
	      public void onLoad(LoadEvent event) {
	    	  
	        imageLoaded = true;
	        image = (ImageElement)(imageData).getElement().cast();
	       
	      }
	    });
		
		imageData.setVisible(false);
		
		BOOT_STRAP_DIV.add(imageData);
	}
	
	/**
	 * Do somthing when this image loads
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
	
	public static Widget getBootStrapDiv()
	{
		return BOOT_STRAP_DIV;
	}
}



