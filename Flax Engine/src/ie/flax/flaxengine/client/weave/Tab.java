package ie.flax.flaxengine.client.weave;

import ie.flax.flaxengine.client.FLog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Tab class makes it extremely easy and fast to add new tabs with spefic tasks
 * to the weave menu. Once constructed a tab is automatically inserted into the 
 * weave menu div, with events registered for hiding and showing of the tab.
 * 
 * It is also given a controller, which preforms a very spefic task. For example
 * the TileSheetController.
 * 
 * @author Ciarán McCann
 *
 */
public class Tab extends FocusPanel{

	/**
	 * CSS string consts for animations
	 */
	private final String SHOW_TAB  = "tabShow";
	private final String HIDE_TAB  = "tabHide";
	
	/**
	 * 
	 * @param tabName - Text you wish to appear in the tab label
	 * @param weaveUIdiv - reference to the weave main UI div
	 * @param controller - controller which preforms a spefic task
	 */
	public Tab(final String tabName, HTMLPanel weaveUIdiv, Controller controller)
	{
		Label tabLabel = setupTab(tabName);
		
		// The contoller is the UI items which preform a very spefic task
		this.add(controller.getHTML()); 
		
		//Adds the tab label auotmatically to the weave panel and menu
		weaveUIdiv.add(tabLabel, WeaveUiManager.MENU);
		weaveUIdiv.add(this, WeaveUiManager.WEAVE);
						
	}
	
	
	/**
	 * This only exists to accomplate the console log 
	 * @param tabName - Text you wish to appear in the tab label
	 * @param weaveUIdiv - reference to the weave main UI div
	 * @param w - allows the inserting of widgets into the tab panel
	 */
	public Tab(final String tabName, HTMLPanel weaveUIdiv, Widget w)
	{
		Label tabLabel = setupTab(tabName);
		
		//Adds a widget type to the weave tab panel
		this.add(w);
		
		/**
		 * Adds the tab label auotmatically to the weave panel and menu
		 */
		weaveUIdiv.add(tabLabel, "menu");
		weaveUIdiv.add(this, "weave");
						
	}
	
	

	/**
	 * This contructs the tabs, placing a label in the menu and setting up an
	 * event call back to show/hide the tab
	 * @param tabName
	 * @return
	 */
	private Label setupTab(final String tabName) {
		this.setStyleName(HIDE_TAB); 		//Sets default hidden state of tab
		this.getElement().setId(tabName);	//Sets the elements id
		
		Label tabLabel =  new Label(tabName);
		tabLabel.setStyleName("label");
		
		/**
		 * When the tabs lable is click it will show it
		 */
		tabLabel.addClickHandler( new ClickHandler() {		
			@Override
			public void onClick(ClickEvent event) {
				
				show(tabName);		
			}
		});
		return tabLabel;
	}
	
	
	/**
	 * This code shows THIS tab and hides all other tabs
	 * @param tab
	 */
	public native void show(String tab)
    /*-{
            
            var arrayOfelemnets = $doc.getElementsByTagName("div")
    
    for( var i = 0; i < arrayOfelemnets.length; i++)
    {
                    if(arrayOfelemnets[i].className == 'tabShow')
                    arrayOfelemnets[i].className = 'tabHide';
    
    }
    //TODO: Rewrite in GWT code - be faster
    
    if($doc.getElementById(tab).className == 'tabHide')
    $doc.getElementById(tab).className = 'tabShow';
    else
    $doc.getElementById(tab).className = 'tabHide';
    }-*/;
	
	
	/**
	 * Hides all the tabs
	 */
	public void hide() {
		
		this.setStyleName(HIDE_TAB);
	}
}
