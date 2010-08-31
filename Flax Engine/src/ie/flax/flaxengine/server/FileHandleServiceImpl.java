package ie.flax.flaxengine.server;

import java.io.File;
import java.io.IOException;

import ie.flax.flaxengine.client.staticServices.FileHandleService;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.xml.client.Document;

@SuppressWarnings("serial")
public class FileHandleServiceImpl extends RemoteServiceServlet implements
		FileHandleService {

	@Override
	public void createFile(String fileName) {
		File fileToCreate = new File(fileName);
		
		// if the file doesn't exist, create it.
		if (!fileToCreate.exists()){
			try {
				fileToCreate.createNewFile();
			} catch (IOException e) {
				Log.error("File could not be written. Details follow.");
				Log.error(e.getMessage());
			}
			Log.warn("The file you tried to create already exists.");
		}
	}

	@Override
	public void clearFile(String fileName) {
		// actually deletes the file and creates a new one with the same name
		// TODO replace with actual in-file overwriting if this proves to be annoying.
		deleteFile(fileName);
		createFile(fileName);
	}

	@Override
	public void deleteFile(String fileName){
		File fileToDelete = new File(fileName);
		
		// delete the file if it exists. If it doesn't exist, exit silently.
		if (fileToDelete.exists()) fileToDelete.delete();
	}

	@Override
	public Document readFileAsXml(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeToXml(Document docToWrite, String fileName) {
		// TODO Auto-generated method stub

	}

}
