package ie.flax.flaxengine.client;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class Audio {
	
	private List<String> tags = new ArrayList<String>();
	
	//TODO this needs to be updated to work with multiple audio streams playing at once.
	//also, should gracefully handle the tag not existing
	static native void play(String src) /*-{
		$doc.getTagById(src).play();
	}-*/;
	
	public native void pause(String src) /*-{
		$doc.getTagById(src).pause();
	}-*/;
	
	private void loadHtml(String src) {
		// Later on, this should be changed to SafeHtml
		// this will be when we move to GWT 2.1
		// http://code.google.com/webtoolkit/doc/latest/DevGuideSecuritySafeHtml.html
		
		//only do anything if the tag hasn't been loaded previously by something else
		if(!tags.contains(src)){
		
		// the source and the tagName are the same thing
		// DIRECTED BY M. NIGHT SHYAMALAN
		String tagName = src;
		
		//adds the html
		String pageContent="<audio id="+tagName+" src="+src+"></audio>";
		RootPanel.get().add(new HTML(pageContent));
		
		//adds that tag to the list
		tags.add(tagName);
		
		loadAudioFile(tagName);
		}
	}
	
	private native void loadAudioFile(String src) /*-{
		// this simply calls the audio JSO load function, because
		// some browsers need to explicitly be told to do this. 
		$doc.getTagById(tagName).load();
	}-*/;
	
	public boolean isComponentReady(){
		return true; // for testing only
	}
}