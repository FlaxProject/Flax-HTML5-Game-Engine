package ie.flax.flaxengine.client;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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

	
	public FCanvas() {
		super();
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
		{
		this.drawImage(img, x, y);
		}else{
			//FLog.warn("DrawImage: Unable to drawImage as the image "+ imagePath +" is null");
		}
	}
	
	/**
	 * Draws an image to the current canvas object
	 * @param imagePath - This is the path to the image, which is used to reference the image lib
	 * @param d
	 * @param e
	 * @param width
	 * @param height
	 */
	public void drawImage(String imagePath, double d, double e, float width, float height) {
			
		ImageElement img = Graphic.getImage(imagePath);
		
		if(img != null){
		this.drawImage(img, d, e,width,height);
	}else{
		//FLog.warn("DrawImage: Unable to drawImage as the image "+ imagePath +" is null");
	}
	}
	
	
	/**
	 * Removed from the drawTile method to increse drawing speed
	 */
	private int numTilesWidth = 0;
	private int ySrc = 0;
	private float xSrc = 0;
	
	/**
	 * Draws an image to the current canvas object
	 * @param imagePath - This is the path to the tileSheet, which is used to reference the image lib
	 * @param Texture - a number which presents a square on the tilesheet
	 * @param tileSize
	 * @param d
	 * @param e
	 */
	public void drawTile(String imagePath, int Texture, int tileSize, double d, double e)
	{	
		
		ImageElement img = Graphic.getImage(imagePath);

		if (img != null) {
			
			numTilesWidth = (img.getWidth() / tileSize);
			ySrc = (int) (Texture / numTilesWidth);
			xSrc = Texture % numTilesWidth;
			this.drawImage(img, (float) xSrc * tileSize, (float) ySrc*tileSize, tileSize, tileSize, d, e, tileSize, tileSize);
		} 
	}
	
	
	/**
	 * Allows mouse clicks to be registered on the tilesheet
	 * @param handler
	 * @return
	 */
	public HandlerRegistration addMouseHandler(MouseDownHandler handler) {
        return addDomHandler(handler, MouseDownEvent.getType());
  }
	
}
