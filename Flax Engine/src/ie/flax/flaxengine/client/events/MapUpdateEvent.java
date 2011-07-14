package ie.flax.flaxengine.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class MapUpdateEvent extends GwtEvent<MapUpdateEventHandler> {

	/**
	 *  For each new event, a new event type must also be specified, 
	 *  with which the event can be registered
	 */
	public static Type<MapUpdateEventHandler> TYPE = new Type<MapUpdateEventHandler>();
	
	
	@Override
	protected void dispatch(MapUpdateEventHandler handler) {
		handler.onMapUpdate(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MapUpdateEventHandler> getAssociatedType() {
		return TYPE;
	}

	

}
