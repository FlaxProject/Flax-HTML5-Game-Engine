package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.WeaveView;
import ie.flax.flaxengine.client.weave.view.MainMenuView;
import ie.flax.flaxengine.client.weave.view.MapImportExportView;
import ie.flax.flaxengine.client.weave.view.TileMenuView;

import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class WeavePresenter extends AbstractPresenter{
	
	Display display;
	
	public interface Display {
		HasWidgets getNorth();
		HasWidgets getSouth();
		HasWidgets getEast();
		void toggle();
	}
	
	public WeavePresenter(Display display, Weave editor, FMap model)
	{
		this.display = display;
		AbstractPresenter TilePresenter = new TileMenuPresenter(new TileMenuView(),editor);
		TilePresenter.go(display.getSouth());
		
		display.getNorth().add(new MainMenuView()); //None MVP include of UI
	}

	@Override
	public void bind() {
		
		KeyPressHandler keyPressHandle = new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				
		
				 if(event.getNativeEvent().getKeyCode() == 92)
				display.toggle();
				
			}
		};
		
		RootPanel.get().addDomHandler(keyPressHandle, KeyPressEvent.getType());
		
	}

	@Override
	public void go(HasWidgets containerElement) {
		bind();
		//containerElement.add(addWidget) No need for this, this time the view inserts itself into rootpanel;		
	}

}
