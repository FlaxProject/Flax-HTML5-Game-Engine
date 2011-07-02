package ie.flax.flaxengine.client.weave.controls;

import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FTile;
import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.FlaxEngine;
import ie.flax.flaxengine.client.weave.Weave;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;

/**
 * This defines a generic class which allows for TileRegion operations
 * 
 * @author Ciaran McCann
 * 
 *
 */
public class TileRegion {

	private enum MouseState
	{
		MOUSE_CLICKED,
		MOUSE_DOWN,
		MOUSE_UP,
	}
	
	private FVector startPos;
	private MouseState mouseState;
	private Weave editor;
	private FTile tile;
	private int tilesize;
	private FMap map;
	private int texture;

	
	public TileRegion(Weave editor)
	{
		startPos = new FVector(0, 0);
		mouseState = MouseState.MOUSE_UP;
		this.editor = editor;
		tile = null;	
		map = editor.getFMapReference();
		texture = editor.getCurrentTile().getTexture();
	}


	public void onMouseUp(MouseUpEvent event) {
		
		if(mouseState == MouseState.MOUSE_DOWN)
		{

			int newX =  (int) (FlaxEngine.camera.getX() + event.getClientX())/tilesize ;
			int newY =  (int) (FlaxEngine.camera.getY() + event.getClientY())/tilesize ;
			
			int startX = (int) ((int) FlaxEngine.camera.getX()/tilesize + startPos.x/tilesize);
			int startY = (int) ((int) FlaxEngine.camera.getY()/tilesize + startPos.y/tilesize);
			
			
			int startXCopy = startX;
			
			while(startY <= newY)
			{
				while(startX <= newX)
				{
									 
					tileSelectedRegion(startX, startY);
					startX++;		
				}
				startX = startXCopy;
				startY++;
			}
			
			mouseState = MouseState.MOUSE_UP;
			editor.getEditorOverLay().getContext2d().clearRect(0, 0, editor.getEditorOverLay().getOffsetWidth(), editor.getEditorOverLay().getOffsetHeight()); 
			map.optimizeCollections();
		}
		
	}


	public void onMouseDown(MouseDownEvent event) {
		
		//event.stopPropagation();
		
		if (event.isShiftKeyDown())
		{			
			tilesize = editor.getFMapReference().getTileSize();
			map = editor.getFMapReference();
			mouseState = MouseState.MOUSE_DOWN;				
			startPos = new FVector(event.getX(), event.getY());
			texture = editor.getCurrentTile().getTexture();
			
		}
		
		
	}

	
	public void onMouseMove(MouseMoveEvent event) {
		
		if (event.isShiftKeyDown()) {
			
			if (mouseState == MouseState.MOUSE_DOWN) {
				drawRegionBox(event.getClientX(), event.getClientY());
			}
		 
		}
		
	}


	private void drawRegionBox(int clientX, int clientY) {
		
		Context2d ctx = editor.getEditorOverLay().getContext2d();	
		ctx.clearRect(0, 0, editor.getEditorOverLay().getOffsetWidth(), editor.getEditorOverLay().getOffsetHeight()); 
		
		double newX =  clientX - startPos.x;
		double newY =  clientY - startPos.y;				
		
		ctx.setStrokeStyle("#CD0000");
		ctx.beginPath();
		
		ctx.moveTo(startPos.x, startPos.y);
		ctx.lineTo(startPos.x+newX, startPos.y);
		
		ctx.lineTo(startPos.x+newX, startPos.y+newY);
		ctx.lineTo(startPos.x, startPos.y+newY);
		
		ctx.closePath();
		ctx.stroke();
		
	}

	
	private void tileSelectedRegion(int startX, int startY)
	{	
		startX *= tilesize;
		startY *= tilesize;
		
		tile = map.getTile(startX,startY);
		
		if(tile == null)
		{		
			map.addTile(new FTile(startX,startY, true, texture));		
		}
		else
		{
			tile.setTexture(texture );
			tile = null;
		}		
		
	}


	

}
