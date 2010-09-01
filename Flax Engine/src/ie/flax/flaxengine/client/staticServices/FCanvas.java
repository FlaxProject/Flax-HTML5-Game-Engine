package ie.flax.flaxengine.client.staticServices;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * This class will allow the developer to programmatically create the canvas tag
 * and set its attributes and specify where to insert the canvas tag in the HTML
 * document i.e for embedding the canvas in a website.
 * 
 * @author Ciarán McCann
 */
public class FCanvas {

	public JavaScriptObject context = null;
	private int width;
	private int height;

	/**
	 * 
	 * @return width of canvas
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 
	 * @return height of canvas
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * This constructor sets up the canvas and gives it a default width and
	 * height of 640*480
	 * 
	 * @param insertID
	 *            is the ID of the HTML element in which to insert the canvas
	 *            tag.
	 */
	public FCanvas(String insertId) {
		this.width = 640;
		this.height = 480;

		setupCanvasTag(insertId, width, height);
		this.context = getContext();
		
	}

	/**
	 * 
	 * @param insertID
	 *            is the ID of the HTML element in which to insert the canvas
	 *            tag.
	 * @param width
	 *            canvas should be
	 * @param height
	 *            canvas should be
	 */
	public FCanvas(String insertId, int width, int height) {

		this.width = width;
		this.height = height;

		setupCanvasTag(insertId, width, height);
		this.context = getContext();
	
	}

	/**
	 * 
	 * @param insertID
	 *            is the ID of the HTML element in which to insert the canvas
	 *            tag.
	 * @param width
	 *            of canvas
	 * @param height
	 *            of canvas
	 */
	public native void setupCanvasTag(String insertId, int width, int height)
	/*-{
		$doc.getElementById(insertId).innerHTML = '<canvas id=\"FlaxEngineCanvas\" style="background:red;" width= ' + width + ' height= ' + height + '  >Your browser is way out of date man, get a good one like Chrome</canvas>';
	}-*/;

	/**
	 * 
	 * @return context in JS object
	 */
	private native JavaScriptObject getContext()
	/*-{
		//TODO store canvas context instead of getting everytime
		return $doc.getElementById('FlaxEngineCanvas').getContext('2d');
		
	}-*/;

	/**
	 * 
	 * @param imageURL
	 * @return JS Object containing the image object
	 */
	public native JavaScriptObject loadImage(String imagePath)
	/*-{
		var img = new Image();
		img.src = imagePath; 

		return img;
	}-*/;

	/**
	 * Draws an image to the canvas
	 * 
	 * @param imageObj
	 *            This is the actual image which will be drawn
	 * @param x
	 * @param y
	 * @param height
	 * @param width
	 */
	public native void drawImage(JavaScriptObject imageObj, float x, float y,float height, float width)
	/*-{	 									 	
		$doc.getElementById('FlaxEngineCanvas').getContext('2d').drawImage(imageObj,x,y,height,width);	
	}-*/;

	/**
	 * Draws an segment of an image to a segment of the canvas
	 * 
	 * @param imageObj
	 * @param xSrc
	 * @param ySrc
	 * @param widthSrc
	 * @param heightSrc
	 * @param xDes
	 * @param yDes
	 * @param widthDes
	 * @param heightDes
	 */
	/*public native void drawImage(JavaScriptObject imageObj, float xSrc,
			float ySrc, float widthSrc, float heightSrc, float xDes,
			float yDes, float widthDes, float heightDes)
	-{
		context.drawImage(imageObj,xSrc,ySrc,widthSrc,heightSrc,xDes,yDes,widthDes,heightDes);
	}-;*/

}
