package ie.flax.flaxengine.client.weave.view.Impl;

import ie.flax.flaxengine.client.weave.presenter.CreateEntityPresenter;
import ie.flax.flaxengine.client.weave.view.CreateEntityView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class CreateEntityViewImpl extends Composite implements CreateEntityView {

	private static CreateEntityViewImplUiBinder uiBinder = GWT.create(CreateEntityViewImplUiBinder.class);
	private CreateEntityPresenter presenter;
	
	@UiField Image spriteImage;
	@UiField Button selectSpriteButton;


	interface CreateEntityViewImplUiBinder extends
			UiBinder<Widget, CreateEntityViewImpl> {
	}

	public CreateEntityViewImpl(CreateEntityPresenter presenter) {
		initWidget(uiBinder.createAndBindUi(this));
		this.presenter = presenter;
	}


	@UiHandler("selectSpriteButton")
	void onSelectSpriteButtonClick(ClickEvent event) {
		
		//presenter.setEntityType(entity);		
	}


	@Override
	public void setImage(String Path) {
	}
}
