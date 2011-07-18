package ie.flax.flaxengine.client.weave.view;

import ie.flax.flaxengine.client.weave.Weave;
import ie.flax.flaxengine.client.weave.presenter.AbstractPresenter;
import ie.flax.flaxengine.client.weave.presenter.FileUploadPresenter;
import ie.flax.flaxengine.client.weave.presenter.MapImportExportPresenter;
import ie.flax.flaxengine.client.weave.view.customwidgets.FWindow;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

/**
 * This is the menu bar in the north panel of Weave. Its not really done in MVP,
 * may want to look into converting it sometime soon
 * 
<<<<<<< HEAD
 * @author Ciaran McCann
 *
=======
 * @author Ciarán McCann
 * 
>>>>>>> refs/remotes/origin/master
 */
public class MainMenuView extends MenuBar {

	private MenuBar file;
	private Command uploadFile;
	private Command exportImport;
	private Weave model;
	private FWindow window;
	
	private AbstractPresenter fileuploadPresneter;
	private AbstractPresenter ImportExportPresenter;

    /**
     * Binds the commands to the menu items
     */
    private void bindCommands() {

        // TODO: Async this code, some reason not working atm. No bige for the
        // mo
        // GWT.runAsync(new RunAsyncCallback() {
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

                window.setTitle("Upload Tilesheet");
                window.add(fileuploadPresneter.getView());
                window.show();

            }
        };
        // }
    }

    @Override
    public void focus() {
        // ignore anything that gives focus
    }

}
