package ie.flax.flaxengine.client;


import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
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
		
		theGame.run(); // Starts the game loop
		
		
				
	}

}
