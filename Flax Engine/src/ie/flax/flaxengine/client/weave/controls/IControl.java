package ie.flax.flaxengine.client.weave.controls;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;

/**
 * Generic interfacen for a control, makes for easy polymorphic operations
 * @author Ciarán McCann
 *
 */
public interface IControl {
	
	public void onMouseUp(MouseUpEvent event);
	public void onMouseDown(MouseDownEvent event);
	public void onMouseMove(MouseMoveEvent event);

}
