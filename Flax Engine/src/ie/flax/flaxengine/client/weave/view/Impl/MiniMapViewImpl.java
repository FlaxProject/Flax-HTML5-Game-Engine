package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.weave.view.MiniMapView;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

public class MiniMapViewImpl implements MiniMapView {
    private final Canvas minimap;
    private final MiniMapView.presenter presenter;

    public MiniMapViewImpl(MiniMapView.presenter pres) {
        presenter = pres;
        // create the canvas here
        minimap = Canvas.createIfSupported();
        minimap.setSize("100%", "100%");
        minimap.setCoordinateSpaceHeight(1500);
        minimap.setCoordinateSpaceWidth(1500);

        minimap.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                presenter.moveMapCamera(
                        event.getRelativeX(minimap.getElement()),
                        event.getRelativeY(minimap.getElement()));
            }
        });
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
