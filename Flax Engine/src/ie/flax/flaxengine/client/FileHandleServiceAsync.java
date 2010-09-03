package ie.flax.flaxengine.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;

/**
 * @author carllange
 *
 */
public interface FileHandleServiceAsync {
	void createFile(String fileName, AsyncCallback<Void> callback);
	void clearFile(String fileName, AsyncCallback<Void> callback);
	void deleteFile(String fileName, AsyncCallback<Void> callback);
	void readFileAsString(String fileName, String id, AsyncCallback<String> callback);
	void writeStringToFile(String stringToWrite, String fileName, AsyncCallback<Void> callback);
}