package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.ImageSelectionEvent;
import ie.flax.flaxengine.client.events.ImageSelectionEvent.Idenfiter;
import ie.flax.flaxengine.client.weave.view.ImageLibView;
import ie.flax.flaxengine.client.weave.view.Impl.ImageLibViewImpl;

import com.google.gwt.user.client.ui.HasWidgets;

/**
 * This Presneter is responisable for the ImageLibView which displays all the images which are 
 * currently loaded into the engine and can be used to select an image to use as lets say the current tilesheet
 * or if selecting a sprite for an entity etc.
 * 
 * @author Ciaran McCann
 *
 */
public class ImageLibPresenter extends AbstractPresenter implements ImageLibView.presenter {

	private ImageLibView display;
	private String currentImage;
	private Idenfiter typeOfImage;
	
	public ImageLibPresenter(Idenfiter tileSheet)
	{
		this.display = new ImageLibViewImpl(this);
		populate();
		typeOfImage = tileSheet;
	}
	
	/**
	 * Gets the currently selected image
	 * @return
	 */
	public String getImageToBeUsed()
	{
		return currentImage;
	}
	
	@Override
	public void setImageToBeUsed(String imageName) {
				this.currentImage = imageName;
				
				EventBus.handlerManager.fireEvent(new ImageSelectionEvent(imageName,typeOfImage));
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void go(HasWidgets containerElement) {
		containerElement.add(display.asWidget());
	}
	
	
	/**
	 * Populates the tree with updated information from the graphics lib.
	 * IE - The image url which have been loaded 
	 */
	public void populate()
	{
		display.clear();
		for(String image : Graphic.getSingleton().getImagesHashMap().keySet())
		{		
			display.addItem(image);
		}
	}

}