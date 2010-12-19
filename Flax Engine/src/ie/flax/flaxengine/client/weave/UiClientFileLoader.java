package ie.flax.flaxengine.client.weave;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.Graphic;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;

public class UiClientFileLoader {

	private HTMLPanel formElements;
	private Button fileuploadButton;

	/**
	 * 
	 * @param upLoaduttonText
	 * @param responseElement
	 */
	public UiClientFileLoader(String upLoaduttonText, final String responseElementId) {
		fileuploadButton = new Button("Upload");

		formElements = new HTMLPanel("<form id=uploadForm>"
				+ "<input type=file id=fileElem accept=image/*  >" + "</form>");
		formElements.add(fileuploadButton, "uploadForm");

		fileuploadButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Graphic.loadImageOffline(getSelectedFile(),getFileName());
				respones(responseElementId);

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
	
	private void respones(String responseElementId)
	{
		try {
			DOM.getElementById(responseElementId).setInnerText("loading..");
		} catch (Exception e) {
			FLog.warn("UiClientFileLoder.responese(" + responseElementId + ") has thrown an expection" );
		}
		
	}

	public HTMLPanel getElement()
	{
		return formElements;
	}
	
	
}
