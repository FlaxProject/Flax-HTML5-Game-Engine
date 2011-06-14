package ie.flax.flaxengine.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * This FCanvas has changed dramatically, it now extends the power of the GWTCanvas lib.
 * @author Ciarán McCann
 *
 */
public class FCanvas {
	
	//FIXME Annoying calls to getter, find out how to proberly extend Canvas thus FCanvas extend Canvas. 
	Canvas canvas;
	
	
	public FCanvas(Canvas canvas)
	{
		if(canvas == null)
			GWT.log("Canvas failed to construct");
			
			
		this.canvas = canvas;
	}
	
	public Canvas getCanvas()
	{
		return canvas;
	}
	
	
	
	//TODO Remove below code as it will not be need in near furture, due to the engine loading process
	/**
	 * Draws an image to the current canvas object
	 * @param imagePath - This is the path to the image, which is used to reference the image lib
	 * @param x 
	 * @param y	
	*/
	public void drawImage(String imagePath, float x, float y) {
			
			canvas.getContext2d().drawImage(Graphic.getSingleton().getImage(imagePath), x, y);
	
			//FLog.warn("DrawImage: Unable to drawImage as the image "+ imagePath +" is null");
	}
	 
	
	/**
	 * Draws an image to the current canvas object
	 * @param imagePath - This is the path to the image, which is used to reference the image lib
	 * @param d
	 * @param e
	 * @param width
	 * @param height
	*/
	public void drawImage(String imagePath, double x, double y, float width, float height) {
	
		canvas.getContext2d().drawImage(Graphic.getSingleton().getImage(imagePath), x, y,width,height);
	
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
		
		ImageElement img = Graphic.getSingleton().getImage(imagePath);

		if (img != null) { //TODO remove image load check soon
			
			numTilesWidth = (img.getWidth() / tileSize);
			ySrc = (int) (Texture / numTilesWidth);
			xSrc = Texture % numTilesWidth;
			canvas.getContext2d().drawImage(img, (float) xSrc * tileSize, (float) ySrc*tileSize, tileSize, tileSize, d, e, tileSize, tileSize);
		} 
	}
	
	/**
	 * Draws a squared grid
	 * @param width
	 * @param height
	 * @param gap
	 */
	public void drawGrid(double width, double height, int gap)
	{
		//canvas.getContext2d().setStrokeStyleDev(Color.RED);				
		for (int x = 0; x < width; x += gap) {
			canvas.getContext2d().moveTo(x, 0);
			canvas.getContext2d().lineTo(x, height);
		}
		
		for (int y = 0; y < height; y+= gap) {
			canvas.getContext2d().moveTo(0, y);
			canvas.getContext2d().lineTo(width, y);
		}
			
		canvas.getContext2d().stroke();
	}
	

	
}
