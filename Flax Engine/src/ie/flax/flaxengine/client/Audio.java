package ie.flax.flaxengine.client;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
/**
 * Audio.java
 * @author carllange
 *
 */
public class Audio {
	
	private static List<String> tags = new ArrayList<String>();
	
	//TODO this needs to be updated to work with multiple audio streams playing at once.
	//also, should gracefully handle the tag not existing
	public static native void play(String src) /*-{
		$doc.getTagById(src).play();
	}-*/;
	
	public static  native void pause(String src) /*-{
		$doc.getTagById(src).pause();
	}-*/;

	/**
	 * Load Audio file and checks if the file has been loaded before so as not to duplicate
	 * @param src
	 */
	public static void loadHtml(String src) {
		//only do anything if the tag hasn't been loaded previously by something else
		if(!tags.contains(src)){
		
		// the source and the tagName are the same thing
		// DIRECTED BY M. NIGHT SHYAMALAN
		String tagName = src;
		
		//adds the html
		String pageContent="<audio id="+tagName+" src="+src+"></audio>";
		FlaxEngine.settings.getContainer().add(new HTML(pageContent));
		
		//adds that tag to the list
		tags.add(tagName);
		
		loadAudioFile(tagName);
		}
	}
	
	private static native void loadAudioFile(String src) /*-{
		// this simply calls the audio JSO load function, because
		// some browsers need to explicitly be told to do this. 
		$doc.getTagById(tagName).load();
	}-*/;
	
	public static  boolean isComponentReady(){
		return true; // for testing only
	}
}