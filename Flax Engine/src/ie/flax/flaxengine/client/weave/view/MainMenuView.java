package ie.flax.flaxengine.client.weave.view;

import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.presenter.AbstractPresenter;
import ie.flax.flaxengine.client.weave.presenter.FileUploadPresenter;
import ie.flax.flaxengine.client.weave.presenter.MapImportExportPresenter;
import ie.flax.flaxengine.client.weave.view.customwidgets.FWindow;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

/**
 * This is the menu bar in the north panel of Weave.
 * Its not really done in MVP, may want to look into converting it sometime soon
 * 
 * @author Ciaran McCann
 *
 */
public class MainMenuView extends MenuBar {

	private MenuBar file;
	private Command uploadFile;
	private Command exportImport;
	private Weave model;
	private FWindow window;
	
	private AbstractPresenter fileuploadPresneter;
	private AbstractPresenter ImportExportPresenter;

	public MainMenuView(Weave model) {
		
		this.model = model;
		
		file = new MenuBar();
		bindCommands();

		//this.addItem("File", file);
		this.addItem("File Upload", uploadFile);
		this.addItem("Export/Import Map", exportImport);
		this.setAnimationEnabled(true);
		
		window = new FWindow("Window");
	
		fileuploadPresneter = new FileUploadPresenter();
		ImportExportPresenter = new MapImportExportPresenter(model);
	}

	/**
	 * Binds the commands to the menu items
	 */
	private void bindCommands() {

		//TODO: Async this code, some reason not working atm. No bige for the mo
		//GWT.runAsync(new RunAsyncCallback() {
			exportImport = new Command() {

					@Override
					public void execute() {
	
						window.setTitle("Import/Export Map");	
						window.add(ImportExportPresenter.getView());
						window.show();

					}
				};

				uploadFile = new Command() {

					@Override
					public void execute() {
					
							window.setTitle("Import/Export Map");								
							window.add(fileuploadPresneter.getView());						
							window.show();

					}
				};
			}	
	}


