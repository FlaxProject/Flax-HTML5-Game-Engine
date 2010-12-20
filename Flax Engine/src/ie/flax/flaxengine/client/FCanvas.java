package ie.flax.flaxengine.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * This FCanvas has changed dramatically, it now extends the power of the GWTCanvas lib.
 * @author Ciarán McCann
 *
 */
public class FCanvas extends GWTCanvas{

	
	/**
	 * Instiates the GWTCanvas
	 * @param width
	 * @param height
	 */
	public FCanvas(int width, int height) {
		super(width,height);
	}

	
	/**
	 * Draws an image to the current canvas object
	 * @param imagePath - This is the path to the image, which is used to reference the image lib
	 * @param x 
	 * @param y
	 */
	public void drawImage(String imagePath, float x, float y) {
			
		ImageElement img = Graphic.getImage(imagePath);
		
		if(img != null)
		this.drawImage(img, x, y);
	}
	
	/**
	 * Draws an image to the current canvas object
	 * @param imagePath - This is the path to the image, which is used to reference the image lib
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawImage(String imagePath, float x, float y, float width, float height) {
			
		ImageElement img = Graphic.getImage(imagePath);
		
		if(img != null)
		this.drawImage(img, x, y,width,height);
	}
	
	
	/**
	 * Draws an image to the current canvas object
	 * @param imagePath - This is the path to the tileSheet, which is used to reference the image lib
	 * @param Texture - a number which presents a square on the tilesheet
	 * @param tileSize
	 * @param x
	 * @param y
	 */
	public void drawTile(String imagePath, int Texture, int tileSize, float x, float y)
	{	
		int numTilesWidth = 0;
		int ySrc = 0;
		float xSrc = 0;
		
		ImageElement img = Graphic.getImage(imagePath);
			
		if(img != null)
		{
				numTilesWidth = (img.getWidth()/tileSize);
				ySrc = (int)(Texture/numTilesWidth);
				xSrc = Texture%numTilesWidth;
				this.drawImage(img, (float)xSrc*tileSize, (float)ySrc*tileSize, tileSize, tileSize, x, y, tileSize, tileSize);
		}
	}
	
}
