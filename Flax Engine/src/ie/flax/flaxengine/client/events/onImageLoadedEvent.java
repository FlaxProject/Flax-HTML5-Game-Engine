package ie.flax.flaxengine.client.events;

import com.google.gwt.event.shared.GwtEvent;


public class onImageLoadedEvent extends GwtEvent<onImageLoadedEventHandler>{

	private String imageName;	
	
	public onImageLoadedEvent(String imageName){
		this.imageName = imageName;
	}
	
	/**
	 * Gets the name of the image which was loaded
	 * @return
	 */
	public String getImageName(){
		return imageName;
	}
	

	/**
	 *  For each new event, a new event type must also be specified, 
	 *  with which the event can be registered
	 */
	public static Type<onImageLoadedEventHandler> TYPE = new Type<onImageLoadedEventHandler>();

	@Override
	public Type<onImageLoadedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(onImageLoadedEventHandler handler) {
		handler.onImageLoaded(this);
	}
}
