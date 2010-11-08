package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;

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
		    	  Log.info(response.getText());
		    	  EventBus.handlerManager.fireEvent(new onFileLoadedEvent(response.getText(), eventID));
		      } else {
		    	  //TODO: make these error messages more explanatory
		    	  Log.error("An error occurred accessing the server. The error was " + response.getStatusCode());
		      }
		    }       
		  });
		} catch (RequestException e) {
			Log.error("An error occurred while connecting to the server.");
		}
		
		
	}
	
	static void writeStringToFile(String fileName, String stringToWrite, final String eventID) {
		String url = "http://flax.ie/private/server.php";
		
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
		
		try {
			builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
			//TODO change the UID=0 to FlaxEngine.settings.getUID(); when using properly
			Request response = builder.sendRequest("UID=0"+"&fileName="+fileName+"&fileContents="+stringToWrite, new RequestCallback() {

				public void onError(Request request, Throwable exception) {
					Log.error("Error while connecting to server.");
				}

				public void onResponseReceived(Request request, Response response) {
					Log.info("Response recieved");
					Log.info(response.getText());
					EventBus.handlerManager.fireEvent(new onFileLoadedEvent(response.getText(), eventID));
				}
			});
		} catch (RequestException e) {
			
		}

	}
}
