
package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.weave.view.MiniMapView;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.attachment.AttachmentHandler;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MiniMapViewImpl implements MiniMapView {
	private final Canvas minimap;
	private final MiniMapView.presenter presenter;
	private AbsolutePanel abp;
	private HTML warning = new HTML("<h3>Click to turn on minimap!</h3><p>(May hurt performance)</p>");

	public MiniMapViewImpl(MiniMapView.presenter pres) {
		presenter = pres;
		
		abp = new AbsolutePanel();
		
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
				if(event.getRelativeY(abp.getElement()) > abp.getOffsetHeight() - 50){
				if(!presenter.isRunning()){
					
					presenter.setRunning(true);
					hideWarning();
				} else {
					presenter.setRunning(false);
					showWarning();
				}
				} else if (presenter.isRunning()){
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
		
		abp.add(minimap);
		abp.setSize("100%", "100%");
	}

	private void showWarning() {
		warning.getElement().getStyle().setBackgroundColor("rgba(255, 255, 255, 0.7) !important;");
		abp.add(warning,0,0);
		
	}
	
	private void hideWarning() {
		abp.getElement().getStyle().setBackgroundColor("");
		abp.remove(warning);
		
	}

	@Override
	public Canvas getCanvas() {
		return minimap;
	}

	@Override
	public Widget getWidget() {
		return abp;
	}

	
}
