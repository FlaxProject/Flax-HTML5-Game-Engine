package ie.flax.flaxengine.client;

/**
 * 2D Vector Class
 *
 */
public class FVector {
	
	public double x,y;
	
	/**
	 * Takes an x double and y double
	 * @param x
	 * @param y
	 */
	public FVector(double x, double y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns new vector with v and this added togtheir
	 * @param v
	 * @return
	 */
	public FVector add(FVector v){
		return new FVector(this.x+v.x,this.y+v.y);
	}
	
	/**
	 * Returns new vector with v and this added togtheier
	 * @param v
	 * @return
	 */
	public FVector subtract(FVector v){
		return new FVector(this.x-v.x,this.y-v.y);
	}
	
	/**
	 * Scales the vectors componemts by F
	 * @param f
	 * @return
	 */
	public FVector scale(float f){
		return new FVector(this.x*f, this.y*f);
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public double dotProduct(FVector v){
		return ((x*(v.x))+(y*(v.y)));
	}
	
	/**
	 * Gets the lenght of the current vector
	 * @return
	 */
	public double lenght(){
		return Math.sqrt((x*x) + (y*y));
	}
	
	
	/**
	 * Retruns a new vector which has been roated by given amount
	 * @param angleInDegrees
	 * @return
	 */
	public FVector rotate(double angleInDegrees){
		angleInDegrees=angleInDegrees/360.0*2*Math.PI;

		double a,b;

		a= x*Math.cos(angleInDegrees)+y*Math.sin(angleInDegrees);
		b= x*-Math.sin(angleInDegrees)+y*Math.cos(angleInDegrees);

		return new FVector(a,b);
	}


	
}
