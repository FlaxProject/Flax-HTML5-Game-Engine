package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.Graphic;
import ie.flax.flaxengine.client.weave.Weave;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class TileMenuPresenter extends AbstractPresenter{

	private Display display;
	private Weave model;
		
	public interface Display {
		HasClickHandlers getTileCanvas();	
		Widget asWidget();
	}
	
	
	public TileMenuPresenter(Display display, Weave model)
	{
		this.model = model;
		this.display = display;
		
		
		Graphic.getSingleton().getCanvas("TileCanvas").drawImage("http://flax.ie/test/p.png", 0, 0);
	}
	
	
	@Override
	public void bind() {
		
		display.getTileCanvas().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Click on canvas");				
				//selectTile(event.getX(),event.getY());
			}
		});
		
	}

	
	private void selectTile(int clickX, int clickY)
	{
		int tileSize = model.getFMapReference().getTileSize();
		int numberOfTilesInaRow = (Graphic.getSingleton().getImage(model.getFMapReference().getTileSheet()).getWidth())/tileSize;
		
		
		int x = clickX/tileSize;
		int y = clickY/tileSize;
		
		model.getCurrentTile().setTexture((y*numberOfTilesInaRow)+x);
	}
	
	@Override
	public void go(final HasWidgets ContainerElement) {
		bind();
		ContainerElement.add(display.asWidget());
		
	}

}
