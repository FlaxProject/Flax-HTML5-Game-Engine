package ie.flax.flaxengine.client;

public class PhysicsObject {

	private FVector position;
	private FVector velocity;
	private float width; 
	private float height;
	
	
	/**
	 * @param position
	 * @param velocity
	 * @param width
	 * @param height
	 */
	public PhysicsObject(FVector position, float width,float height) {
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	
	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}


	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}


	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}


	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}



	
	/**
	 * @return the position
	 */
	public FVector getPosition() {
		return position;
	}


	/**
	 * @param position the position to set
	 */
	public void setPosition(FVector position) {
		this.position = position;
	}


	/**
	 * @return the velocity
	 */
	public FVector getVelocity() {
		return velocity;
	}


	/**
	 * @param velocity the velocity to set
	 */
	public void setVelocity(FVector velocity) {
		this.velocity = velocity;
	}
}
