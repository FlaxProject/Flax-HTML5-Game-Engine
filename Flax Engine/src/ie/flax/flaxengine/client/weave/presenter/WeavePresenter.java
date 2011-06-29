package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.MainMenuView;
import ie.flax.flaxengine.client.weave.view.TileMenuView;
import ie.flax.flaxengine.client.weave.view.WeaveView;

import com.google.gwt.user.client.ui.HasWidgets;

/**
 * This is the main presenter which control the Main View which is called the WeaveView.
 * @author Ciarán McCann
 *
 */
public class WeavePresenter extends AbstractPresenter{
	
	private TileMenuPresenter TilePresenter;
	private WeaveView display;
	private Weave editor;
	
	
	public WeavePresenter(WeaveView display, Weave editor)
	{
		this.display = display;
		TilePresenter = new TileMenuPresenter(new TileMenuView(),editor);
		display.addToSouth(TilePresenter.asWidget(), "TileMenu");
		
			
		display.addToNorth(new MainMenuView(editor).asWidget()); //None MVP include of UI
	}

	@Override
	public void bind() {
		
	}
	
	public void toggleDisplay() {
		display.toggle();
		
	}

	@Override
	public void go(HasWidgets containerElement) {
		bind();
		//containerElement.add(addWidget) No need for this, this time the view inserts itself into rootpanel;		
	}

}
