package ie.flax.flaxengine.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Window;
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
	private int texture;
	
	private double textureX;
	private double textureY;
		
	/**
	 * Constructs the tile object
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param texture
	 */

	public FTile(int x, int y, int texture, ImageElement img, int tileSize) {
		super();
		
		this.x = x;
		this.y = y;
		this.texture = texture;
		this.setTileTexture(texture, img, tileSize);
		
		//FLog.debug( this + " was created width  ( "+ x + "x, " +  y + "y )" );
	}
	
	
	
	/**
	 * Draws an image to the canvas object passed in.
	 * @param imagePath - This is the path to the tileSheet, which is used to reference the image lib
	 * @param tileSize
	 * @param d
	 * @param e
	 */
	public void draw(ImageElement img, int tileSize, double x, double y, final Context2d context)
	{											
			context.drawImage(img, textureX, textureY, tileSize, tileSize, x, y, tileSize, tileSize);
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
	public final int getX() {
		return x;
	}

	/**
	 * Gets the Y postion of the tile
	 * @return int Y
	 */
	public final int getY() {
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
	 * Sets the texture of the tile which is the location on the tilesheet to used when drawing the tile
	 * @return int texture number reference
	 */
	public final int getTexture() {
		return texture;
	}

	/**
	 * Sets the texture of the tile which is the image used when drawing the tile
	 * @param texture
	 */
	public void setTileTexture(int texture, ImageElement img, int tileSize) {
		
		this.texture = texture;
		
		/**
		 * These are here so they don't have to be done in the draw loop
		 */
		int numTilesWidth = 0;
		int ySrc = 0;
		float xSrc = 0;
		
		numTilesWidth = (img.getWidth() / tileSize);
		ySrc = (int) (texture / numTilesWidth);
		xSrc = texture % numTilesWidth;	
		
		
		
		textureX = xSrc * tileSize;
		textureY = ySrc * tileSize;
		
		
		//FLog.debug("t" + textureX);
	}
	
	
	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public void setTexture(int texture)
	{
		this.texture = texture;
	}



	public double getTextureX() {
		return textureX;
	}


	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public void setTextureX(double textureX) {
		this.textureX = textureX;
	}


	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public double getTextureY() {
		return textureY;
	}


	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public void setTextureY(double textureY) {
		this.textureY = textureY;
	}

}
