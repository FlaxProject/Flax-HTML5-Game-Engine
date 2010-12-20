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
	public UiClientFileLoader(String upLoaduttonText, final FMap map) {
		fileuploadButton = new Button("Upload");

		formElements = new HTMLPanel("<form id=uploadForm><label id=state></label>"
				+ "<input type=file id=fileElem accept=image/*  >" + "</form>");
		formElements.add(fileuploadButton, "uploadForm");

		fileuploadButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//Graphic.loadImageOffline(getSelectedFile(),getFileName());
				respones("Currently loading image " + getFileName()  +" - Loading...");	
				map.setTileSheet(getFileName());
			}
		});

	}

	private native JavaScriptObject getSelectedFile()
	/*-{
		return $doc.forms["uploadForm"]["fileElem"].files[0];
	}-*/;
	
	private native String getFileName()
	/*-{
		return $doc.forms["uploadForm"]["fileElem"].files[0].name;
	}-*/;
	
	private void respones(String msg)
	{
		FLog.info(msg);			
	}
	
	/**
	 * Allows this objects html to be added to the rest of weave
	 * @return
	 */
	public HTMLPanel getElement()
	{
		return formElements;
	}
	
	
}
