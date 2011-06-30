package ie.flax.flaxengine.client.weave.controls;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;

public interface IControlTileRegion {
	
	public void doTileRegionLogic();
	public void onMouseUp(MouseUpEvent event);
	public void onMouseDown(MouseDownEvent event);
	public void onMouseMove(MouseMoveEvent event);

}
