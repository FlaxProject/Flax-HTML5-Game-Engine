package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.MainMenuView;
import ie.flax.flaxengine.client.weave.view.TileMenuView;
import ie.flax.flaxengine.client.weave.view.WeaveView;
import ie.flax.flaxengine.client.weave.view.Impl.WeaveViewImpl;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the main presenter which control the Main View which is called the
 * WeaveView.
 * 
 * @author Ciaran McCann
 * 
 */
public class WeavePresenter extends AbstractPresenter {
	
	private final MiniMapPresenter MiniPresenter;
	private final TileMenuPresenter TilePresenter;
	private final WeaveView display;

	public WeavePresenter(Weave editor) {
		display = new WeaveViewImpl();
		TilePresenter = new TileMenuPresenter(new TileMenuView(), editor);
		MiniPresenter = new MiniMapPresenter(editor);
		display.addToSouth(TilePresenter.getView(), "TileMenu");
		display.addToSouth(FLog.getWidget(), "Console");

		display.addToSouthEastCornor(MiniPresenter.getView());

		// TODO Carl Create some kind of strings/help file class for this string and others like it.
		// TODO Shoudn't really be using plain html in strings.
		String logo = "<a href='http://flax.ie'><img style='padding-bottom:5px' src='http://flax.ie/images/flaxLogo.png' width='100%'/></a>";
		String quickInstructions;
		String tiling = "<strong>Tiling: </strong> To tile, select a tile from the tilesheet below " +
				"and click on the map on the left. You can use any image as a tilesheet, just press " +
				"<em>Select Tilesheet</em> at the bottom left.";
		String move = "<strong>Tiling on move:</strong> To tile where your mouse is moving to, hold down shift and " +
				"simply move your mouse. Don't forget to select a tile!";
		String regions = "<strong>Tiling regions:</strong> To tile a region, hold down shift and click on the map. "
				+ "Then drag out from that point. You'll see a red box form. "
				+ "When you let go, the tiles in that box will be textured with the current texture.";
		String twitterPlug = "<br/><p style='text-align:center'>You should follow us on Twitter " +
				"<a href='http://twitter.com/flaxproject'>here</a>.";
		String sitePlug = "Read more about this project at <a href='http://flax.ie'>flax.ie</a>!</p>";
		
		quickInstructions = logo + "<br/>" + tiling + "<br/>" + move + "<br/>" + regions + "<br/>" + twitterPlug + "<br/>" + sitePlug;
		display.addToEast(new HTML(quickInstructions), "Help Menu");
		display.addToEast(new Label("Next iteration, sorry!"), "Create Entity Type");
		//display.addToEast(new Label("Next iteration, sorry!"), "Entity Type List");
		display.addToEast(new Label("Next iteration, sorry!"), "Entity List");

		display.addToNorth(new MainMenuView(editor).asWidget()); // None MVP include of UI
	}

	@Override
	public Widget getView() {
		return null;
	}

	/**
	 * Toggles the 3 main weave panels to the north, south and east
	 */
	public void toggleDisplay() {
		display.toggle();

	}

}
