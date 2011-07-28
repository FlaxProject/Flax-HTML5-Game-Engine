
package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.weave.view.MiniMapView;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.attachment.AttachmentHandler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MiniMapViewImpl implements MiniMapView {
	private final Canvas minimap;
	private final MiniMapView.presenter presenter;

	public MiniMapViewImpl(MiniMapView.presenter pres) {
		presenter = pres;
		// create the canvas here
		minimap = Canvas.createIfSupported();
		minimap.setSize("100%", "100%");
		minimap.setCoordinateSpaceHeight(minimap.getCanvasElement()
				.getClientHeight());
		minimap.setCoordinateSpaceWidth(minimap.getCanvasElement()
				.getClientWidth());
		
		minimap.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(!presenter.isRunning()){
					presenter.setRunning(true);
					hideWarning();
				} else {
					presenter.moveMapCamera(
						event.getRelativeX(minimap.getElement()),
						event.getRelativeY(minimap.getElement()));
				}
			}

			
		});
		
		minimap.addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				//event.preventDefault();
				//event.stopPropagation();
				//presenter.drawCameraRectangle(event.getRelativeX(minimap.getElement()),
				//		event.getRelativeY(minimap.getElement()));
			}
		});
		
		minimap.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				event.preventDefault();
			}
		});
		
		this.showWarning();
	}

	private void showWarning() {
		minimap.getElement().getStyle().setBackgroundColor("rgba(110, 110, 110, 0.7) !important;");
	}
	
	private void hideWarning() {
		//minimap.getElement().setAttribute("style", "");
		
	}

	@Override
	public Canvas getCanvas() {
		return minimap;
	}

	@Override
	public Widget getWidget() {
		return minimap;
	}

	
}
