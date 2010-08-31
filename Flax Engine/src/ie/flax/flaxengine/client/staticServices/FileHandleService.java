package ie.flax.flaxengine.client.staticServices;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.xml.client.Document;

/**
 * @author carllange
 *
 */

@RemoteServiceRelativePath("FileHandleService")
public interface FileHandleService extends RemoteService {
	public void createFile(String fileName);
	public void clearFile(String fileName);
	public void deleteFile(String fileName) throws FileNotFoundException;
	public Document readFileAsXml(String fileName) throws IOException;
	public void writeXmlToFile(Document docToWrite, String fileName);
}