package ie.flax.flaxengine.client.events;

import com.google.gwt.event.shared.HandlerManager;

/**
 * This class has only one property and simply acts as a globe way of accessing the eventBus
 * 
 * @author Ciarán McCann
 *
 */
public class EventBus {
	
	/**
	 * This is the events hanlder which is used for firing and registering events.
	 */
	public static HandlerManager handlerManager = new HandlerManager(null);

}
