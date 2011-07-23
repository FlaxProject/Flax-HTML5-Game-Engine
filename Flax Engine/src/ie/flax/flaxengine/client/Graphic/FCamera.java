package ie.flax.flaxengine.client.Graphic;

import ie.flax.flaxengine.client.FEntity;
import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FObject;
import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.events.CameraUpdateEvent;
import ie.flax.flaxengine.client.events.EventBus;

/**
 * FCamera controls the viewport of a map. It allows for only what the user is looking at to be rendered instead of the whole
 * map making for good performance boost.
 * 
 * @author Ciar�n McCann
 *
 */
public class FCamera {
	
	private transient FVector position;
	private transient int width;
	private transient int height;
	private transient int mapWidth;
	private transient int mapHeight;
	private transient int interpolation;
	
	
	public enum Directoin { NORTH, SOUTH, EAST, WEST}
	
		
	/**
	 * The position is the vector at the top left hand corner
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
		 * These are not set in the constructor as due to the JSON deserialising 
		 * method when a map is constructed it will call its set width and height.
		 * So a call to the camera is made at that time.
		 */
		
		//FIXME: Actually this needs to be changed, as what happens when more the one map is loaded, will do for the mo.
		
		//this.mapWidth = mapWidth;
		//this.mapHeight = mapHeight;		
	}
	
	
	/**
	 * Not yet implemented
	 * @param x
	 * @param y
	 */
	public void panTo(int x, int y, int speed)
	{
		//TODO implement for in-game sinmetics 
	}
	
	public void panCentered(FEntity entity, Directoin direction )
	{
		int cameraSpeed = entity.getSpeed();
		
		/**
		 * Checks if the player is in the center of the screen and if so
		 * moves the camera. This stops the camera been moved when the
		 * player is at the boundary of the map
		 */
		
		
		if(direction == Directoin.EAST)
		{
						
			if (entity.getX() + entity.getWidth() - this.getX() > (this.getWidth() / 2)) {
	
				this.incrementX(cameraSpeed);
	
				if (this.getX() % 32 != 0) {
	
					interpolation += cameraSpeed * -1;
				} else {
					interpolation += interpolation * -1;
				}
	
			}
			
		}
		else if ( direction)
			
			
			
			
			
			
			
			
			

			FLog.trace(" % [" + this.getX() + "]  " + this.getX() % 32
					+ " translated  " + interpolation);
		
	}
	
	
	public int getInterpolation()
	{
		return interpolation;
	}

	/**
	 * @param mapWidth the mapWidth to set
	 */
	public void setMapWidth(final int mapWidth) {
		this.mapWidth = mapWidth;
	}

	/**
	 * @param mapHeight the mapHeight to set
	 */
	public void setMapHeight(final int mapHeight) {
		this.mapHeight = mapHeight;
	}
		
	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}



	/**
	 * @param width the width to set
	 */
	public  void setWidth(int width) {
		this.width = width;
		FLog.trace(this.toString() + " setWidth(int " + this.width + ") PX ");
	}
	

	/**
	 * @return the width
	 */
	public  int getWidth() {
		return width;
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
	 * Gets the camera's x position
	 * @return
	 */
	public double getX()
	{
		return position.x;
	}
	
	/**
	 * Gets the camera's y position
	 * @return
	 */
	public double getY(){
		return position.y;
	}
	
	/**
	 * Sets the cameras x position, though first it checks
	 * is the input valid
	 * @param x
	 */
	public void setX(double x)
	{	
		
		//FIXME CIARAN - magic number, only for testing will change at some piont
		if(x <= (mapWidth*32)-(width)&& x >= 0)
		{	
			position.x = x;
			FLog.trace(this.toString() + " setX(double " + position.x + ") ");
		}else{
			FLog.warn("Unable to set " + this.toString() + " setX(double " + x + ") ");
		}
	}

	/**
	 * Sets the cameras y position, though first it checks
	 * is the input valid
	 * @param y
	 */
	public void setY(double y) {
		
		//FIXME CIARAN - magic number, only for testing will change at some piont
		if(y <= (mapHeight*32)-(height)&& y >= 0) 
		{

			position.y = y;
			FLog.trace(this.toString() + " setY(double " + position.y + ") ");						
		}else{
			
			FLog.warn("Unable to set " + this.toString() + " setY(double " + y + ") ");

		}
	}
	
	/**
	 * Increments the x position by provided amount
	 * @param x
	 */
	public void incrementX(double x)
	{		
		setX(x+position.x);
	}
	
	/**
	 * Increments the y position by provided amount
	 * @param y
	 */
	public void incrementY(double y)
	{		
		setY(y+position.y);	
	}
}
