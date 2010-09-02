package ie.flax.flaxengine.client;


import org.mortbay.log.Log;

import com.google.gwt.core.client.EntryPoint;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FlaxEngine implements EntryPoint {
	
	FMap myMap = new FMap();
	
	@Override
	public void onModuleLoad() {
		
		Graphic.init("test");		
		Graphic.loadImage("x.jpg","x");
		Graphic.loadImage("u.jpg","u");
		Graphic.loadImage("x.jpg","c");
		
		myMap.draw();
		

		
		
	
	}
}
