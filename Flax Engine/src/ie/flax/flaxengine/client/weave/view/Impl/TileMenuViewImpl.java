package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.weave.view.TileMenuView;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class TileMenuViewImpl extends Composite implements TileMenuView {

	private Canvas canvas;
	private Button selectImageButton;
	private TileMenuView.presenter presenter;
	
	public TileMenuViewImpl( final TileMenuView.presenter presenter)
	{
		this.presenter = presenter;
		
		canvas = Canvas.createIfSupported();
		canvas.setWidth("1000px");
		canvas.setHeight("1000px");		
		canvas.setCoordinateSpaceHeight(1000);
		canvas.setCoordinateSpaceWidth(1000);
				
		selectImageButton = new Button("Select TitleSheet");			
		
		ScrollPanel canvasScrollPanel = new ScrollPanel(canvas);
		canvasScrollPanel.setWidth("100%");
		canvasScrollPanel.setHeight("135px");
		HorizontalPanel panel = new HorizontalPanel();
		
		panel.add(selectImageButton);
		panel.add(canvasScrollPanel);
		
		initWidget(panel);
		selectImageButton.setVisible(true);
		
		
		canvas.addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				presenter.onCanvasMouseMove(event);
				
			}
		});
		
		selectImageButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				presenter.displayTileSelectionMenu();
				
			}
		});
		
		canvas.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				presenter.selectTexture(event.getX(),event.getY());
				
			}
		});
	}

	@Override
	public Canvas getTileCanvas() {
		return canvas;
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

}
