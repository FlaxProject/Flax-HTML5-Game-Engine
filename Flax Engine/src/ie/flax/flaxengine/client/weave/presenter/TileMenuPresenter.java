package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.Graphic;
import ie.flax.flaxengine.client.weave.Weave;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * Controls the logic of the tilesheet view
 * 
 * @author Ciarán McCann
 *
 */
public class TileMenuPresenter extends AbstractPresenter{

	private Display display;
	private Weave model;

		
	public interface Display {
		Canvas getTileCanvas();	//MVP - I'm sorry buts its never not going to be a canvas
		HasClickHandlers getSelectImageButton();
		Widget asWidget();
	}
	
	
	public TileMenuPresenter(Display display, Weave model)
	{
		this.model = model;
		this.display = display;		
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
		
		display.getSelectImageButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Select Button");
				
			}
		});
		
		/**
		 * Its cool, draws the rect around the tiles when you mouse over the titlesheet
		 */
		display.getTileCanvas().addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				
				if(model.getFMapReference().getTileSheet() != null)
				{
				
					int tileSize = model.getFMapReference().getTileSize();
					int numberOfTilesInaRow = (Graphic.getSingleton().getImage(model.getFMapReference().getTileSheet()).getWidth())/tileSize;
						
					int x = (event.getX()/tileSize)*tileSize;
					int y = (event.getY()/tileSize)*tileSize;
				
					display.getTileCanvas().getContext2d().fillRect(x, y, tileSize, tileSize);
				}
				
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
