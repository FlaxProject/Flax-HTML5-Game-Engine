package ie.flax.flaxengine.client.weave;
import ie.flax.flaxengine.client.FCanvas;
import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FTile;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.widgetideas.graphics.client.Color;

import ie.flax.flaxengine.client.weave.presenter.*;
import ie.flax.flaxengine.client.weave.view.TileMenuView;

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
public class Weave {
	
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
		SimplePanel t = new SimplePanel();
		
		AbstractPresenter presenter = new TileMenuPresenter(new TileMenuView(), this); 
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
		
		FCanvas display = Graphic.getSingleton().getCanvas("Flax");
	 
		//Find the midpoints of the Canvas
		double width = display.getCanvas().getCoordinateSpaceWidth();
		double height = display.getCanvas().getCoordinateSpaceHeight();
		int tileSize = map.getTileSize();

		display.drawGrid(width,height,tileSize);
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
			

}
