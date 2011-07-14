package ie.flax.flaxengine.client.weave;
import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FTile;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.ImageSelectionEvent;
import ie.flax.flaxengine.client.events.ImageSelectionEventHandler;
import ie.flax.flaxengine.client.weave.controls.TileRegion;
import ie.flax.flaxengine.client.weave.controls.TileRegion.MouseState;
import ie.flax.flaxengine.client.weave.presenter.WeavePresenter;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
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
	
	private final int cameraPanSpeed = 32;
	
	private FMap map;
	private FTile currentTile;
	private Canvas drawingSpace;
	private boolean running;
	private final WeavePresenter WeavePresenter;
	
	private TileRegion tileRegion;
	private Canvas editorOverLay;

	
	/**
	 * This construct takes in the width and height of the canvas. It then inserts the panel of into the element 
	 * which you have provided the ID to.
	 * @param insertID
	 * @param map 
	 * @param drawingSpace 
	 * @param editorOverLay 
	 * @param width
	 * @param height
	 */
	public Weave(final String insertID, Canvas drawingSpace, Canvas editorOverLay, FMap map)
	{ 
		this.editorOverLay = editorOverLay;
		this.drawingSpace = drawingSpace;
		this.map = map;
		this.currentTile = new FTile();	
		
		//Controls the select region of tiles operations
		tileRegion = new TileRegion(this);
	

		WeavePresenter = new WeavePresenter(this); 
		//RootPanel.get(insertID).add(WeavePresenter.getView());
		
		EventBus.handlerManager.addHandler(ImageSelectionEvent.TYPE, this); //Register to listen for event
		
		bind(); //Key and Mouse Events
	}
		
	/**
	 * This binds the goble key events for the editor, such as the backslash.
	 * Though in furture the canvas keyevents may be changed to goble rootpanel events and thus
	 * this method will handle all them as well.
	 */
	public void bind()
	{
				
		/**
		 * Editor Camera Moving
		 */
		KeyDownHandler keyDownHandle = new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				
				if(running)
				{
					/*
					 * The event.preventDefault() is to avoid anything else scrolling with the camera.
					 */
					if(event.isUpArrow()){
						FlaxEngine.camera.incermentY(-cameraPanSpeed);		    
						event.preventDefault();
					}
					if(event.isDownArrow()){
						FlaxEngine.camera.incermentY(cameraPanSpeed);
						event.preventDefault();
					}
					if(event.isLeftArrow()){
						FlaxEngine.camera.incermentX(-cameraPanSpeed);
						event.preventDefault();
					}
					if(event.isRightArrow()){
						FlaxEngine.camera.incermentX(cameraPanSpeed);
						event.preventDefault();
					}
										
				}
			}
		};
			
		
		/**
		 * Toggle Editor
		 */
		KeyPressHandler keyPressHandle = new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
					
				 if(event.getNativeEvent().getKeyCode() == 92)
				 {
					 WeavePresenter.toggleDisplay();
					 running = !running;
				 }				
			}
		};		
		
		/**
		 * RootPanel  
		 */
		RootPanel.get().addDomHandler(keyPressHandle, KeyPressEvent.getType());
		RootPanel.get().addDomHandler(keyDownHandle, KeyDownEvent.getType());
	}
	
	
	/**
	 * draws a Grid over the map so its easier to edit
	 * FIXME: last time I tried this frame rate was killed, look into in furture.
	 */
	public void drawGrid() {
	 
		//Find the midpoints of the Canvas
		double width = drawingSpace.getCoordinateSpaceWidth();
		double height =drawingSpace.getCoordinateSpaceHeight();
		int tileSize = map.getTileSize();
		Graphic.getSingleton().drawGrid(width,height,tileSize,drawingSpace);
	}


	/**
	 * Finds the tile the user clicked on, if there is not one there it will create one with the currrent tile info
	 * and add it to the map. It then retuns the tile
	 * @param x
	 * @param y
	 */
	public FTile selectedTile(int x, int y) 
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
			tile = new FTile(tX*tileSize,  tY*tileSize, false, currentTile.getTexture()) ;
			map.addTile(tile );//TODO refactor into more generic
		}
		
		return tile;
	}
		

	/**
	 * While the editor is active there will be certain keys which will do stuff, this method binds those keys to functionality
	 * @param event
	 */
	public void keyboardControls(KeyDownEvent event)
	{
				//Will be used in future, so don't remove me or I will slap you!		
	}
	
	/**
	 * The Editor does certain things on mouse move, like tiling etc
	 * @param event
	 */
	public void onMouseMove(MouseMoveEvent event)
	{
		
		if (this.isRunning()) {
			
			if (event.isShiftKeyDown() && tileRegion.getMouseState() == MouseState.MOUSE_DOWN)
			{						
				//tileRegion.onMouseMove(event);				
			}
			else if(event.isShiftKeyDown())
			{
				this.selectedTile(event.getX(), event.getY());
			}
		}			
	}
	
	
	public void onMouseDown(MouseDownEvent event)
	{		
		/*
		 * I know, preventDefault is bad in general, but it's
		 * necessary here. This is to stop the selection happening (of widgets etc).
		 */
		event.preventDefault();
		
		if (event.isShiftKeyDown())
		{				
			//tileRegion.onMouseDown(event);	
		}	
		else
		{
			this.selectedTile(event.getX(), event.getY());
		}
		
	}
	
	public void onMouseUp(MouseUpEvent event) {	
		
		if (tileRegion.getMouseState() == MouseState.MOUSE_DOWN)
		{		
			//tileRegion.onMouseUp(event);		
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


	public Canvas getdrawingSpace() {return drawingSpace;}
	public final FTile getCurrentTile(){return currentTile;}		
	public final FMap getFMapReference(){return map;}
	
	/**
	 * Is weave running or not
	 * @return - true or false
	 */
	public boolean isRunning() {
		return running;
	}
	
	
	public final Canvas getEditorOverLay() {
		return editorOverLay;
	}

	public void toggle() {
		running = !running;
		WeavePresenter.toggleDisplay();
	}



	
}
