package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.MainMenuView;
import ie.flax.flaxengine.client.weave.view.TileMenuView;
import ie.flax.flaxengine.client.weave.view.WeaveView;
import ie.flax.flaxengine.client.weave.view.Impl.WeaveViewImpl;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the main presenter which control the Main View which is called the
 * WeaveView.
 * 
 * @author Ciarán McCann
 * 
 */
public class WeavePresenter extends AbstractPresenter{
	
	private TileMenuPresenter TilePresenter;
	private WeaveView display;
		
	
	public WeavePresenter(Weave editor)
	{
		this.display = new WeaveViewImpl(); 
		TilePresenter = new TileMenuPresenter(new TileMenuView(), editor);
		MiniPresenter = new MiniMapPresenter(editor);
		display.addToSouth(TilePresenter.getView(), "TileMenu");
		display.addToSouth(FLog.getWidget(), "Console");
		display.addToSouth(MiniPresenter.getView(), "Min-Map Experiment");

		// TODO Carl Create some kind of strings/help file class for this string
		// and others like it.

		String quickInstructions = "To tile a region, whole down shift and click on the map"
				+ "Then dragg out from that piont, you will see a red box form"
				+ "When you let go the tiles will be textured with the current texture"
				+ "in that region";

		display.addToEast(new Label(quickInstructions), "Help Menu");
		display.addToEast(new Label("Widget - 1"), "Create Entity Type");
		display.addToEast(new Label("Widget - 2"), "Entity Type List");
		display.addToEast(new Label("Widget - 3"), "Entity List");

		display.addToNorth(new MainMenuView(editor).asWidget()); // None MVP
																	// include
																	// of UI
	}



	@Override
	public Widget getView() {
		return null;
	}

}
