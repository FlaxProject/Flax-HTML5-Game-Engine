package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.weave.presenter.AbstractPresenter;
import ie.flax.flaxengine.client.weave.view.ImageLibView.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.ListBox;

public class CreateEntityView extends Composite {

	
	private static CreateEntityViewUiBinder uiBinder = GWT.create(CreateEntityViewUiBinder.class);
	private AbstractPresenter presenter;
	
	@UiField VerticalPanel vPanel;
	@UiField Button selectSpriteButton;
	@UiField ListBox entityTypeDropDown;

	interface CreateEntityViewUiBinder extends
			UiBinder<Widget, CreateEntityView> {
	}

	public CreateEntityView(AbstractPresenter presenter) {
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
	}

	@UiHandler("selectSpriteButton")
	void onSelectSpriteButtonClick(ClickEvent event) {
		
	}
}
