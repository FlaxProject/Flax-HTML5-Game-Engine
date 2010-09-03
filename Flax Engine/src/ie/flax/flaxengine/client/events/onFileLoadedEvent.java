package ie.flax.flaxengine.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class onFileLoadedEvent extends GwtEvent<onFileLoadedEventHandler> {

	private String dataLoadedFromFile;
	
		
	public onFileLoadedEvent(String dataLoadedFromFile) {
		
		this.dataLoadedFromFile = dataLoadedFromFile;
	}
	
	/**
	 *  For each new event, a new event type must also be specified, 
	 *  with which the event can be registered
	 */
	public static Type<onFileLoadedEventHandler> TYPE = new Type<onFileLoadedEventHandler>();

	public String getDataLoadedFromFile() {
		return dataLoadedFromFile;
	}

	@Override
	protected void dispatch(onFileLoadedEventHandler handler) {
		handler.onFileLoaded(this);
		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<onFileLoadedEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	

}
