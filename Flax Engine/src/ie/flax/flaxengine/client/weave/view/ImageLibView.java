package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.user.client.ui.Widget;
/**
 * This is allows for bi-directional commuication between the ImageLib ViewImpl and Presenter
 * @author Ciarán McCann
 *
 */
public interface ImageLibView {
	
	void addItem(String imageName);
	Widget asWidget();
	void clear();
	
	/**
	 * Presenter interface for ImageLib
	 */
	public interface presenter
	{
		void setImageToBeUsed(String imageName);
	}

}
