package ie.flax.flaxengine.client.weave.controls;

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
 * @author Ciarán McCann
 * 
 *
 */
public abstract class TileRegion implements IControlTileRegion {

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

	
	public TileRegion(Weave editor)
	{
		startPos = new FVector(0, 0);
		mouseState = MouseState.MOUSE_UP;
		this.editor = editor;
		tile = null;		
	}


	@Override
	public void onMouseUp(MouseUpEvent event) {
		
		if(mouseState == MouseState.MOUSE_DOWN)
		{
				
			int tilesize = editor.getFMapReference().getTileSize();
			
			int newX =  (int) (FlaxEngine.camera.getX() + event.getClientX())/tilesize ;
			int newY =  (int) (FlaxEngine.camera.getY() + event.getClientY())/tilesize ;
			
			int startX = (int) ((int) FlaxEngine.camera.getX()/tilesize + startPos.x/tilesize);
			int startY = (int) ((int) FlaxEngine.camera.getY()/tilesize + startPos.y/tilesize);
			
			
			int startXCopy = startX;
			
			while(startY <= newY)
			{
				while(startX <= newX)
				{
									 
					this.doTileRegionLogic(startX, startY);
					startX++;		
				}
				startX = startXCopy;
				startY++;
			}
			
			mouseState = MouseState.MOUSE_UP;
		}
		
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		
		if (event.isShiftKeyDown())
		{			
			mouseState = MouseState.MOUSE_DOWN;				
			startPos = new FVector(event.getX(), event.getY());
		}
		
		
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		
		if (event.isShiftKeyDown()) {
			
			if (mouseState == MouseState.MOUSE_DOWN) {
				drawRegionBox(event.getClientX(), event.getClientY());
			}
		 
		}
		
	}


	private void drawRegionBox(int clientX, int clientY) {
		

		double newX =  clientX - startPos.x;
		double newY =  clientY - startPos.y;
		
		Context2d ctx = editor.getdrawingSpace().getContext2d();				
		
		ctx.setStrokeStyle("#CD0000");
		ctx.beginPath();
		
		ctx.moveTo(startPos.x, startPos.y);
		ctx.lineTo(startPos.x+newX, startPos.y);
		
		ctx.lineTo(startPos.x+newX, startPos.y+newY);
		ctx.lineTo(startPos.x, startPos.y+newY);
		
		ctx.closePath();
		ctx.stroke();
		
	}

	
	protected void tileSelectedRegion(int startX, int startY)
	{
		int tilesize = editor.getFMapReference().getTileSize();
		
		tile = editor.getFMapReference().getTile(startX*tilesize, startY*tilesize);
		
		if(tile == null)
		{
		
			editor.getFMapReference().addTile(new FTile(startX*tilesize, startY*tilesize, true, editor.getCurrentTile().getTexture()));
		
		}
		else
		{
			tile.setTexture(editor.getCurrentTile().getTexture());
			tile = null;
		}						
	}


	

}
