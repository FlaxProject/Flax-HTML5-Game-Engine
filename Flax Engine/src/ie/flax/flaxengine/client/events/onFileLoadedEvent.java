package ie.flax.flaxengine.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class onFileLoadedEvent extends GwtEvent<onFileLoadedEventHandler> {

	private String dataLoadedFromFile;
	
		
	public onFileLoadedEvent(String dataLoadedFromFile) {
		
		this.dataLoadedFromFile = dataLoadedFromFile;
	}

	public String getDataLoadedFromFile() {
		return dataLoadedFromFile;
	}

	@Override
	protected void dispatch(onFileLoadedEventHandler handler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<onFileLoadedEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return null;
	}

}
