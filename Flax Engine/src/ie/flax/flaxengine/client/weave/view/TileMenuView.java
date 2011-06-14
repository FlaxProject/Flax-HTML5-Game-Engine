package ie.flax.flaxengine.client.weave.view;

import ie.flax.flaxengine.client.Graphic;
import ie.flax.flaxengine.client.weave.presenter.TileMenuPresenter;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TileMenuView extends Composite implements TileMenuPresenter.Display {

	Canvas canvas;
	
	public TileMenuView()
	{
		canvas = Graphic.getSingleton().createCanvas("TileCanvas",  "100%", "40px").getCanvas();
		initWidget(canvas);
	}

	@Override
	public HasClickHandlers getTileCanvas() {
		return canvas;
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}
	


}
