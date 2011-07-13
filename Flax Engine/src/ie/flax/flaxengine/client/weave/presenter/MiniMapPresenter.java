package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic.FCamera;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.MapUpdateEvent;
import ie.flax.flaxengine.client.events.MapUpdateEventHandler;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.MiniMapView;
import ie.flax.flaxengine.client.weave.view.Impl.MiniMapViewImpl;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

public class MiniMapPresenter extends AbstractPresenter implements MiniMapView.presenter, MapUpdateEventHandler {

	private final Weave model;
	private final FCamera cam;
	private final MiniMapView view;
	private final int inverseScale = 12; // eg 8 if you want 1/8 scale
	Timer timer = new Timer() {
		@Override
		public void run() {
			draw();
		}
	};
	
	
	@Override
	public void onMiniMapUpdate(MapUpdateEvent e) {
		
		if(model.isRunning()) //all ways check to see if weave is running before doing anything intensive
		{
		
		//FIXME CARL - drawing code should go here and also check out the below link
		//http://stackoverflow.com/questions/3318565/any-way-to-clone-html5-canvas-element-with-its-content
			draw();
			//FLog.debug("Draw");
		}
		
	}
	
	
	

	public MiniMapPresenter(Weave editor) {
		
		this.model = editor;
		view = new MiniMapViewImpl(this);
		cam = new FCamera(new FVector(0, 0), FlaxEngine.camera.getWidth()* inverseScale, FlaxEngine.camera.getHeight() *inverseScale);
		
		view.getCanvas().getContext2d().scale(1.0 / inverseScale, 1.0 / inverseScale);
		
		EventBus.handlerManager.addHandler(MapUpdateEvent.TYPE, this);

		/*
		 * Once, I used requestAnimationFrame here. However, there are two
		 * things wrong with this. One is that this _doesn't need to refresh
		 * that often_. Users won't notice. The other is that it makes much more
		 * sense to just update the minimap when and only when the main map is
		 * changed. Compromise for the moment - timer that refreshes every
		 * second.
		 */

		// TODO Carl change to event-based?
		//timer.scheduleRepeating(50);
	}

	@Override
	public Widget getView() {
		return view.getWidget();
	}

	@Override
	public void moveMapCamera(int x, int y) {
		// divided by two to offset the move. TODO Carl make this better
		FlaxEngine.camera.setX((x * inverseScale) / 2);
		FlaxEngine.camera.setY((y * inverseScale) / 2);
	}

	@Override
	public void drawCurrentCameraRectangle() {
		view.getCanvas().getContext2d().setStrokeStyle("#F00");
		view.getCanvas().getContext2d().setLineWidth(8.0);
		view.getCanvas().getContext2d().strokeRect(
				FlaxEngine.camera.getX(), 
				FlaxEngine.camera.getY(), 
				FlaxEngine.camera.getWidth(), 
				FlaxEngine.camera.getHeight());
	}
	
	private void clear() {
		view.getCanvas().getContext2d().fillRect(0, 0,
				view.getCanvas().getCoordinateSpaceWidth()*inverseScale,
				view.getCanvas().getCoordinateSpaceHeight()*inverseScale);
	}




	private void draw() {
		clear();
		
		if (view.getCanvas().getCoordinateSpaceHeight() == 0) {
			view.getCanvas().setCoordinateSpaceHeight(
					view.getCanvas().getCanvasElement().getClientHeight());
			view.getCanvas().setCoordinateSpaceWidth(
					view.getCanvas().getCanvasElement().getClientWidth());
			view.getCanvas().getContext2d()
					.scale(1.0 / inverseScale, 1.0 / inverseScale);
		}
		
		
		model.getFMapReference().draw(cam, view.getCanvas());
		drawCurrentCameraRectangle();
		
	}


}
