package ie.flax.flaxengine.client.weave.view;

import ie.flax.flaxengine.client.weave.presenter.TileMenuPresenter;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the tileSheet view, usally at the south panel
 * 
 * @author Ciarán McCann
 * @since 16th Jun 2011
 *
 */
public class TileMenuView extends Composite implements TileMenuPresenter.Display {

	Canvas canvas;
	Button selectImageButton;
	
	public TileMenuView()
	{
		canvas = Canvas.createIfSupported();
		canvas.setWidth("80%");
		canvas.setHeight("100%");		
		canvas.setCoordinateSpaceHeight(canvas.getOffsetHeight());
		canvas.setCoordinateSpaceWidth(canvas.getOffsetWidth());
		
		selectImageButton = new Button("Select TitleSheet");
		initWidget(canvas);
	}

	@Override
	public Canvas getTileCanvas() {
		return canvas;
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getSelectImageButton() {
		return selectImageButton;
	}
	


}
