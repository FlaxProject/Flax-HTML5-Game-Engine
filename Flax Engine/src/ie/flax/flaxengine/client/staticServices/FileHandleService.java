package ie.flax.flaxengine.client.staticServices;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.xml.client.Document;

/**
 * This class is the client-side code for the FileHandling service. Pretty much just a method list.
 * To view the actual code behind the methods, see FileHandleServiceImpl.
 * 
 * @author carllange
 *
 */
@RemoteServiceRelativePath("FileHandleService")
public interface FileHandleService extends RemoteService {
	
	/**
	 * Creates a file on the server in the same directory as the code.
	 * 
	 * @param fileName The name and extension of the file to create.
	 */
	public void createFile(String fileName);
	
	/**
	 * Creates a file on the server in the same directory as the code.
	 * At the moment, this actually deletes and recreates the file.
	 * This will also create a blank file if the file does not yet exist.
	 * This functionality should be changed.
	 * 
	 * @param fileName The name and extension of the file to clear.
	 */
	public void clearFile(String fileName);
	
	/**
	 * Deletes a file on the server which is in the same directory as the code.
	 * This will exit quietly if the file doesn't exist
	 * 
	 * @param fileName The name and extension of the file to delete.
	 * @throws FileNotFoundException Throws this if it can't find the file.
	 */
	public void deleteFile(String fileName);
	
	/**
	 * This returns an XML file as a Document.
	 * May be renamed to parse XML.
	 * 
	 * @param fileName The name and extension of the XML file to parse.
	 * @return
	 * @throws IOException
	 */
	public Document readFileAsXml(String fileName);
	
	/**
	 * This writes a Document to a file
	 * 
	 * @param docToWrite
	 * @param fileName
	 */
	public void writeXmlToFile(Document docToWrite, String fileName);
}