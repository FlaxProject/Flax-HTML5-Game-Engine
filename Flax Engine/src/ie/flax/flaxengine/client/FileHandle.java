package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

/**
 * This class handles files (surprise!). Essentially, it sends HTTP requests to the PHP backend.
 * Easy.
 * 
 * @author carllange
 *
 */
public class FileHandle {

	static void readFileAsString(String fileURL, final String eventID) {
		//send GET request
		String url = fileURL;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));

		try {
		  Request request = builder.sendRequest(null, new RequestCallback() {
		    public void onError(Request request, Throwable exception) {
		       // Couldn't connect to server (could be timeout, SOP violation, etc.)     
		    }

		    public void onResponseReceived(Request request, Response response) {
		      if (200 == response.getStatusCode()) {
		    	 // Log.info(response.getText());
		    	  EventBus.handlerManager.fireEvent(new onFileLoadedEvent(response.getText(), eventID));
		      } else {
		    	  //TODO: make these error messages more explanatory
		    	  FLog.error("An error occurred accessing the server. The error was " + response.getStatusCode());
		      }
		    }       
		  });
		} catch (RequestException e) {
			FLog.error("An error occurred while connecting to the server.");
		}
		
		
	}
	
	public static void writeStringToFile(String fileName, String stringToWrite, final String eventID) {
		//quick warning: this will not work unless a game is initialised
		
		//a small guard against a zero or null UID
		if (FlaxEngine.settings.getUID() == "0"){
			FLog.warn("Your UID is set incorrectly. Don't use a UID of 0. A UID of 0 is insecure. Change it in your Settings file or in your Settings code.");
		} else if (FlaxEngine.settings.getUID() == null){
			FLog.error("You don't have a UID set! Change that in your settings file or code, and don't forget to configure server.php the same way!");
		}
		
		//a guard against a null server path
		if (FlaxEngine.settings.getServerPath() == null){
			FLog.error("You have no server path set! Change that in your settings file or code!");
		}
		
		String url = FlaxEngine.settings.getServerPath();
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		
		try {
			builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
			//TODO change the UID=0 to FlaxEngine.settings.getUID(); when using properly
			Request response = builder.sendRequest("UID=0"+"&fileName="+fileName+"&fileContents="+stringToWrite, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
					FLog.error("Error while connecting to server.");
				}

				public void onResponseReceived(Request request, Response response) {
					FLog.info("Response recieved");
					FLog.info(response.getText());
					EventBus.handlerManager.fireEvent(new onFileLoadedEvent(response.getText(), eventID));
				}
			});
		} catch (RequestException e) {
			
		}

	}
}
