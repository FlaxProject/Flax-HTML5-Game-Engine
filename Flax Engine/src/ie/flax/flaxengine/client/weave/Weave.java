package ie.flax.flaxengine.client.weave;
import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FTile;
import ie.flax.flaxengine.client.FileHandle;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.events.CameraUpdateEvent;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.ImageSelectionEvent;
import ie.flax.flaxengine.client.events.ImageSelectionEventHandler;
import ie.flax.flaxengine.client.events.MapUpdateEvent;
import ie.flax.flaxengine.client.weave.controls.TileRegion;
import ie.flax.flaxengine.client.weave.controls.TileRegion.MouseState;
import ie.flax.flaxengine.client.weave.presenter.WeavePresenter;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
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

	private HTML fpsHtml;
	
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
		
		fpsHtml = new HTML();
		fpsHtml.setStyleName("fpsWidget");
		FlaxEngine.settings.getContainer().add(fpsHtml,10,30);
		
		
		//Controls the select region of tiles operations
		tileRegion = new TileRegion(this);
	

		WeavePresenter = new WeavePresenter(this); 
		//RootPanel.get(insertID).add(WeavePresenter.getView());
		
		EventBus.handlerManager.addHandler(ImageSelectionEvent.TYPE, this); //Register to listen for event
		
		bind(); //Key and Mouse Events
	}
	
	public void localStoreCurrentMap() {
		FileHandle.writeStringToLocalStorage("map", FMap.toJson(getFMapReference()));
	}
	
	/**
	 * This binds the global key events for the editor, such as the backslash.
	 * Though in future the canvas keyevents may be changed to global rootpanel events and thus
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
						FlaxEngine.camera.incrementY(-cameraPanSpeed);		    
						event.preventDefault();
					}
					else if(event.isDownArrow()){
						FlaxEngine.camera.incrementY(cameraPanSpeed);
						event.preventDefault();
					}
					else if(event.isLeftArrow()){
						FlaxEngine.camera.incrementX(-cameraPanSpeed);
						event.preventDefault();
					}
					else if(event.isRightArrow()){
						FlaxEngine.camera.incrementX(cameraPanSpeed);
						event.preventDefault();
					}
										
				}
			}
		};
		
		KeyUpHandler keyUpHandle = new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(event.isUpArrow()){
					event.preventDefault();
					EventBus.handlerManager.fireEvent(new CameraUpdateEvent()); 
				}
				if(event.isDownArrow()){
					event.preventDefault();
					EventBus.handlerManager.fireEvent(new CameraUpdateEvent()); 
				}
				if(event.isLeftArrow()){
					event.preventDefault();
					EventBus.handlerManager.fireEvent(new CameraUpdateEvent()); 
				}
				if(event.isRightArrow()){
					event.preventDefault();
					EventBus.handlerManager.fireEvent(new CameraUpdateEvent()); 
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
		RootPanel.get().addDomHandler(keyUpHandle, KeyUpEvent.getType());
	}
	
	
	/**
	 * draws a Grid over the map so its easier to edit
	 * FIXME: last time I tried this frame rate was killed, look into in future.
	 */
	public void drawGrid() {
	 
		//Find the midpoints of the Canvas
		double width = drawingSpace.getCoordinateSpaceWidth();
		double height =drawingSpace.getCoordinateSpaceHeight();
		int tileSize = map.getTileSize();
		Graphic.getSingleton().drawGrid(width,height,tileSize,drawingSpace);
	}


	/**
	 * Sets the texture of the tile at location (x, y)
	 * @param x
	 * @param y
	 */
	public void textureTileAt(int x, int y){		
		
		FTile temp =  map.getTile(x, y);		
		temp.setTextureX(currentTile.getTextureX());	
		temp.setTextureY(currentTile.getTextureY());
	}
		

	/**
	 * While the editor is active there will be certain keys which will do stuff, this method binds those keys to functionality
	 * @param event
	 */
	public void keyboardControls(KeyDownEvent event){
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
				tileRegion.onMouseMove(event);
			}
			else if(event.isShiftKeyDown())
			{
				this.textureTileAt(event.getX(), event.getY());
				EventBus.handlerManager.fireEvent(new MapUpdateEvent());
			}
		}			
	}
	
	
	public void onMouseDown(MouseDownEvent event)
	{		
		if (event.isShiftKeyDown())
		{				
			tileRegion.onMouseDown(event);	
		}else{
			this.textureTileAt(event.getX(), event.getY());
		}
		
	}
	
	public void onMouseUp(MouseUpEvent event) {	
		
		if (tileRegion.getMouseState() == MouseState.MOUSE_DOWN)
		{		
			tileRegion.onMouseUp(event);
		}

		EventBus.handlerManager.fireEvent(new MapUpdateEvent());
	}


	
	/**
	 * When a new tilesheet is selected the weave object is updated
	 */
	@Override
	public void onImageSelection(ImageSelectionEvent e) {
		
		if(e.getIdenfiter() == ImageSelectionEvent.Identifier.TILE_SHEET)
		{
			map.setTileSheet(e.getImageUrl());
		}
		
	}


	public final Canvas getdrawingSpace() {return drawingSpace;}
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

	public void updateFps(int frameCount) {
		fpsHtml.setText("FPS: "+frameCount);
	}

	/**
	 * Sets the current map which the editor works on
	 * @param map
	 */
	public void setFMapReference(FMap map) {
		this.map = map;		
	}
}
