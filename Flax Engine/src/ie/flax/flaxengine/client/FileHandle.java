package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * This class is necessary as some FileHandle methods are done client-side.
 * 
 * @author carllange
 *
 */
public class FileHandle {
	/*
	 * @see ie.flax.flaxengine.client.staticServices.FileHandleService#createFile(java.lang.String)
	 */
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
	
	/*
	 * @see ie.flax.flaxengine.client.staticServices.FileHandleService#clearFile(java.lang.String)
	 */
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
	
	/*
	 * @see ie.flax.flaxengine.client.staticServices.FileHandleService#deleteFile(java.lang.String)
	 */
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
	
	/*
	 * @see ie.flax.flaxengine.client.staticServices.FileHandleService#readFileAsString(java.lang.String)
	 */
	static void readFileAsString(String fileName, String inid) {
		
		FileHandleServiceAsync fh = (FileHandleServiceAsync) GWT.create(FileHandleService.class);
		final String id = inid;
		fh.readFileAsString(fileName, id, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				EventBus.handlerManager.fireEvent(new onFileLoadedEvent(result, id));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Log.error("Could not return file as string. Details follow.");
				Log.error(caught.getMessage());
			}
		});
	}
	
	/*
	 * @see ie.flax.flaxengine.client.staticServices.FileHandleService#writeStringToFile(java.lang.String)
	 */
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
