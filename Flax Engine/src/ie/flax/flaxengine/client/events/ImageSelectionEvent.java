package ie.flax.flaxengine.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This is the event object for onFileLoaded, it allows the file data to be stored and 
 * passed to the listening classes.
 * @author Ciarán McCann
 *
 */
public class ImageSelectionEvent extends GwtEvent<ImageSelectionEventHandler> {

	public enum Idenfiter{
		TILE_SHEET,
		NONE
	}
	
	
	/**
	 * This the string of file data 
	 */
	private String imageUrl;
	private Idenfiter idenfiter;
	
	public ImageSelectionEvent(String imageUrl, Idenfiter idenfiter) {
		this.imageUrl = imageUrl;
		this.idenfiter = idenfiter;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public Idenfiter getIdenfiter() {
		return idenfiter;
	}

	/**
	 *  For each new event, a new event type must also be specified, 
	 *  with which the event can be registered
	 */
	public static Type<ImageSelectionEventHandler> TYPE = new Type<ImageSelectionEventHandler>();

	
	
	@Override
	protected void dispatch(ImageSelectionEventHandler handler) {
		handler.onImageSelection(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ImageSelectionEventHandler> getAssociatedType() {
		return TYPE;
	}

	

}
