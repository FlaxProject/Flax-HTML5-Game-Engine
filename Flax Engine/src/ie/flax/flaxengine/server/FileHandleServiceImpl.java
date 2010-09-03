package ie.flax.flaxengine.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import ie.flax.flaxengine.client.FileHandleService;
import ie.flax.flaxengine.client.FlaxEngine;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author carllange
 *
 */
@SuppressWarnings("serial")
public class FileHandleServiceImpl extends RemoteServiceServlet implements FileHandleService {

	
	/* (non-Javadoc)
	 * @see ie.flax.flaxengine.client.staticServices.FileHandleService#createFile(java.lang.String)
	 */
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
		}
		else Log.warn("The file you tried to create already exists.");
	}

	
	/* (non-Javadoc)
	 * @see ie.flax.flaxengine.client.staticServices.FileHandleService#clearFile(java.lang.String)
	 */
	@Override
	public void clearFile(String fileName) {
		// actually deletes the file and creates a new one with the same name
		// replace with actual in-file overwriting if this proves to be annoying.
		deleteFile(fileName);
		createFile(fileName);
	}

	/* (non-Javadoc)
	 * @see ie.flax.flaxengine.client.staticServices.FileHandleService#deleteFile(java.lang.String)
	 */
	@Override
	public void deleteFile(String fileName){
		File fileToDelete = new File(fileName);
		
		// delete the file if it exists. If it doesn't exist, exit silently.
		if (fileToDelete.exists()) fileToDelete.delete();
	}

	/* This converts the contents of the file to a String 
	 * stole some code from http://cl.ly/2Byf 
	 * 
	 * TODO Check for compatibility with file encodings
	 * 
	 * (non-Javadoc)
	 * @see ie.flax.flaxengine.client.staticServices.FileHandleService#readFileAsXml(java.lang.String)
	 */
	@Override
	public String readFileAsString(String fileName) {
		String fileAsString = null;
		
		// TODO clean this shit up, very untidy
		FileInputStream stream = null;
		
		try {
			try {
				stream = new FileInputStream(new File(fileName));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			// Instead of using default, pass in a decoder.
			fileAsString = Charset.defaultCharset().decode(bb).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return fileAsString;
	}

	/* (non-Javadoc)
	 * @see ie.flax.flaxengine.client.staticServices.FileHandleService#writeXmlToFile(com.google.gwt.xml.client.Document, java.lang.String)
	 */
	@Override
	public void writeStringToFile(String stringToWrite, String fileName) {
		createFile(fileName);
		
		try {
			FileWriter fw = new FileWriter(fileName);
			fw.write(stringToWrite);
		} catch (IOException e) {
			Log.error("File could not be written. Details follow.");
			Log.error(e.getMessage());
		}
	}
}