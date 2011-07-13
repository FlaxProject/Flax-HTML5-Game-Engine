package ie.flax.flaxengine.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class MiniMapUpdateEvent extends GwtEvent<MiniMapUpdateEventHandler> {

	/**
	 *  For each new event, a new event type must also be specified, 
	 *  with which the event can be registered
	 */
	public static Type<MiniMapUpdateEventHandler> TYPE = new Type<MiniMapUpdateEventHandler>();
	
	
	@Override
	protected void dispatch(MiniMapUpdateEventHandler handler) {
		handler.onMiniMapUpdate(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MiniMapUpdateEventHandler> getAssociatedType() {
		return TYPE;
	}

	

}
