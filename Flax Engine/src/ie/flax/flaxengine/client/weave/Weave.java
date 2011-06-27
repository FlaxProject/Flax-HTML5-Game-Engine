package ie.flax.flaxengine.client.weave;
import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FTile;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.events.ImageSelectionEvent;
import ie.flax.flaxengine.client.events.ImageSelectionEventHandler;
import ie.flax.flaxengine.client.weave.presenter.AbstractPresenter;
import ie.flax.flaxengine.client.weave.presenter.WeavePresenter;
import ie.flax.flaxengine.client.weave.view.WeaveView;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * TODO: Weave as a whole is still quite messy as I have been expierementing with GWT and how to build interfaces
 * and modify data based on DOM events from those UI elements. So I have not defines a stardard why of adding Ui compenets.
 * so the code is some what messy. Though it works fine and for the amount I am happy to leave it, it can all be easliy refactored 
 * when I don't have more pressing features to implement.
 */

/**
 * Weave is the editor that allows the user to create maps.
 * @author Ciarán McCann
 *
 */
public class Weave implements ImageSelectionEventHandler{
	
	private FMap map;
	private FTile currentTile;
	
	/**
	 * This construct takes in the width and height of the canvas. It then inserts the panel of into the element 
	 * which you have provided the ID to.
	 * @param insertID
	 * @param width
	 * @param height
	 */
	public Weave(String insertID)
	{ 
		currentTile = new FTile();		

		PopupPanel t = new PopupPanel();
		AbstractPresenter presenter = new WeavePresenter(new WeaveView(), this, this.map); 
		presenter.go(RootPanel.get(insertID));
		

	}
	
	/**
	 * Gets the current tile
	 * @return
	 */
	public FTile getCurrentTile()
	{
		return currentTile;
	}
	
	/**
	 * Runs the editor
	 * @param currentMap
	 */
	public void run(FMap currentMap)
	{
		this.map = currentMap;
		//ui.toggleDisplay();
				
		//Graphic.getSingleton().getCanvas("Weave").getCanvas().getContext2d().resize(Graphic.getSingleton().getImage(Weave.getFMapReference().getTileSheet()).getWidth(), Graphic.getSingleton().getImage(Weave.getFMapReference().getTileSheet()).getHeight());
		//Graphic.getSingleton().getCanvas("Weave").drawImage(Weave.getFMapReference().getTileSheet(), 0, 0);	
	}
	
	
	/**
	 * Gets a reference to the current map object
	 * @return
	 */
	public FMap getFMapReference()
	{
		return map;
	}
	
	/**
	 * draws a Grid over the map so its easier to edit
	 */
	public void drawGrid() {
		
		Canvas display = Graphic.getSingleton().getCanvas("Flax");
	 
		//Find the midpoints of the Canvas
		double width = display.getCoordinateSpaceWidth();
		double height = display.getCoordinateSpaceHeight();
		int tileSize = map.getTileSize();

		Graphic.getSingleton().drawGrid(width,height,tileSize,display);
	}


	/**
	 * Finds the tile the user clicked on
	 * @param x
	 * @param y
	 */
	public void selectedTile(int x, int y)
	{
		FTile tile = map.getTile(x, y);
		
		if(tile != null) 
		{
			tile.setTexture(currentTile.getTexture());	
		}
		else
		{
			int tileSize = map.getTileSize();
			int tX = (int) ((x+FlaxEngine.camera.getX())/tileSize);
			int tY = (int) ((y+FlaxEngine.camera.getY())/tileSize);
			
			//FLog.debug("New tile Object created at x=" + tX + " y= "+ tY +" with currentTile " );
			
			map.addTile( new FTile(tX*tileSize,  tY*tileSize, false, currentTile.getTexture())  );
		}
	}
	
	/**
	 * Is weave running or not
	 * @return - true or false
	 */
	public boolean isRunning() {
		return false;//ui.weaveUIdiv.isVisible();
	}
	
	
	/**
	 * Switchs the weave UI from visible to hidden
	 */
	public void toggleDisplay()
	{
		//ui.toggleDisplay();
	}
		
	/**
	 * While the editor is active there will be certain keys which will do stuff, this method binds those keys to functionality
	 * @param event
	 */
	public void keyboardControls(KeyDownEvent event)
	{
		/*
		if(event.isUpArrow())
		{
			this.getCurrentMap().getEntity(0).setY(getCurrentMap().getEntity(0).getY()-3);
			camera.incermentY(-5);
		    
		}
		
		if(event.isDownArrow())
		{
		   getCurrentMap().getEntity(0).setY(getCurrentMap().getEntity(0).getY()+3);
			camera.incermentY(5);
		}
		
		if(event.isLeftArrow())
		{
		  getCurrentMap().getEntity(0).setX(getCurrentMap().getEntity(0).getX()-3);
		  camera.incermentX(-5);
		}
		
		if(event.isRightArrow())
		{
			getCurrentMap().getEntity(0).setX(getCurrentMap().getEntity(0).getX()+3);
			camera.incermentX(5);
		}
		*/
	}
	
	/**
	 * The Editor does certain things on mouse move, like tiling etc
	 * @param event
	 */
	public void onMouseMove(MouseMoveEvent event)
	{

		if (this.isRunning()) {
			if (event.isShiftKeyDown())
				this.selectedTile(event.getX(), event.getY());
		}
	}

	@Override
	public void onImageSelection(ImageSelectionEvent e) {
		
		if(e.getIdenfiter() == ImageSelectionEvent.Idenfiter.TILE_SHEET)
		{
			map.setTileSheet(e.getImageUrl());
		}
		
	}

}
