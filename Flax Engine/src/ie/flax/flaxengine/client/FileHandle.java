package ie.flax.flaxengine.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

/**
 * This class is necessary as some FileHandle methods are done client-side.
 * 
 * @author carllange
 *
 */
public class FileHandle {
	static void createFile(String fileName) {
		FileHandleServiceAsync fh = (FileHandleServiceAsync) GWT.create(FileHandleService.class);
		fh.createFile(fileName, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				Log.info("File was created successfully.");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Log.error("File could not be created. Details follow.");
				Log.error(caught.toString());
			}
		});
	}
	
	static void clearFile(String fileName) {
		FileHandleServiceAsync fh = (FileHandleServiceAsync) GWT.create(FileHandleService.class);
		fh.clearFile(fileName, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				Log.info("File was cleared successfully.");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Log.error("File could not be cleared. Details follow.");
				Log.error(caught.toString());
			}
		});
	}
	
	static void deleteFile(String fileName) {
		FileHandleServiceAsync fh = (FileHandleServiceAsync) GWT.create(FileHandleService.class);
		fh.deleteFile(fileName, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				Log.info("File was deleted successfully.");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Log.error("File could not be deleted. Details follow.");
				Log.error(caught.toString());
			}
		});
	}
	
	static void fileAsString(String fileName) {
		
		FileHandleServiceAsync fh = (FileHandleServiceAsync) GWT.create(FileHandleService.class);

		fh.readFileAsString(fileName, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				//TODO fire event
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Log.error("Could not return file as string. Details follow.");
				Log.error(caught.getMessage());
			}
		});
	}
	
	static Document convertStringToDocument(String inputString){
		return XMLParser.parse(inputString);
	}
	
	static void writeStringToFile(String stringToWrite, String fileName) {
		FileHandleServiceAsync fh = (FileHandleServiceAsync) GWT.create(FileHandleService.class);
		
		fh.writeStringToFile(stringToWrite, fileName, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				Log.info("String was successfully written to a file.");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Log.error("String could not be written to a file. Details follow.");
				Log.error(caught.getMessage());
			}
		});
	}
}
