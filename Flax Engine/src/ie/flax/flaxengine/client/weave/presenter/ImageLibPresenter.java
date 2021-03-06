package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.Graphic.Graphic;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.ImageSelectionEvent;
import ie.flax.flaxengine.client.events.onImageLoadedEvent;
import ie.flax.flaxengine.client.events.onImageLoadedEventHandler;
import ie.flax.flaxengine.client.events.ImageSelectionEvent.Identifier;
import ie.flax.flaxengine.client.weave.view.ImageLibView;
import ie.flax.flaxengine.client.weave.view.Impl.ImageLibViewImpl;

import com.google.gwt.user.client.ui.Widget;

/**
 * This Presneter is responisable for the ImageLibView which displays all the images which are 
 * currently loaded into the engine and can be used to select an image to use as lets say the current tilesheet
 * or if selecting a sprite for an entity etc.
 * 
 * @author Ciaran McCann
 *
 */
public class ImageLibPresenter extends AbstractPresenter implements ImageLibView.presenter , onImageLoadedEventHandler{

	private ImageLibView display;
	private String currentImage;
	private Identifier typeOfImage;
	//private FileUploadPresenter uploadPresenter;
	
	public ImageLibPresenter(Identifier tileSheet)
	{
		this.display = new ImageLibViewImpl(this);
		populate();
		typeOfImage = tileSheet;
		
		EventBus.handlerManager.addHandler(onImageLoadedEvent.TYPE, this); //Register to recive onImageLoadedEvent
		
		//uploadPresenter = new FileUploadPresenter();
		//FIXME Ciaran sort out the nested view/presenter stuff for the new MVP model
		
		//FIXME CIARAN you really really need to finished the convert from old MVP, and remove bugs
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

	@Override
	public Widget getView() {
		populate();
		return display.asWidget();
	}

	@Override
	public void onImageLoaded(onImageLoadedEvent e) {
		populate();
		
	}

}
