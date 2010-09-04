package ie.flax.flaxengine.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * FImage is used to store a JS image objects width and height in java so when
 * referencing it can be done so simply with the dot operator. Instead of having
 * to get it return from a JSNI method everytime
 * 
 * @author Ciarán McCann
 * 
 */
public class FImage {

	public JavaScriptObject imageData; // Set as public so as not to add overhead of a method call
	private int width;
	private int height;
	

	
	public FImage(JavaScriptObject imageData) {

		this.imageData = imageData;
		this.width = getWidth(imageData);
		this.height = getHeight(imageData);
	}

	private native int getWidth(JavaScriptObject imageData)
	/*-{
		return imageData.width;
	}-*/;

	private native int getHeight(JavaScriptObject imageData)
	/*-{
		return imageData.height;
	}-*/;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public native  boolean isLoaded()	
	/*-{	 									 	
			return this.@ie.flax.flaxengine.client.FImage::imageData.complete;
	}-*/;
}
