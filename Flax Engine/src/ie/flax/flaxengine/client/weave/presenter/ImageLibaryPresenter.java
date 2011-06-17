package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.Graphic.Graphic;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * Handles the logoic for the image libary vew
 * @author Ciarán McCann
 *
 */
public class ImageLibaryPresenter extends AbstractPresenter {

	private Display display;
	
	
	public interface Display
	{
		void addTreeItem(TreeItem item);		
		Widget asWidget();
	}
	
	public ImageLibaryPresenter(Display display)
	{
		this.display = display;
		
		/*for(String imageName : Graphic.getSingleton().getKeys())
		{
			TreeItem temp = new TreeItem();
			temp.setText(imageName);
			display.addTreeItem(temp);
		}
		*/
		display.addTreeItem(new TreeItem("dfd"));
	}
	
	@Override
	public void bind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void go(HasWidgets containerElement) {
		bind();
		containerElement.add(display.asWidget());
		
	}

}
