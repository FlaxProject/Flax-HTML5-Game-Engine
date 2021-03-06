package ie.flax.flaxengine.client.events;

import com.google.gwt.event.shared.EventHandler;

/**
 * Spefic Events handler interface for event onFileLoadedEvent
 * @author Ciarán McCann
 *
 */
public interface ImageSelectionEventHandler extends EventHandler{

	/**
	 * The code body for this method is implemented in the listening class. The code in the body 
	 * is called when the event is fired and caught by the listener
	 * @param e
	 */
	void onImageSelection(ImageSelectionEvent e);
}
