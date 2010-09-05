package ie.flax.flaxengine.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.kfuntak.gwt.json.serialization.client.JsonSerializable;
import com.kfuntak.gwt.json.serialization.client.Serializer;

import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;
import ie.flax.flaxengine.client.events.onFileLoadedEventHandler;

public class FAudio implements JsonSerializable, onFileLoadedEventHandler{
	
	private List<FTile> tiles;
	
	public FAudio(String jsonFilePath){
		EventBus.handlerManager.addHandler(onFileLoadedEvent.TYPE, this);
		FileHandle.readFileAsString(jsonFilePath, this.toString());
	}
	
	@Override
	public void onFileLoaded(onFileLoadedEvent e) {
		if(this.toString().equalsIgnoreCase(e.getId())){
			FAudio temp = JsonToFAudio(e.getDataLoadedFromFile());
			
			this.tiles = temp.tiles;
		}
	}
	
	private FAudio JsonToFAudio(String Json) {
		Serializer serializer = (Serializer) GWT.create(Serializer.class);
		return (FAudio) serializer.deSerialize(Json,"ie.flax.flaxengine.client.FAudio");
		// FIXME I doubt this works and it's not been tested...
	}
	
	//TODO this needs to be updated to work with multiple audio streams playing at once.
	public native void play(String tagName) /*-{
		$doc.getTagById(tagName).play();
	}-*/;
	
	public native void pause(String tagName) /*-{
		$doc.getTagById(tagName).pause();
	}-*/;
	
	public native void load(String tagName) /*-{
		$doc.getTagById(tagName).load();
	}-*/;
	
	
	@Deprecated
	public FAudio() {
		
	}

	@Deprecated
	public List<FTile> getTiles() {
		return tiles;
	}

	@Deprecated
	public void setTiles(List<FTile> tiles) {
		this.tiles = tiles;
	}
}