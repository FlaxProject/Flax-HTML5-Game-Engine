package ie.flax.flaxengine.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;

/**
 * FObject is the base object which all physical objects in the game are derived from.
 * 
 * @author Ciaran McCann
 *
 */
public class FObject implements JsonSerializable{

	private float x;
	private float y;
	private float width;
	private float height;
	private String sprite;
	private String audio;
	


	/**
	 * Constructs the Objects	
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public FObject(float x, float y, float width, float height, String sprite, String[] audio) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		this.audio = audio[0];
		
	}
	

	/**
	 * Draws the objects sprite
	 * its passed the canvas reference to speed up rendering
	 * @param refence
	 */
	public void draw(FCanvas refence)
	{
		if(sprite != null)
		{
			refence.drawImage(sprite, x-FlaxEngine.camera.getX(), y-FlaxEngine.camera.getY(),width,height);
		}
		//TODO have a thing about animation and weather or not to have that as extend feature
	}
	
	
	/**
	 * DO NOT USE THIS METHOD -This method only exist so that JSON serialization
	 * can work Using this method is at your own risk and will most likely break
	 * your code in RUNTIME!!
	 * 
	 */
	@Deprecated
	public FObject() {
		
	}
	
	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}


	
	public String getSprite() {
		return sprite;
	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Gets the X value of the FObject
	 * @return float
	 */
	public float getX() {
		return x;
	}
	/**
	 * Sets the X value of the FObject
	 * 
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * Gets the Y value of the FObject
	 * @return float
	 */
	public float getY() {
		return y;
	}
	/**
	 * Sets the Y value of the FObject
	 */
	public void setY(float y) {
		this.y = y;
	}
	/**
	 * Gets the width value of the FObject
	 * @return float
	 */
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	/**
	 * Gets the height value of the FObject
	 * @return float
	 */
	public float getHeight() {
		return height;
	}
	/**
	 * Sets the height value of the FObject
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	
	
}
