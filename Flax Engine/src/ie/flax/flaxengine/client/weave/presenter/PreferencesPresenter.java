package ie.flax.flaxengine.client.weave.presenter;

import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.view.PreferencesView;
import ie.flax.flaxengine.client.weave.view.Impl.PreferencesViewImpl;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;

public class PreferencesPresenter extends AbstractPresenter implements PreferencesView.presenter{
	private PreferencesView view;
	private Weave model;
	
	private Timer autosaveTimer = new Timer() {
		@Override
		public void run() {
			model.localStoreCurrentMap();
		}
	};
	
	public PreferencesPresenter(Weave editor) {
		view = new PreferencesViewImpl(this);
		model = editor;
	}
	
	@Override
	public void setAutosave(boolean on) {
		if (on) {
			autosaveTimer.scheduleRepeating(6000);
		} else if (!on) {
			autosaveTimer.cancel();
		}
	}

	@Override
	public Widget getView() {
		return view.asWidget();
	}

}
