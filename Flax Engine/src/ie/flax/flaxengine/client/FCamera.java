package ie.flax.flaxengine.client;

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
	}	
	
	
	public double getX()
	{
		return position.x;
	}
	
	public double getY()
	{
		return position.y;
	}
	
	public void setX(double x)
	{
		
		if(x <= mapWidth-width&& x > 0)
		{
			position.x = x;
		}
	}
	
	public void incermentX(double x)
	{		
		setX(x+position.x);
	}
	
	public void incermentY(double y)
	{		
		setY(y+position.y);	
	}

	public void setY(double y) {
		
		if(y <= mapHeight-height&& y > 0)
		{
			position.y = y;
		}
		
	}
	
	

}
