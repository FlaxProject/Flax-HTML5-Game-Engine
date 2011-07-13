package ie.flax.flaxengine.client.weave.controls;

import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.FTile;
import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.Graphic.Graphic;
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
public class TileRegion implements IControl {

	public enum MouseState
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

			
			double newX =  event.getClientX();
			double newY =  event.getClientY();
			
			
			int startX = (int)startPos.x;
			int startY = (int)startPos.y;
			
			
			
			int startXCopy = (int)startX;
			
			//start is when the click is started down and then new is got from the current position of the mouse, when moving
			while(startY <= newY)
			{
				while(startX <= newX)
				{
									 
					tileSelectedRegion(startX, startY);
					startX += tilesize;		
				}
				startX = startXCopy;
				startY += tilesize;
			}
			
			mouseState = MouseState.MOUSE_UP;
			editor.getEditorOverLay().getContext2d().clearRect(0, 0, editor.getEditorOverLay().getOffsetWidth(), editor.getEditorOverLay().getOffsetHeight()); 
		}
		
	}


	public void onMouseDown(MouseDownEvent event) {
			
			tilesize = editor.getFMapReference().getTileSize();
			map = editor.getFMapReference();
			mouseState = MouseState.MOUSE_DOWN;		
			
					
			startPos = new FVector(event.getClientX(),event.getClientY());
			texture = editor.getCurrentTile().getTexture();
	
	}

	
	public void onMouseMove(MouseMoveEvent event) {
		
		if (event.isShiftKeyDown()) {
			
			if (mouseState == MouseState.MOUSE_DOWN) {
				drawRegionBox(event.getClientX(),event.getClientY());
			}		 
		}		
	}


	/**
	 * Draws a Box from the start positon to the new posiotn of the mouse
	 * @param clientX
	 * @param clientY
	 */
	private void drawRegionBox(int clientX, int clientY) {
		
		Context2d ctx = editor.getEditorOverLay().getContext2d();	
		ctx.clearRect(0, 0, editor.getEditorOverLay().getOffsetWidth(), editor.getEditorOverLay().getOffsetHeight()); 

		
		double newX =  clientX - (startPos.x);
		double newY =  clientY - (startPos.y);	
		
		
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
		tile = map.getTile(startX,startY);
		
		if(tile == null)
		{		
			map.addTile(new FTile(startX,startY, texture, Graphic.getSingleton().getImage(map.getTileSheet()), map.getTileSize()));	
	
		}
		else			
		{
			//FIXME CIARAN - uncomment when getting tileregion back
			//tile.setTexture(texture );
			tile = null;
		}		
		
	}


	/**
	 * Gets the mouse state, i.e MOUSE_UP MOUSE_DOWN 
	 * @return
	 */
	public MouseState getMouseState()
	{
		return mouseState;
	}

}
