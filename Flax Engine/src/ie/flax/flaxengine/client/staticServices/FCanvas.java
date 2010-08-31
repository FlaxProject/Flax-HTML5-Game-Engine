package ie.flax.flaxengine.client.staticServices;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * 
 * @author Ciarán McCann
 * 
 *         This class will allow the developer to programmatically create the
 *         canvas tag and set its attributes and specify where to insert the
 *         canvas tag in the HTML document i.e for embedding the canvas in a
 *         website.
 * 
 * 
 */
public class FCanvas {

	private JavaScriptObject context;
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
	public FCanvas(String insertID) {
		this.width = 640;
		this.height = 480;

		setupCanvasTag(insertID, width, height);
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
	public FCanvas(String insertID, int width, int height) {

		this.width = width;
		this.height = height;

		setupCanvasTag(insertID, width, height);
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
	public native void setupCanvasTag(String insertID, int width, int height)
	/*-{
		$doc.getElementById(insertID).innerHTML = "<canvas style=border:black 1px solid id=\"FlaxEngineCanvas\" width=" + width + " height=" + height + ">Your browser is way out of date man, get a good one like Chrome</canvas>";
	}-*/;

	/**
	 * 
	 * @return context in JS object
	 */
	public native JavaScriptObject getContext()
	/*-{
		return $doc.getElementById('FlaxEngineCanvas').getContext('2d');
	}-*/;

	/**
	 * 
	 * @param imageURL
	 * @return JS Object containing the image object
	 */
	public native JavaScriptObject loadImage(String imageURL)
	/*-{
		//TODO add in check for if its relative url or abolsute 
		var img = new Image();
		img.src = imageURL; 

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
	public native void drawImage(JavaScriptObject imageObj, float x, float y,
			float height, float width)
	/*-{
		context.drawImage(imageObj,x,y,width,height);
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
	public native void drawImage(JavaScriptObject imageObj, float xSrc,
			float ySrc, float widthSrc, float heightSrc, float xDes,
			float yDes, float widthDes, float heightDes)
	/*-{
		context.drawImage(imageObj,xSrc,ySrc,widthSrc,heightSrc,xDes,yDes,widthDes,heightDes);
	}-*/;

}
