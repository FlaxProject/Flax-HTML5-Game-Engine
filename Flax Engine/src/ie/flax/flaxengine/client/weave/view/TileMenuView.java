package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.user.client.ui.Widget;


/**
 * This is the tileSheet view, usally at the south panel
 *
 */
public interface TileMenuView  {

	Widget asWidget();
	Canvas getTileCanvas(); 


	public interface presenter {
		
		void displayTileSelectionMenu();
		void onCanvasMouseMove(MouseMoveEvent event);
	}

}
