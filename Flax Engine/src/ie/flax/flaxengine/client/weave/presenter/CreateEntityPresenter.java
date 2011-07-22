package ie.flax.flaxengine.client.weave.presenter;

import com.google.gwt.user.client.ui.Widget;

import ie.flax.flaxengine.client.weave.view.CreateEntityView;
import ie.flax.flaxengine.client.weave.view.Impl.CreateEntityViewImpl;

public class CreateEntityPresenter extends AbstractPresenter implements CreateEntityView.presenter {

	private CreateEntityView view;
	
	
	public CreateEntityPresenter()
	{
		this.view = new CreateEntityViewImpl(this); 
	}
	
	
	@Override
	public void setEntityType(String entity) {
	}

	@Override
	public Widget getView() {
		return view.asWidget();
	}

	

}
