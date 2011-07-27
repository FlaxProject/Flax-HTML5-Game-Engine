package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.weave.UIStrings;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.MainMenuView;
import ie.flax.flaxengine.client.weave.view.WeaveView;
import ie.flax.flaxengine.client.weave.view.Impl.WeaveViewImpl;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTML;
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
	private final PreferencesPresenter preferencePresenter;
	private final WeaveView display;

	public WeavePresenter(Weave editor) {
		display = new WeaveViewImpl();
		TilePresenter = new TileMenuPresenter(editor);
		MiniPresenter = new MiniMapPresenter(editor);
		preferencePresenter = new PreferencesPresenter(editor);
		
		/*
		 * FIXME Switch these back again - tilemenu first, console second.
		 */
		display.addToSouth(FLog.getWidget(), "Console");
		display.addToSouth(TilePresenter.getView(), "TileMenu");
		
	

		display.addToSouthEastCorner(MiniPresenter.getView());

		display.addToEast(new HTML(new SafeHtml() {
			
			@Override
			public String asString() {
				return UIStrings.help();
			}
		}), "Help Menu");
		
		display.addToEast(new HTML(new SafeHtml() {
			
			@Override
			public String asString() {
				return UIStrings.about();
			}
		}), "About");

		display.addToNorth(new MainMenuView(editor).asWidget()); // Non MVP include of UI
		
		display.addToEast(preferencePresenter.getView(), "Preferences");
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
