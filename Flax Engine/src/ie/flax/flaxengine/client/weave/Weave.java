package ie.flax.flaxengine.client.weave;
import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FTile;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.ImageSelectionEvent;
import ie.flax.flaxengine.client.events.ImageSelectionEventHandler;
import ie.flax.flaxengine.client.weave.controls.TileRegion;
import ie.flax.flaxengine.client.weave.presenter.WeavePresenter;
import ie.flax.flaxengine.client.weave.view.Impl.WeaveViewImpl;

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
	private final TileRegion tileRegionControl;

	
	/**
	 * This construct takes in the width and height of the canvas. It then inserts the panel of into the element 
	 * which you have provided the ID to.
	 * @param insertID
	 * @param map 
	 * @param drawingSpace 
	 * @param width
	 * @param height
	 */
	public Weave(final String insertID, Canvas drawingSpace, FMap map)
	{ 
		this.drawingSpace = drawingSpace;
		this.map = map;
		this.currentTile = new FTile();	
		
		//Controls the select region of tiles operations
		tileRegionControl = new TileRegion(this) {
			
			@Override
			public void doTileRegionLogic(int startX, int startY) {
				
				this.tileSelectedRegion(startX, startY);
				
			}
		};
	

		WeavePresenter = new WeavePresenter(new WeaveViewImpl(), this); 
		WeavePresenter.go(RootPanel.get(insertID));
		
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
					//TODO bug, when the camera moves up so does the tilesheet panel
					//event.preventDefault();
					
					if(event.isUpArrow())
						FlaxEngine.camera.incermentY(-cameraPanSpeed);		    
					
					if(event.isDownArrow())
						FlaxEngine.camera.incermentY(cameraPanSpeed);
					
					if(event.isLeftArrow())
						FlaxEngine.camera.incermentX(-cameraPanSpeed);
					
					
					if(event.isRightArrow())
						FlaxEngine.camera.incermentX(cameraPanSpeed);
										
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
			
			
			tileRegionControl.onMouseMove(event);
			
			/*
						
			if (event.isShiftKeyDown())
			{

				if(mouseState == MouseState.MOUSE_DOWN)
				{
										
					//getTilesInRegion

												
					drawRegionBox(event.getClientX(),event.getClientY());
					
	
				}
				else
				{
				
				this.selectedTile(event.getX(), event.getY());
				}
			}
			
			*/
		}
	}
	
	

	public void onMouseDown(MouseDownEvent event)
	{
				
		tileRegionControl.onMouseDown(event);
		
	}
	
	public void onMouseUp(MouseUpEvent event) {
		
		tileRegionControl.onMouseUp(event);
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
	
}
