package ie.flax.flaxengine.client.weave.controls;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;

/**
 * Orginally I though I would leave the logic method open, though now not so sure.
 * will leave it open for the mo, though you can use predefined logic methos such as
 * tileselectedregion
 * 
 * @author Ciarán McCann
 *
 */
public interface IControlTileRegion {
	
	/**
	 * This method most be implemented when an object is created which implements this interface.
	 * It defines the tile logic.
	 */
	public void doTileRegionLogic(int startX, int startY);
	
	public void onMouseUp(MouseUpEvent event);
	public void onMouseDown(MouseDownEvent event);
	public void onMouseMove(MouseMoveEvent event);

}
