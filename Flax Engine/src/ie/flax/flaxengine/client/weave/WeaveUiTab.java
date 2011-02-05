package ie.flax.flaxengine.client.weave;

import com.google.gwt.user.client.ui.FocusPanel;

public class WeaveUiTab extends FocusPanel{

	private final String SHOW_TAB  = "tabShow";
	private final String HIDE_TAB  = "tabHide";
	
	
	public WeaveUiTab(String idElement)
	{
		this.setStyleName(HIDE_TAB);
		this.getElement().setId(idElement);
				
	}
	
	public void show()
	{
		this.setStyleName(SHOW_TAB);
	}
	
	public void hide() {
		
		this.setStyleName(HIDE_TAB);
	}
}
