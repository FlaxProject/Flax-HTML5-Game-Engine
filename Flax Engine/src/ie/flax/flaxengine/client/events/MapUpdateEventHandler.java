package ie.flax.flaxengine.client.events;

import com.google.gwt.event.shared.EventHandler;


public interface MapUpdateEventHandler extends EventHandler{

	/**
	 * The code body for this method is implemented in the listening class. The code in the body 
	 * is called when the event is fired and caught by the listner
	 * @param e
	 */
	void onMapUpdate(MapUpdateEvent e);
}
