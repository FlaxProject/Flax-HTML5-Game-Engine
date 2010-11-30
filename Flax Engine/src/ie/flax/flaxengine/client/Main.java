package ie.flax.flaxengine.client;


import com.allen_sauer.gwt.log.client.Log;
import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Timer;

/**
 * This class is the EntryPiont of the program and its used to test the engine.
 * @author Ciarán McCann
 *
 */
public class Main implements EntryPoint{

	developersCodeTest theGame = new developersCodeTest("map.json", "nameFieldContainer");
	
	@Override
	public void onModuleLoad() {
		/*FileHandle.writeStringToFile("testName.txt", "This is a test", this.toString());
		FileHandle.readFileAsString("http://flax.ie/private/testName.txt", this.toString());*/
		
		theGame.run(); // Starts the game loop
		//com.google.gwt.user.client.Window.alert("start");
		
		theGame.addKeyDownHandler( new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				Log.info("key");
				
			}
		});
	}

}
