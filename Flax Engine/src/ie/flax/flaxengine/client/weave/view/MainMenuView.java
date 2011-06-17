package ie.flax.flaxengine.client.weave.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;

import ie.flax.flaxengine.client.Graphic;
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

		//TODO: Async this code, some reason not working atm. No bige for the mo
		//GWT.runAsync(new RunAsyncCallback() {

				

				 AbstractPresenter exportImportPresenter = new MapImportExportPresenter(new MapImportExportView());

				exportImport = new Command() {

					@Override
					public void execute() {
						 Window.alert(Graphic.getSingleton().isComponentReady()+"");

					}
				};

				uploadFile = new Command() {

					@Override
					public void execute() {

						AbstractPresenter fileuploadPresneter = new FileUploadPresenter(new FileUploadView());
						FWindow window = new FWindow("File Upload");
						fileuploadPresneter.go(window.asWdidget());

					}
				};

			}

	
	}


