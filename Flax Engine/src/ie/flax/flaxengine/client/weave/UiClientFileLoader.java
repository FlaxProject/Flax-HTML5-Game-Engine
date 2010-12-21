package ie.flax.flaxengine.client.weave;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.Graphic;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Ui element which provide file directroy browsing and loading of client side images into the engine.
 * @author Ciarán McCann
 *
 */
public class UiClientFileLoader {

	private HTMLPanel formElements;
	private Button fileuploadButton;

	/**
	 * 
	 * @param upLoaduttonText
	 * @param responseElement
	 */
	public UiClientFileLoader(String upLoaduttonText) {
		fileuploadButton = new Button("Upload");
		fileuploadButton.setTitle("Before clicking I just you want to let you know that if there is no image at the location your probly going to crash the engine.");
		//TODO:Vaildate input

		formElements = new HTMLPanel("<form id=uploadForm><label>Load TileSheet</label>"
				+ "<input type=text id=fileElem  size=32 >" + "</form>");
		formElements.add(fileuploadButton, "uploadForm");

	}

	public native String getUrl()
	/*-{
		return $doc.getElementById("fileElem").value;
	}-*/;
	

	/**
	 * Allows this objects html to be added to the rest of weave
	 * @return
	 */
	public HTMLPanel getElement()
	{
		return formElements;
	}
	
	/**
	 * Returns a ref to the button in this UI element
	 * @return
	 */
	public Button getButton()
	{
		return fileuploadButton;
	}
	
}
