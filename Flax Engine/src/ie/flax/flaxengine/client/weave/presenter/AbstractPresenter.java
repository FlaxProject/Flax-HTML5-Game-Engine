package ie.flax.flaxengine.client.weave.presenter;

import com.google.gwt.user.client.ui.HasWidgets;

public abstract class AbstractPresenter {
	
	public abstract void bind();
	public abstract void go(final HasWidgets containerElement);

}
