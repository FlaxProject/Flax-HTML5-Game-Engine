package ie.flax.flaxengine.client.staticServices;

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
	void readFileAsXml(String fileName, AsyncCallback<Document> callback);
	void writeXmlToFile(Document docToWrite, String fileName, AsyncCallback<Void> callback);
}
