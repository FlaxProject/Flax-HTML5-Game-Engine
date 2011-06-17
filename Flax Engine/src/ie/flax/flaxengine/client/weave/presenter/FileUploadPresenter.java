package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.Graphic;
import ie.flax.flaxengine.client.weave.view.ImageLibaryView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The presenter which handles the logic of the fileupload view
 * @author Ciarán McCann
 *
 */
public class FileUploadPresenter extends AbstractPresenter {
	
	private final String SUCESSFUL_LOAD = "postive";
	private Display display;
	
	public interface Display
	{
		HasClickHandlers getUploadButton();
		HasKeyPressHandlers getUrlBox();
		HasClickHandlers getUrlBoxClick();
		void setFeedback(String msg, String styleName);
		String getUrl();
		void clearUrlBox();
	
		Widget asWidgets();
	}
	
	
	public FileUploadPresenter(Display display)
	{
		this.display = display;
	}

	@Override
	public void bind() {
		
		display.getUploadButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {				
				loadprocess();				
			}
		});
		
		
		display.getUrlBox().addKeyPressHandler( new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				
				if(event.getCharCode() == KeyCodes.KEY_ENTER)
					loadprocess();
				
			}
		});
		
		display.getUrlBoxClick().addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				display.clearUrlBox();
				
			}
		});
		
		
	}

	@Override
	public void go(HasWidgets containerElement) {
		bind();
		containerElement.add(display.asWidgets());
		
	}
	
	private void loadprocess()
	{
		if(display.getUrl() != null)
		{
			
			display.setFeedback("Image Loading...",""); 
			Graphic.getSingleton().loadImage(display.getUrl()).addLoadHanderl( new LoadHandler() {
				
				@Override
				public void onLoad(LoadEvent event) {
			
					display.setFeedback("Image Loaded", SUCESSFUL_LOAD);
					
					AbstractPresenter ImageLibaryPresenter = new ImageLibaryPresenter( new ImageLibaryView() );
					ImageLibaryPresenter.go(RootPanel.get());
					
				}
			});
		}
	
	}

}
