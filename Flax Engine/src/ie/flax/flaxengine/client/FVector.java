package ie.flax.flaxengine.client;

public class FVector {
	public double x,y;
	
	FVector(double x, double y){
		this.x = x;
		this.y = y;
	}

	void add(FVector v){
		x+=v.x;
		y+=v.y;
	}
	
	void rotate(double angleInDegrees){
		angleInDegrees=angleInDegrees/360.0*2*Math.PI;

		double a,b;

		a= x*Math.cos(angleInDegrees)+y*Math.sin(angleInDegrees);
		b= x*-Math.sin(angleInDegrees)+y*Math.cos(angleInDegrees);

		x=a;
		y=b;
	}
	
	void scale(float f){
		x*=f;
		y*=f;
	}
	
	double dot(FVector v){
		return ((x*(v.x))+(y*(v.y)));
	}
	
	double magnitude(){
		return Math.sqrt((x*x) + (y*y));
	}
}
