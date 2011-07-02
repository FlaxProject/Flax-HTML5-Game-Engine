package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic.FCamera;
import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.Graphic.TimerCallback;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.MiniMapView;
import ie.flax.flaxengine.client.weave.view.Impl.MiniMapViewImpl;

import com.google.gwt.user.client.ui.Widget;

public class MiniMapPresenter extends AbstractPresenter implements
		MiniMapView.presenter {

	private final Weave model;
	private final FCamera cam;
	private final MiniMapView v;
	
	//FIXME CARL - Before starting work remeber we need to remove the hidden files gwt-log 
	// from the tracking and also make your classpath match mine. I have removed all other files that
	// I can though the EGIT interface

	TimerCallback drawLoop = new TimerCallback() {

		@Override
		public void fire() {
			Graphic.getSingleton().requestAnimationFrame(drawLoop);
			model.getFMapReference().draw(cam, v.getCanvas());
		}
	};

	public MiniMapPresenter(Weave model) {
		this.model = model;
		cam = new FCamera(new FVector(0, 0), 500, 500);
		v = new MiniMapViewImpl(this);
		// set up draw loop here
		Graphic.getSingleton().requestAnimationFrame(drawLoop);
	}

	@Override
	public Widget getView() {
		// TODO Auto-generated method stub
		return v.getWidget();
	}

	@Override
	public void moveMapCamera(int x, int y) {
		FlaxEngine.camera.setX(x);
		FlaxEngine.camera.setY(y);
	}
}
