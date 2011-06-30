package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.ImageSelectionEvent;
import ie.flax.flaxengine.client.events.ImageSelectionEventHandler;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.customwidgets.FWindow;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * Controls the logic of the tilesheet view
 * 
 * @author Ciaran McCann
 *
 */
public class TileMenuPresenter extends AbstractPresenter implements ImageSelectionEventHandler{

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
		EventBus.handlerManager.addHandler(ImageSelectionEvent.TYPE, this);
		bind();
	}
	
	
	@Override
	public void bind() {
		
		
		display.getTileCanvas().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {		
				selectTile(event.getX(),event.getY());
			}
		});
		
		display.getSelectImageButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				AbstractPresenter imageLibPresenter = new ImageLibPresenter(ImageSelectionEvent.Idenfiter.TILE_SHEET);
				FWindow window = new FWindow("Select TileSheet");
				imageLibPresenter.go(window.asWdidget());
							
				
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
					Context2d ctx = display.getTileCanvas().getContext2d();
					
					
					ctx.fillRect(0, 0, display.getTileCanvas().getOffsetWidth(), display.getTileCanvas().getOffsetHeight());
					ctx.drawImage(Graphic.getSingleton().getImage(model.getFMapReference().getTileSheet()), 0, 0);
					int tileSize = 32;//TODO change back once canvas back online model.getFMapReference().getTileSize();
					int tileSheetWidth = Graphic.getSingleton().getImage(model.getFMapReference().getTileSheet()).getWidth();
			
						
					int x = (event.getX()/tileSize)*tileSize;
					int y = (event.getY()/tileSize)*tileSize;
								
					
					ctx.setStrokeStyle("#CD0000");
					ctx.beginPath();
					ctx.moveTo(x, y);
					ctx.lineTo(x+tileSize, y);
					ctx.lineTo(x+tileSize, y+tileSize);
					ctx.lineTo(x, y+tileSize);
					ctx.closePath();
					ctx.stroke();
				}
				
			}
		});
		
	}

	/**
	 * This gets the tile which the user has just clicked on and sets it as the current tile to be used when tilting
	 * @param clickX
	 * @param clickY
	 */
	private void selectTile(int clickX, int clickY)
	{
		if(model.getFMapReference().getTileSheet() != null)
		{
			int tileSize = model.getFMapReference().getTileSize();
			int numberOfTilesInaRow = (Graphic.getSingleton().getImage(model.getFMapReference().getTileSheet()).getWidth())/tileSize;
					
			int x = clickX/tileSize;
			int y = clickY/tileSize;
			
			model.getCurrentTile().setTexture((y*numberOfTilesInaRow)+x);
		}
	}
	
	@Deprecated
	public void go(final HasWidgets ContainerElement) {
		
		ContainerElement.add(display.asWidget());
		
	}
	
	//TODO This method should be in abstractPresenter
	public Widget asWidget()
	{
		return display.asWidget();
	}


	@Override
	public void onImageSelection(ImageSelectionEvent e) {
		if(e.getIdenfiter() == ImageSelectionEvent.Idenfiter.TILE_SHEET)
		{
			display.getTileCanvas().getContext2d().fillRect(0, 0, display.getTileCanvas().getOffsetWidth(), display.getTileCanvas().getOffsetHeight());
			display.getTileCanvas().getContext2d().drawImage(Graphic.getSingleton().getImage(e.getImageUrl()), 0, 0);
		}
		
		
	}

}
