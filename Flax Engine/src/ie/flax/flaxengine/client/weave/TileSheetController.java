package ie.flax.flaxengine.client.weave;

import ie.flax.flaxengine.client.FLog;
import ie.flax.flaxengine.client.FMap;
import ie.flax.flaxengine.client.Graphic;
import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;
import ie.flax.flaxengine.client.events.onImageLoadedEvent;
import ie.flax.flaxengine.client.events.onImageLoadedEventHandler;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Ui element which provide file directroy browsing and loading of client side images into the engine.
 * @author Ciarán McCann
 *
 */
public class TileSheetController extends Controller implements onImageLoadedEventHandler {

	private HTMLPanel formElements;
	private Button fileuploadButton;

	/**
	 * 
	 * @param upLoaduttonText
	 * @param responseElement
	 */
	public TileSheetController(final Weave referenceToTheEditor) {
		
		/**
		 * Create canvas to view the tilesheets
		 */
		Graphic.getSingleton().createCanvas("Weave","300px","300px");	
		
		//Setting up click functionality for the weave tileSheet
		
		
		
		Graphic.getSingleton().getCanvas("Weave").getCanvas().addMouseDownHandler( new MouseDownHandler() {		
		@Override
		public void onMouseDown(MouseDownEvent event) {
			
			int tileSize = Weave.getFMapReference().getTileSize();
			int numberOfTilesInaRow = (Graphic.getSingleton().getImage(Weave.getFMapReference().getTileSheet()).getWidth())/tileSize;
			
			
			int x = event.getX()/tileSize;
			int y = event.getY()/tileSize;
			
			referenceToTheEditor.getCurrentTile().setTexture((y*numberOfTilesInaRow)+x);
			
			}
		});
		
		

		//Registers to listen for on image load event
		EventBus.handlerManager.addHandler(onImageLoadedEvent.TYPE, this);
		
		fileuploadButton = new Button("Upload");
		fileuploadButton.setTitle("Before clicking I just you want to let you know that if there is no image at the location your probly going to crash the engine.");
		//TODO:Vaildate input

		formElements = new HTMLPanel("<form id=uploadForm><label>Load TileSheet</label>"
				+ "<input type=text id=fileElem  size=32 >" + "</form><div id=viewer ></div>");
		formElements.add(fileuploadButton, "uploadForm");
		
		formElements.add(Graphic.getSingleton().getCanvas("Weave").getCanvas().asWidget(), "viewer");
		
		
		//Action to take when the user clicks tileSheetUploader button
		fileuploadButton.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				Graphic.getSingleton().loadImage(getUrl());
				Weave.getFMapReference().setTileSheet(getUrl());
				
			}
		});	
		
	}
	

	/**
	 * Gets the URL to the image the user entered
	 * @return
	 */
	private native String getUrl()
	/*-{
		return $doc.getElementById("fileElem").value;
	}-*/;
	

	/**
	 * Allows this objects html to be added to the rest of weave
	 * @return
	 */
	public HTMLPanel getHTML()
	{
		return formElements;
	}


	/**
	 * Once the image which was loaded into the engine by the user is loaded then draw it in weave tilesheet canvas
	 */
	@Override
	public void onImageLoaded(onImageLoadedEvent e) {
		
		if(e.getImageName() == Weave.getFMapReference().getTileSheet())
		{
		//Graphic.getSingleton().getCanvas("Weave").resize(Graphic.getSingleton().getImage(Weave.getFMapReference().getTileSheet()).getWidth(), Graphic.getSingleton().getImage(Weave.getFMapReference().getTileSheet()).getHeight());
		Graphic.getSingleton().getCanvas("Weave").drawImage(Weave.getFMapReference().getTileSheet(), 0, 0);
		}
		
	}
	
}
