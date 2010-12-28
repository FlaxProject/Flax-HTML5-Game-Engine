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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.widgetideas.graphics.client.Color;

/**
 * Weave is the editor that allows the user to create maps.
 * @author Ciarán McCann
 *
 */
public class Weave {
	
	private static FMap map;
	private weaveUi ui;
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
		ui = new weaveUi(insertID);	
		
			//Setting up click functionality for the weave tileSheet
			Graphic.getCanvas("Weave").addMouseHandler( new MouseDownHandler() {		
			@Override
			public void onMouseDown(MouseDownEvent event) {
				
				int tileSize = Weave.map.getTileSize();
				int numberOfTilesInaRow = (Graphic.getImage(map.getTileSheet()).getWidth())/tileSize;
				
				
				int x = event.getX()/tileSize;
				int y = event.getY()/tileSize;
				
				currentTile.setTexture((y*numberOfTilesInaRow)+x);
				
			}
		});
	}
	
	
	/**
	 * 
	 * @param currentMap
	 */
	public void run(FMap currentMap)
	{
		this.map = currentMap;
		ui.toggleDisplay();
		
		drawGrid();	
		
		Graphic.getCanvas("Weave").resize(Graphic.getImage(Weave.getFMapReference().getTileSheet()).getWidth(), Graphic.getImage(Weave.getFMapReference().getTileSheet()).getHeight());
		Graphic.getCanvas("Weave").drawImage(Weave.getFMapReference().getTileSheet(), 0, 0);	
	}
	
	
	/**
	 * Gets a reference to the current map object
	 * @return
	 */
	public static FMap getFMapReference()
	{
		return map;
	}
	
	/**
	 * draws a Grid over the map so its easier to edit
	 */
	private void drawGrid() {
		
	 FCanvas display = Graphic.getCanvas("Flax");
	 
		//Find the midpoints of the Canvas
		double width = display.getCoordWidth();
		double height = display.getCoordHeight();
		int tileSize = map.getTileSize();


		display.strokeRect(0, 0, height, width);
		display.strokeRect(0, width, height, width);
		display.strokeRect(height, 0, height, width);
		display.strokeRect(height, width, height, width);
		
		for (int x = 0; x < width; x += tileSize) {
			display.moveTo(x, 0);
			display.lineTo(x, height);
		}
		
		for (int y = 0; y < height; y+= tileSize) {
			display.moveTo(0, y);
			display.lineTo(width, y);
		}
		
		display.setStrokeStyle(Color.BLACK);
		display.stroke();
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
		return ui.weave.isVisible();
	}
	
	
	/**
	 * Switchs the weave UI from visible to hidden
	 */
	public void toggleDisplay()
	{
		ui.toggleDisplay();
	}
			
	
	/**
	 * Given an ID and content it inserts the content into element with provided ID
	 * To get a list of UI elements to update use UiElement.yourElementName. You should NOT raw string ID's. 
	 * @param id
	 * @param content
	 */
   public void updateElement(final String id,String content)
	{
	  ui.updateElement(id, content);
	}
	
   
   /**
	 * Given an ID and content it appends the content into element with provided ID
	 * To get a list of UI elements to update use UiElement.yourElementName. You should NOT raw string ID's. 
	 * @param id
	 * @param content
	 */
  public void appendElement(final String id,String content)
	{
	  ui.appendElement(id, content);
	}
  
  /**
   * Given an ID it inserts a widget 
   * To get a list of UI elements to update use UiElement.yourElementName. You should NOT raw string ID's. 
   * @param UiElement
   * @param widget
   */
   public void insertWidget(final String UiElement, Widget widget)
   {
	   ui.insertWidget(UiElement, widget);
   }

   
   /**
    * Defines the actions the editor will take when a mouse click is registered
    * @param event
    */
   public void onClick(ClickEvent event) {
	  selectedTile(event.getX(), event.getY());	
   }

   /**
    * Defines the actions the editor will take when the key is pressed
    * @param event
    */
   public void onKeyDown(KeyDownEvent event) {
	
	 ui.onKeyDown(event);
	
   }
	
	
	
	
	
   
   

}
