package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.Widget;

public interface MiniMapView {
	public interface presenter {
		public void moveMapCamera(int x, int y);

		public void drawCurrentCameraRectangle();
	}

	public Canvas getCanvas();

	public Widget getWidget();
}
