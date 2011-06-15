package ie.flax.flaxengine.client.weave.presenter;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.MenuBar;

public class MainMenuPresenter extends AbstractPresenter {
	
	public interface Display
	{
		void addItem(String Name, Command cmd);
		void addItem(String Name, MenuBar cmd);
	}

	@Override
	public void bind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void go(HasWidgets containerElement) {
		// TODO Auto-generated method stub
		
	}

}
