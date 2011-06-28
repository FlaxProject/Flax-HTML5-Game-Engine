package ie.flax.flaxengine.client.weave;
import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FTile;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.ImageSelectionEvent;
import ie.flax.flaxengine.client.events.ImageSelectionEventHandler;
import ie.flax.flaxengine.client.weave.presenter.AbstractPresenter;
import ie.flax.flaxengine.client.weave.presenter.WeavePresenter;
import ie.flax.flaxengine.client.weave.view.WeaveView;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This is the main class of the weave editor. 
 * The structure of the editor goes like this
 * 
 * ---WeavePresenter ---- creates WeaveView (Which is the three slide in panels)
 * ---TileMenuPresenter -- creates TileMenuView (which is inserted into the south panel)
 * 
 * TODO finish comment
 * 
 * @author Ciaran McCann
 *
 */
public class Weave implements ImageSelectionEventHandler{
	
	private FMap map;
	private FTile currentTile;
	private Canvas drawingSpace;
	private boolean running;
	
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
		final AbstractPresenter presenter = new WeavePresenter(new WeaveView(), this, this.map); 
		presenter.go(RootPanel.get(insertID));
		
		EventBus.handlerManager.addHandler(ImageSelectionEvent.TYPE, this);
		
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
	 * @param drawingSpace2 
	 * @param currentMap
	 */
	public void run(Canvas drawingSpace2, FMap currentMap)
	{
		this.map = currentMap;
		this.drawingSpace = drawingSpace2;
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
	 
		//Find the midpoints of the Canvas
		double width = drawingSpace.getCoordinateSpaceWidth();
		double height =drawingSpace.getCoordinateSpaceHeight();
		int tileSize = map.getTileSize();
		Graphic.getSingleton().drawGrid(width,height,tileSize,drawingSpace);
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
			map.addTile( new FTile(tX*tileSize,  tY*tileSize, false, currentTile.getTexture())  );
		}
	}
	
	/**
	 * Is weave running or not
	 * @return - true or false
	 */
	public boolean isRunning() {
		return true;
	}
	

	/**
	 * While the editor is active there will be certain keys which will do stuff, this method binds those keys to functionality
	 * @param event
	 */
	public void keyboardControls(KeyDownEvent event)
	{
		
		if(event.isUpArrow())
			FlaxEngine.camera.incermentY(-5);		    
		
		if(event.isDownArrow())
			FlaxEngine.camera.incermentY(5);
		
		if(event.isLeftArrow())
			FlaxEngine.camera.incermentX(-5);
		
		
		if(event.isRightArrow())
			FlaxEngine.camera.incermentX(5);

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

	
	/**
	 * When a new tilesheet is selected the weave object is updated
	 */
	@Override
	public void onImageSelection(ImageSelectionEvent e) {
		
		if(e.getIdenfiter() == ImageSelectionEvent.Idenfiter.TILE_SHEET)
		{
			map.setTileSheet(e.getImageUrl());
		}
		
	}

}
