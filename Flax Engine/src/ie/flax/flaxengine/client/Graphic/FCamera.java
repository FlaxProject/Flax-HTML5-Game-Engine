package ie.flax.flaxengine.client.Graphic;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.events.CameraUpdateEvent;
import ie.flax.flaxengine.client.events.EventBus;

/**
 * FCamera controls the viewport of a map. It allows for only what the user is looking at to be rendered instead of the whole
 * map making for good performance bust.
 * 
 * @author Ciaran McCann
 *
 */
public class FCamera {
	
	private FVector position;
	private int width;
	private int height;
	private int mapWidth;
	private int mapHeight;
	
		
	/**
	 * The position is the vector at the top left hand conor
	 * The width and the height define the viewport of the map<br>
	 * <br>Most of the time the width and height will be the same as the canvas width and height
	 * @param position
	 * @param width
	 * @param height
	 * @param mapWidth
	 * @param mapHeight
	 */
	public FCamera(FVector position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;
		
		FLog.trace(this.toString() + " Was created ");
		
		EventBus.handlerManager.fireEvent(new CameraUpdateEvent()); 
		
		/**
		 * These are not set in the constructer as due to the JSON deserailing 
		 * metho when a map is constructed it will call its set width and height.
		 * So a call to the camera is made at that time.
		 */
		
		//FIXME: Actually this needs to be chnaged, as what happens when more the one map is loaded, will do for the mo.
		
		//this.mapWidth = mapWidth;
		//this.mapHeight = mapHeight;		
	}

	/**
	 * @param mapWidth the mapWidth to set
	 */
	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	/**
	 * @param mapHeight the mapHeight to set
	 */
	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	/**
	 * @return the width
	 */
	public  int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public  void setWidth(int width) {
		this.width = width;
		FLog.trace(this.toString() + " setWidth(int " + this.width + ") PX ");
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height)  {
		this.height = height;
		FLog.trace(this.toString() + " setHeight(int " + this.height + ") PX ");
	}	
	
	/**
	 * Gets the cameras x postion
	 * @return
	 */
	public double getX()
	{
		return position.x;
	}
	
	/**
	 * Gets the cameras y postition
	 * @return
	 */
	public double getY()
	{
		return position.y;
	}
	
	/**
	 * Sets the cameras x postion, though first it checks
	 * is the input valid
	 * @param x
	 */
	public void setX(double x)
	{		
		//if(x <= mapWidth-width&& x > 0)
		{
			
			position.x = x;
			FLog.trace(this.toString() + " setX(double " + position.x + ") ");

			EventBus.handlerManager.fireEvent(new CameraUpdateEvent()); 
		}
	}

	/**
	 * Sets the cameras y postion, though first it checks
	 * is the input valid
	 * @param y
	 */
	public void setY(double y) {
		
		//if(y <= mapHeight-height&& y > 0) //FIXME CIARAN
		{

			position.y = y;
			FLog.trace(this.toString() + " setY(double " + position.y + ") ");
			

			EventBus.handlerManager.fireEvent(new CameraUpdateEvent()); 
		}
		
	}
	
	
	/**
	 * Incerments the x postion by provided amount
	 * @param x
	 */
	public void incermentX(double x)
	{		
		setX(x+position.x);
	}
	
	/**
	 * Incerments the y postion by provided amount
	 * @param y
	 */
	public void incermentY(double y)
	{		
		setY(y+position.y);	
	}
	

}
