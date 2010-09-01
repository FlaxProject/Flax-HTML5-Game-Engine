package ie.flax.flaxengine.client;

/**
 * FObject is the base object which all physical objects in the game are derived from.
 * 
 * @author Ciarán McCann
 *
 */
public class FObject {

	private float x;
	private float y;
	private float width;
	private float height;
	
	/**
	 * Constructs the Objects	
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public FObject(float x, float y, float width, float height) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
