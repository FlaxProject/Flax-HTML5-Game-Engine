package ie.flax.flaxengine.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;

/**
 * FTile - This class is as lightweight as possible. It only has 2 members. Tiles are simple painted areas. 
 * you can't interact with tiles or anything. If you want more interact or animations you most use an FObject.
 * 
 * <br><br>
 * 
 * @TODO CIARAN - Though will probly make an AnimatedTile which extends from tile. 
 * 
 * @author Ciaran McCann
 * 
 */
public class FTile implements JsonSerializable {

	private double textureX;
	private double textureY;

	/**
	 * This constructs the FTile and calls setTileTexture, which calauates the textel corrdinates
	 * @param texture - the number which repsents a position on a tilesheet image
	 * @param img	- tilesheet image
	 * @param tileSize - size of the tiles ie 32 * 32
	 */
	public FTile(int texture, ImageElement img, int tileSize) {
		super();
		this.setTileTexture(texture, img, tileSize);
		// FLog.debug( this + " was created width  ( "+ x + "x, " + y + "y )" );
	}

	/**
	 * Tiledrawing has been inlined, this method will be removed later
	 * @param img
	 * @param tileSize
	 * @param x
	 * @param y
	 * @param context
	 */
	@Deprecated
	public void draw(ImageElement img, int tileSize, double x, double y,final Context2d context) {
		context.drawImage(img, textureX, textureY, tileSize, tileSize, x, y,tileSize, tileSize);
		// context.setFont("6pt Calibri");
		//context.fillText("    ("+ x + "," + y + ")    ", x, y);
	}

	/**
	 * DO NOT USE THIS CONSTRUCTOR -This method only exist so that JSON
	 * serialization can work Using this method is at your own risk and will
	 * most likely break your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public FTile() {
		super();
	}


	/**
	 * Caluates the texture X and Y corrdinates on the tilesheet image and saves them in 
	 * the TextureX and TextureY FTile memebers
	 * @param texture
	 * @param img
	 * @param tileSize
	 */
	public void setTileTexture(int texture, ImageElement img, int tileSize) {


		/**
		 * These are here so they don't have to be done in the draw loop
		 */
		int numTilesWidth = (img.getWidth() / tileSize);
		int ySrc = (int) (texture / numTilesWidth);
		float xSrc = texture % numTilesWidth;

		textureX = xSrc * tileSize;
		textureY = ySrc * tileSize;
	}

	/**
	 * Gets the X corrdinate of the positon of the texture on the tilesheet
	 * @return
	 */
	public final double getTextureX() {
		return textureX;
	}


	/**
	 * Sets the X corrdinate of the position of the texture on the tilesheet
	 * @param textureX
	 */
	public void setTextureX(double textureX) {
		this.textureX = textureX;
	}


	/**
	 * Gets the Y corrdinate of the positon of the texture on the tilesheet
	 * @return
	 */
	public final double getTextureY() {
		return textureY;
	}


	/**
	 * Sets the Y corrdinate of the position of the texture on the tilesheet
	 * @param textureY
	 */
	public void setTextureY(double textureY) {
		this.textureY = textureY;
	}

}
