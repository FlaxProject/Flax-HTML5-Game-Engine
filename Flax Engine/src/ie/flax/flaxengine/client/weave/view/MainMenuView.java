package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;

import ie.flax.flaxengine.client.weave.presenter.*;
import ie.flax.flaxengine.client.weave.view.customwidgets.FWindow;

public class MainMenuView extends MenuBar {

	MenuBar file;
	Command uploadFile;
	Command exportImport;

	public MainMenuView() {
		file = new MenuBar();
		bindCommands();

		this.addItem("File", file);
		this.addItem("File Upload", uploadFile);
		this.addItem("Export/Import Map", exportImport);

		this.setAnimationEnabled(true);
	}

	private void bindCommands() {

		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				

				final AbstractPresenter exportImportPresenter = new MapImportExportPresenter(new MapImportExportView());

				exportImport = new Command() {

					@Override
					public void execute() {
						// TODO Auto-generated method stub

					}
				};

				uploadFile = new Command() {

					@Override
					public void execute() {

						FWindow window = new FWindow("File Upload");
						exportImportPresenter.go(window.asWdidget());

					}
				};

			}

			@Override
			public void onFailure(Throwable reason) {
				// TODO Auto-generated method stub

			}
		});
	}

}
