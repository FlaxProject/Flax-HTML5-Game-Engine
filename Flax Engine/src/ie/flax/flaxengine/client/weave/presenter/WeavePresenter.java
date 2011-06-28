package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.MainMenuView;
import ie.flax.flaxengine.client.weave.view.TileMenuView;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;

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
		
		display.getNorth().add(new MainMenuView(editor)); //None MVP include of UI
	}

	@Override
	public void bind() {
		/**
		 * Toggle Editor
		 */
		KeyPressHandler keyPressHandle = new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
					
				 if(event.getNativeEvent().getKeyCode() == 92)
				 {
					 display.toggle();
					 //((WeavePresenter) presenter).toggleDisplay();				 					
					 // running = !running; 
				 }				
			}
		};		
		RootPanel.get().addDomHandler(keyPressHandle, KeyPressEvent.getType());
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
