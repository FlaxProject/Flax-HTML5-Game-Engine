package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic.FCamera;
import ie.flax.flaxengine.client.events.CameraUpdateEvent;
import ie.flax.flaxengine.client.events.CameraUpdateEventHandler;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.MapUpdateEvent;
import ie.flax.flaxengine.client.events.MapUpdateEventHandler;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.MiniMapView;
import ie.flax.flaxengine.client.weave.view.Impl.MiniMapViewImpl;

import java.util.Date;

import com.google.gwt.user.client.ui.Widget;

public class MiniMapPresenter extends AbstractPresenter implements MiniMapView.presenter, MapUpdateEventHandler, CameraUpdateEventHandler {

	private final Weave model;
	private final FCamera cam;
	private final MiniMapView view;
	private final int inverseScale = 12; // eg 8 if you want 1/8 scale

	private double oldTime = new Date().getTime();
	private double currentTime;
	private double elapsedTime;
	
	private boolean isRunning = false;
	private boolean firstRun = true;
	
	@Override
	public void onMapUpdate(MapUpdateEvent e) {
		//if(firstRun){
		//	//this is in order to set up the canvas width and height (yes, I know, wtf)
		//	draw();
		//	firstRun = false;
		//}
		if(isRunning){
			currentTime = new Date().getTime();
			elapsedTime = currentTime - oldTime;
			if(model.isRunning()){
				if (elapsedTime > 2000){
					oldTime = currentTime;
					draw();
				}
			}
		}
	}
	
	@Override
	public void onCameraUpdate(CameraUpdateEvent e) {
		if(isRunning){
			if (model.isRunning()){
				clear();
				draw();
				drawCurrentCameraRectangle();
			}
		}
	}
	
	public MiniMapPresenter(Weave editor) {
		this.model = editor;
		view = new MiniMapViewImpl(this);
		cam = new FCamera(new FVector(0, 0), FlaxEngine.camera.getWidth()* inverseScale, FlaxEngine.camera.getHeight() *inverseScale);
		
		view.getCanvas().getContext2d().scale(1.0 / inverseScale, 1.0 / inverseScale);
		
		EventBus.handlerManager.addHandler(MapUpdateEvent.TYPE, this);
		EventBus.handlerManager.addHandler(CameraUpdateEvent.TYPE, this);
		
	}

	@Override
	public Widget getView() {
		return view.getWidget();
	}

	@Override
	public void moveMapCamera(int x, int y) {
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
		if (view.getCanvas().getCoordinateSpaceHeight() == 0) {
			view.getCanvas().setCoordinateSpaceHeight(
					view.getCanvas().getCanvasElement().getClientHeight());
			view.getCanvas().setCoordinateSpaceWidth(
					view.getCanvas().getCanvasElement().getClientWidth());
			view.getCanvas().getContext2d()
					.scale(1.0 / inverseScale, 1.0 / inverseScale);
		}
		
		
		model.getFMapReference().draw(cam, view.getCanvas(),-1);
	}

	@Override
	public void setRunning(boolean run) {
		isRunning = run;
	}
	
	@Override
	public boolean isRunning() {
		return isRunning;
	}
}
