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

import ie.flax.flaxengine.client.staticServices.FileHandleService;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

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
	public Document readFileAsXml(String fileName) throws IOException {
		FileReader fr;
		String fileAsString;
		Document fileAsXml;
		
		// This converts the contents of the file to a String
		// stole this code from http://cl.ly/2Byf
		// TODO Check for compatibility with file encodings
		// TODO Actually understand this
		FileInputStream stream = new FileInputStream(new File(fileName));
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			// Instead of using default, pass in a decoder.
			fileAsString = Charset.defaultCharset().decode(bb).toString();
		}
		finally {
			stream.close();
		}

		fileAsXml = XMLParser.parse(fileAsString);

		return fileAsXml;
	}

	@Override
	public void writeXmlToFile(Document docToWrite, String fileName) {
		// TODO Apparently Document.toString() actually does work. Check for legitimency
		// TODO this deletes files without warning, change to appending
		// TODO this is very iffy, make better.
		deleteFile(fileName);
		createFile(fileName);
		
		try {
			FileWriter fw = new FileWriter(fileName);
			fw.write(docToWrite.toString()); // this may be extremely slow
		} catch (IOException e) {
			Log.error("File could not be written. Details follow.");
			Log.error(e.getMessage());
		}
	}

	//TODO: JSON stuff
}
