package ie.flax.flaxengine.client;


import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;


public class FlaxEngine implements EntryPoint {
	
	FMap myMap = new FMap("map.json");
	
	@Override
	public void onModuleLoad() {
		
		//Graphic.init("nameFieldContainer");		
		//Graphic.loadImage("http://cdn.flax.ie/images/flaxLogo.png","c");
		//Graphic.loadImage("u.jpg","u");
		//Graphic.loadImage("x.jpg","c");
		
		//myMap.draw();
		
		Log.info(myMap.getTileSheet());
		

		
		int inid = 1;
		//FileHandle.readFileAsString("testFile.txt", inid );
		//EventBus.handlerManager.fireEvent(new onFileLoadedEvent("OHHAI", 3));
	}
}
