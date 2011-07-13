package ie.flax.flaxengine.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class CameraUpdateEvent extends GwtEvent<CameraUpdateEventHandler> {

	/**
	 *  For each new event, a new event type must also be specified, 
	 *  with which the event can be registered
	 */
	public static Type<CameraUpdateEventHandler> TYPE = new Type<CameraUpdateEventHandler>();
	
	
	@Override
	protected void dispatch(CameraUpdateEventHandler handler) {
		handler.onCameraUpdate(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CameraUpdateEventHandler> getAssociatedType() {
		return TYPE;
	}
}
