package ie.flax.flaxengine.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * This class is the EntryPiont of the program and its used to test the engine.
 * @author Ciaran McCann
 *
 */
public class Main implements EntryPoint{

	developersCodeTest theGame = new developersCodeTest("smallMap.json", "nameFieldContainer");
	
	@Override
	public void onModuleLoad() {				
		theGame.run(); // Starts the game loop		
	}

}
