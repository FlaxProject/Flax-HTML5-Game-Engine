package ie.flax.flaxengine.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * This class will allow the developer to programmatically create the canvas tag
 * and set its attributes and specify where to insert the canvas tag in the HTML
 * document i.e for embedding the canvas in a website.
 * 
 * @author Ciarán McCann
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

		this.context = getContext();	
	}


	/**
	 * Currently not in use but should be, see setupHtmlPanel method in flaxengine for more info
	 * @param width
	 * @param height
	 * @return
	 */
	public String getDomRenderingElement(int width, int height) {
		return "<canvas id=\"FlaxEngineCanvas\" style=\"background:red;\" width= " + width +" height=" + height + " >Your browser is way out of date man, get a good one like Chrome</canvas>";
	}
	

	/**
	 * 
	 * @return context in JS object
	 */
	private native JavaScriptObject getContext()
	/*-{		
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
	
	
	
	public native  JavaScriptObject loadImageOffline(JavaScriptObject file)
	/*-{		
				  
   var img = new Image();
     img.file = file;		
		   
    var reader = new FileReader();
     reader.onload = (function(aImg) { return function(e) { aImg.src = e.target.result; }; })(img);
    reader.readAsDataURL(file);
    
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
			this.@ie.flax.flaxengine.client.FCanvas::context.drawImage(imageObj,x,y,height,width);			
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
	public native void drawImage(JavaScriptObject imageObj, float xSrc,float ySrc, float widthSrc, float heightSrc, float xDes,float yDes, float widthDes, float heightDes)
	/*-{
		//this.@ie.flax.flaxengine.client.FCanvas::context.rotate(45);
		this.@ie.flax.flaxengine.client.FCanvas::context.drawImage(imageObj,xSrc,ySrc,widthSrc,heightSrc,xDes,yDes,widthDes,heightDes);
	}-*/;

	

}
