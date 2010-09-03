package ie.flax.flaxengine.client;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;


public class FlaxEngine implements EntryPoint {
	
	FMap myMap = new FMap();
	
	@Override
	public void onModuleLoad() {
		
		Graphic.init("test");		
		Graphic.loadImage("x.jpg","x");
		Graphic.loadImage("u.jpg","u");
		Graphic.loadImage("x.jpg","c");
		
	//	myMap.draw();
		
		Log.info(myMap.FMapToJson());
		

		
		
	
	}
}
