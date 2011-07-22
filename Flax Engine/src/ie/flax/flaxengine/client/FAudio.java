package ie.flax.flaxengine.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.HTML;
/**
 * Audio.java
 * @author carllange
 *
 */
public class FAudio {
	
	
	/**
	 * Load Audio file and checks if the file has been loaded before so as not to duplicate
	 * @param src
	 * @param string 
	 */
	public static void play(final String src) {
		Audio s = Audio.createIfSupported();
		s.setSrc(src);
		s.load();
	}
	
	public static  boolean isComponentReady(){
		return true; // for testing only
	}
}