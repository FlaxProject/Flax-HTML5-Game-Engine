package ie.flax.flaxengine.client.Graphic;

import ie.flax.flaxengine.client.FEntity;
import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FVector;
import ie.flax.flaxengine.client.events.CameraUpdateEvent;
import ie.flax.flaxengine.client.events.EventBus;

/**
 * FCamera controls the viewport of a map. It allows for only what the user is looking at to be rendered instead of the whole
 * map making for good performance boost.
 * 
 * @author Ciaran McCann
 *
 */
public class FCamera {
	
	private transient FVector position;
	private transient int width;
	private transient int height;
	private transient int mapWidth;
	private transient int mapHeight;
	private transient FVector interpolation;
	private transient Directoin interpolationDirection;
	
	
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
		
		this.interpolation = new FVector(0, 0);
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
	
	/**
	 * Allows the camera the be centered on a entity and pan with easing.
	 * 
	 * <br><br>
	 * This method should be called when the entity moves, it caluates should the camera move.
	 * It also eases the camera across with a smooth translation
	 * 
	 * @param entity - Entity which the camera will center on
	 * @param direction - direction that entity is moving
	 */
	public void panCentered(FEntity entity, Directoin direction )
	{
		
		/**
		 * READ ME
		 * This method is quite complex, look at the
		 * first if block for step by step instructions
		 */
		
		
		int cameraSpeed = entity.getSpeed();		
		
		if(direction == Directoin.EAST) // check direction camera is to pan in
		{
			 // check if this is the direction the entity was moving in, at the last call of this method
			if(interpolationDirection != direction)
			{
				interpolationDirection = direction;
				interpolation.x = 0;
			}
				
			//below if checks basically if the entity is in the center of the drawingspace
			if (entity.getX() + entity.getWidth() - this.getX() > (this.getWidth() / 2)) {
	
				 //if the camera was sucessfully set to the next value, if so do translate pan. If not you have reached a border of the map
				if(this.incrementX(cameraSpeed))
				{
					//FIXME - CIARAN magic numbers
					if (this.getX() % 32 != 0) {
		
						interpolation.x += cameraSpeed * -1; //Interpolation.x is used in a GLTranslate call to achieve the interpolation
					} else {
						interpolation.x += interpolation.x * -1; //Thisa then sets it back when the full tile can be seen
					}
				}
	
			}
			
		}
		else if (direction == Directoin.WEST)
		{
			
			if(interpolationDirection != direction)
			{
				interpolationDirection = direction;
				interpolation.x = 0;
				
			}
			
			if (entity.getX() + entity.getWidth() - this.getX() < (this.getWidth() / 2)) {			
					
					if (this.getX() % 32 != 0) {
		
						interpolation.x += cameraSpeed;
					} else {
						interpolation.x += interpolation.x * -1;
					}	
					
					this.incrementX(cameraSpeed * -1);				
			}		
			
		}
		else if (direction == Directoin.SOUTH)
		{
			
			if(interpolationDirection != direction)
			{
				interpolationDirection = direction;
				interpolation.y = 0;
			}
			
			if (entity.getY() + entity.getHeight() - this.getY() > (this.getHeight() / 2)) {
		
				if(this.incrementY(cameraSpeed))
				{
					if (this.getY() % 32 != 0) {
		
						interpolation.y += cameraSpeed * -1;
					} else {
						interpolation.y += interpolation.y * -1;
					}			
				}
			}
			
			
		}		
		else if (direction == Directoin.NORTH)
		{
			
			if(interpolationDirection != direction)
			{
				interpolationDirection = direction;
				interpolation.y = 0;
			}
			
			if (entity.getY() + entity.getHeight() - this.getY() < (this.getHeight() / 2)) {
	
				
					if (this.getY() % 32 != 0) {
		
						interpolation.y += cameraSpeed;
					} else {
						interpolation.y += interpolation.y * -1;
					}	
					
					this.incrementY(cameraSpeed * -1);
			}			
		}	
	}
	
	
	public FVector getInterpolation(){
		return interpolation;
	}

	/**
	 * @param mapWidth the mapWidth to set
	 */
	public void setMapWidth(final int mapWidth) {
		this.mapWidth = mapWidth;
		this.mapWidth += 1;
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
	public boolean setX(double x)
	{	
		
		//FIXME CIARAN - magic number, only for testing will change at some piont
		if(x <= (mapWidth*32)-(width)&& x >= 0)
		{	
			position.x = x;
			FLog.trace(this.toString() + " setX(double " + position.x + ") ");
			return true;
			
		}else{
			FLog.warn("Unable to set " + this.toString() + " setX(double " + x + ") ");
			return false;
		}
	}

	/**
	 * Sets the cameras y position, though first it checks
	 * is the input valid
	 * @param y
	 */
	public boolean setY(double y) {
		
		//FIXME CIARAN - magic number, only for testing will change at some piont
		if(y <= (mapHeight*32)-(height)&& y >= 0) 
		{
			position.y = y;
			FLog.trace(this.toString() + " setY(double " + position.y + ") ");		
			return true;
			
		}else{
			
			FLog.warn("Unable to set " + this.toString() + " setY(double " + y + ") ");
			return false;

		}
	}
	
	/**
	 * Increments the x position by provided amount
	 * @param x
	 */
	public boolean incrementX(double x)
	{		
		return setX(x+position.x);
	}
	
	/**
	 * Increments the y position by provided amount
	 * @param y
	 */
	public boolean incrementY(double y)
	{		
		return setY(y+position.y);	
	}
}
