package ie.flax.flaxengine.client.exception;

import ie.flax.flaxengine.client.FLog;

public class MapDataCorrupt extends Exception {

	public MapDataCorrupt()
	{
		super();
		FLog.error(getError());
	}
	
	public String getError(){
	   return "Sorry but it seems your map data is Corrupted" + "\n\n" + this.getMessage();
	}
}
