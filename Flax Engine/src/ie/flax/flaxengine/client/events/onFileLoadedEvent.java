package ie.flax.flaxengine.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This is the event object for onFileLoaded, it allows the file data to be stored and 
 * passed to the listening classes.
 * @author Ciarán McCann
 *
 */
public class onFileLoadedEvent extends GwtEvent<onFileLoadedEventHandler> {

	/**
	 * This the string of file data 
	 */
	private String dataLoadedFromFile;
	private String id;
	
	public String getId() {
		return id;
	}
	
	/**
	 * Constructs the event object
	 * @param dataLoadedFromFile stores the File Data for transport as string
	 * @param id 
	 */
	public onFileLoadedEvent(String dataLoadedFromFile, String id) {
		this.id = id;
		this.dataLoadedFromFile = dataLoadedFromFile;
	}
	
	
	/**
	 * Access the data which was got from the file
	 * @return String filedata
	 */
	public String getDataLoadedFromFile() {
		return dataLoadedFromFile;
	}



	/**
	 *  For each new event, a new event type must also be specified, 
	 *  with which the event can be registered
	 */
	public static Type<onFileLoadedEventHandler> TYPE = new Type<onFileLoadedEventHandler>();

	
	
	@Override
	protected void dispatch(onFileLoadedEventHandler handler) {
		handler.onFileLoaded(this);
		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<onFileLoadedEventHandler> getAssociatedType() {
		return TYPE;
	}
}
