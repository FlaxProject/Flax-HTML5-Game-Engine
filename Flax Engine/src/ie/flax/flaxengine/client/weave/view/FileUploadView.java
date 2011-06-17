package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HTMLPanel;

import ie.flax.flaxengine.client.weave.presenter.*;

public class FileUploadView extends Composite implements FileUploadPresenter.Display {

	private static FileUploadViewUiBinder uiBinder = GWT
			.create(FileUploadViewUiBinder.class);
	@UiField Button UploadButton;
	@UiField Label feedback;
	@UiField TextBox urlBox;

	interface FileUploadViewUiBinder extends UiBinder<Widget, FileUploadView> {
	}

	public FileUploadView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public FileUploadView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		UploadButton.setText(firstName);
	}

	@Override
	public HasClickHandlers getUploadButton() {
		return UploadButton;
	}

	@Override
	public void setFeedback(String msg, String style) {
			feedback.setText(msg);
			feedback.setStyleName(style);
	}

	@Override
	public String getUrl() {
		return urlBox.getText();
	}

	@Override
	public Widget asWidgets() {
		return this;
	}

	@Override
	public HasKeyPressHandlers getUrlBox() {
		return urlBox;
	}


	

}
