package ie.flax.flaxengine.client.weave.controls;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.weave.Weave;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;

/**
 * This control allows for tileRegion effects. Such as setting the texture of a tile
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
	private int tilesize;


	public TileRegion(Weave editor)
	{
		startPos = new FVector(0, 0);
		mouseState = MouseState.MOUSE_UP;
		this.editor = editor;		
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
									 
					editor.textureTileAt(startX, startY);
					startX += tilesize;		
				}
				startX = startXCopy;
				startY += tilesize;
			}
			
			mouseState = MouseState.MOUSE_UP;
			FLog.debug("editor.getEditorOverLay().getOffsetWidth()" + editor.getEditorOverLay().getOffsetWidth() + "editor.getEditorOverLay().getOffsetHeight()" + editor.getEditorOverLay().getOffsetHeight());
			editor.getEditorOverLay().getContext2d().clearRect(0, 0, editor.getEditorOverLay().getOffsetWidth(), editor.getEditorOverLay().getOffsetHeight()); 
		}
		
	}


	/**
	 * This starts this control, the positon of mouse down is saved
	 */
	public void onMouseDown(MouseDownEvent event) {
			
			tilesize = editor.getFMapReference().getTileSize();
			mouseState = MouseState.MOUSE_DOWN;									
			startPos = new FVector(event.getClientX(),event.getClientY());	
	}

	/**
	 * When the mouse moves and the mouse is in a down state it draws the regions box
	 */
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

	/**
	 * Gets the mouse state, i.e MOUSE_UP MOUSE_DOWN 
	 * @return
	 */
	public MouseState getMouseState()
	{
		return mouseState;
	}

}
