package ie.flax.flaxengine.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;

/**
 * FTile extends FObject and is the main compoenet of the map class.
 * 
 * @author Ciaran McCann
 *
 */
public class FTile implements JsonSerializable{

	private int x;
	private int y;
	private boolean solid;
	private int texture;
		
	/**
	 * Constructs the tile object
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param texture
	 */

	public FTile(int x, int y, boolean solid, int texture) {
		super();
		this.x = x;
		this.y = y;
		this.solid = solid;
		this.texture = texture;
	}
	
	
	
	/**
	 * Draws an image to the canvas object passed in.
	 * @param imagePath - This is the path to the tileSheet, which is used to reference the image lib
	 * @param tileSize
	 * @param d
	 * @param e
	 */
	public void draw(ImageElement img, int tileSize, double x, double y, Context2d context)
	{	
	
		if (img != null) {
			
			int numTilesWidth = 0;
			int ySrc = 0;
			float xSrc = 0;
			
			numTilesWidth = (img.getWidth() / tileSize);
			ySrc = (int) (texture / numTilesWidth);
			xSrc = texture % numTilesWidth;
			context.drawImage(img, (float) xSrc * tileSize, (float) ySrc*tileSize, tileSize, tileSize, x, y, tileSize, tileSize);
		} 
	}

	
	
	/**
	 * DO NOT USE THIS CONSTRUCTOR -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public FTile() {
		super();
	}

	/**
	 * Gets the X postion of the tile
	 * @return int X
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the Y postion of the tile
	 * @return int Y
	 */
	public int getY() {
		return y;
	}


	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated

	public void setY(int y) {
		this.y = y;
	}


	/**
	 * Ture or false is the tile collideable or not
	 * @return
	 */
	public boolean getSolid() {
		return solid;
	}

	/**
	 * Ture or false is the tile collideable or not
	 * @param solid
	 */
	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	/**
	 * Sets the texture of the tile which is the location on the tilesheet to used when drawing the tile
	 * @return int texture number reference
	 */
	public int getTexture() {
		return texture;
	}

	/**
	 * Sets the texture of the tile which is the image used when drawing the tile
	 * @param texture
	 */
	public void setTexture(int texture) {
		this.texture = texture;
	}

}
