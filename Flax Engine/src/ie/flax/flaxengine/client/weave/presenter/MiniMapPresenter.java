package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic.FCamera;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.MiniMapView;
import ie.flax.flaxengine.client.weave.view.Impl.MiniMapViewImpl;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

public class MiniMapPresenter extends AbstractPresenter implements
		MiniMapView.presenter {

	private final Weave model;
	private final FCamera cam;
	private final MiniMapView v;
	private final int inverseScale = 8; // eg 8 if you want 1/8 scale
	Timer timer = new Timer() {
		@Override
		public void run() {
			v.getCanvas().getContext2d().setFillStyle("#0F0");
			v.getCanvas().getContext2d().fillRect(0, 0, v.getCanvas().getOffsetWidth(), v.getCanvas().getOffsetHeight());
			
			if (v.getCanvas().getCoordinateSpaceHeight() == 0) {
				v.getCanvas().setCoordinateSpaceHeight(
						v.getCanvas().getCanvasElement().getClientHeight());
				v.getCanvas().setCoordinateSpaceWidth(
						v.getCanvas().getCanvasElement().getClientWidth());
				v.getCanvas().getContext2d()
						.scale(1.0 / inverseScale, 1.0 / inverseScale);
			}
			
			model.getFMapReference().draw(cam, v.getCanvas());
			drawCurrentCameraRectangle();
			
		}
	};

	public MiniMapPresenter(Weave model) {
		this.model = model;
		v = new MiniMapViewImpl(this);
		cam = new FCamera(new FVector(0, 0), FlaxEngine.camera.getWidth()
				* inverseScale, FlaxEngine.camera.getHeight());
		v.getCanvas().getContext2d()
				.scale(1.0 / inverseScale, 1.0 / inverseScale);

		/*
		 * Once, I used requestAnimationFrame here. However, there are two
		 * things wrong with this. One is that this _doesn't need to refresh
		 * that often_. Users won't notice. The other is that it makes much more
		 * sense to just update the minimap when and only when the main map is
		 * changed. Compromise for the moment - timer that refreshes every
		 * second.
		 */

		// TODO Carl change to event-based?
		timer.scheduleRepeating(1000);
	}

	@Override
	public Widget getView() {
		return v.getWidget();
	}

	@Override
	public void moveMapCamera(int x, int y) {
		// divided by two to offset the move. TODO Carl make this better
		FlaxEngine.camera.setX((x / 2) * inverseScale);
		FlaxEngine.camera.setY((y / 2) * inverseScale);
	}

	@Override
	public void drawCurrentCameraRectangle() {
		v.getCanvas().getContext2d().setStrokeStyle("#F00");
		v.getCanvas().getContext2d().rect(
				FlaxEngine.camera.getX(), 
				FlaxEngine.camera.getY(), 
				FlaxEngine.camera.getWidth(), 
				FlaxEngine.camera.getHeight());
		v.getCanvas().getContext2d().stroke();
	}
}
