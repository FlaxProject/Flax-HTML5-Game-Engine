package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.ImageSelectionEvent;
import ie.flax.flaxengine.client.events.ImageSelectionEventHandler;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.TileMenuView;
import ie.flax.flaxengine.client.weave.view.Impl.TileMenuViewImpl;
import ie.flax.flaxengine.client.weave.view.customwidgets.FWindow;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * Controls the logic of the tilesheet view. It contains a canvas which displays the current tilesheet and is updated by
 * the event ImageSelection. It also has a button which when clicked displays a diolog box which allows the user
 * to select an image for the current tilesheet from the currently loaded images
 * 
 * @author Ciaran McCann
 * 
 */
public class TileMenuPresenter extends AbstractPresenter implements ImageSelectionEventHandler, TileMenuView.presenter {


	private TileMenuView display;
	private Weave model;
	private FWindow window;
	
	private AbstractPresenter imageLibPresenter;
	private FileUploadPresenter uploadPresenter;


	public TileMenuPresenter(Weave model) {
		
		this.model = model;
		this.display = new TileMenuViewImpl(this);	
		
		
		EventBus.handlerManager.addHandler(ImageSelectionEvent.TYPE, this); //Register for ImageSelection event

		window = new FWindow("Window Tile");		
		uploadPresenter = new FileUploadPresenter();
		imageLibPresenter =  new ImageLibPresenter(ImageSelectionEvent.Idenfiter.TILE_SHEET);			
	}

	
	/**
	 * Brings up a window which the upload and image libary views are inserted into
	 */
	@Override
	public void displayTileSelectionMenu()
	{
		 window.setTitle("Select TileSheet");	
		 window.add(uploadPresenter.getView());
		 window.add(imageLibPresenter.getView());
		 window.show();
	}
	
	/**
	 * Its cool, draws the rect around the tiles when you mouse over the
	 * titlesheet
	 */
	@Override
	public void onCanvasMouseMove(MouseMoveEvent event)
	{
		if (model.getFMapReference().getTileSheet() != null) {
			Context2d ctx = display.getTileCanvas().getContext2d();

			ctx.fillRect(0, 0,display.getTileCanvas().getOffsetWidth(), display.getTileCanvas().getOffsetHeight());
			ctx.drawImage(Graphic.getSingleton().getImage(model.getFMapReference().getTileSheet()),0, 0);
			int tileSize = model.getFMapReference().getTileSize();

			int x = (event.getX() / tileSize) * tileSize;
			int y = (event.getY() / tileSize) * tileSize;


			display.getTileCanvas().getContext2d().setStrokeStyle("#CD0000");
			ctx.strokeRect(x, y, tileSize, tileSize);
		}
	}
	

	@Override
	public void onImageSelection(ImageSelectionEvent e) {
		if (e.getIdenfiter() == ImageSelectionEvent.Idenfiter.TILE_SHEET) {
			
			
			ImageElement temp = Graphic.getSingleton().getImage(e.getImageUrl());			
			if (temp.getWidth() != display.getTileCanvas().getCoordinateSpaceWidth()){
				display.getTileCanvas().setWidth(temp.getWidth()+"px");
				display.getTileCanvas().setHeight(temp.getHeight()+"px");
				display.getTileCanvas().setCoordinateSpaceWidth(temp.getWidth());
				display.getTileCanvas().setCoordinateSpaceHeight(temp.getHeight());
			}
			
			display.getTileCanvas().getContext2d().fillRect(0, 0, display.getTileCanvas().getOffsetWidth(),
			display.getTileCanvas().getOffsetHeight());
			display.getTileCanvas().getContext2d().drawImage(Graphic.getSingleton().getImage(e.getImageUrl()),0, 0);
					
		}
	}

	/**
	 * This gets the tile which the user has just clicked on and sets it as the
	 * current tile to be used when tilting
	 * 
	 * @param clickX
	 * @param clickY
	 */
	@Override
	public void selectTile(int clickX, int clickY) {
		if (model.getFMapReference().getTileSheet() != null) {
			
			int tileSize = model.getFMapReference().getTileSize();
			int numberOfTilesInaRow = (Graphic.getSingleton().getImage(model.getFMapReference().getTileSheet()).getWidth())/ tileSize;

			int x = clickX / tileSize;
			int y = clickY / tileSize;

			model.getCurrentTile().setTexture((y * numberOfTilesInaRow) + x);
		}
	}



	@Override
	public Widget getView() {
		return display.asWidget();
	}

}
