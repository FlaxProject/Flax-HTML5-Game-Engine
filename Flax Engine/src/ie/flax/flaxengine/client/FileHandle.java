package ie.flax.flaxengine.client;

import ie.flax.flaxengine.client.events.EventBus;
import ie.flax.flaxengine.client.events.onFileLoadedEvent;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.storage.client.Storage;

/**
 * This class handles files (surprise!). You can read and write files from a
 * server specified in {@link Settings}, or html5 local storage. You can also
 * clear local storage and delete key-value pairs.
 * 
 * @author carllange
 */
public class FileHandle {

    private static Storage storage = Storage.getLocalStorageIfSupported();

    /**
     * This reads a file as a String on the the server specified in
     * {@link Settings}. It then fires an {@link onFileLoadedEvent} with the
     * contents of the file.
     * 
     * @param fileURL
     *            TODO find out if this is relative of absolute.
     * @param eventID
     *            TODO what is this again?
     */
    static void readFileAsString(String fileURL, final String eventID) {
        // send GET request
        String url = fileURL;
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
                URL.encode(url));

        try {
            Request request = builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    FLog.error("Couldn't connect to server (could be timeout, SOP violation, etc.)");
                }

                public void onResponseReceived(Request request,
                        Response response) {
                    if (200 == response.getStatusCode()) {
                        // 200 is a success response
                        EventBus.handlerManager
                                .fireEvent(new onFileLoadedEvent(response
                                        .getText(), eventID));
                    } else {
                        // TODO: make these error messages more explanatory
                        FLog.error("An error occurred accessing the server. It was a "
                                + response.getStatusCode());
                    }
                }
            });
        } catch (RequestException e) {
            FLog.error("An error occurred while connecting to the server.");
        }

    }

    /**
     * Writes a string to a file on the server. TODO check security on this.
     * 
     * @param fileName
     *            The name of the file to write.
     * @param stringToWrite
     *            The string to write
     * @param eventID
     */
    public static void writeStringToFile(String fileName, String stringToWrite,
            final String eventID) {
        // quick warning: this will not work unless a game is initialised

        // a small guard against a zero or null UID
        if (FlaxEngine.settings.getUID() == "0") {
            FLog.warn("Your UID is set incorrectly. Don't use a UID of 0. A UID of 0 is insecure. Change it in your Settings file or in your Settings code.");
        } else if (FlaxEngine.settings.getUID() == null) {
            FLog.error("You don't have a UID set! Change that in your settings file or code, and don't forget to configure server.php the same way!");
        }

        // a guard against a null server path
        if (FlaxEngine.settings.getServerPath() == null) {
            FLog.error("No server path is set! Change that in your settings file or code!");
        }

        String url = FlaxEngine.settings.getServerPath();

        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);

        try {
            builder.setHeader("Content-Type",
                    "application/x-www-form-urlencoded");
            // TODO change the UID=0 to FlaxEngine.settings.getUID(); when using
            // properly
            Request response = builder.sendRequest("UID=0" + "&fileName="
                    + fileName + "&fileContents=" + stringToWrite,
                    new RequestCallback() {

                        public void onError(Request request, Throwable exception) {
                            FLog.error("Couldn't connect to server (could be timeout, SOP violation, etc.)");
                        }

                        // TODO: wtf does this do, why does it fire that event?
                        public void onResponseReceived(Request request,
                                Response response) {
                            FLog.info("Response recieved");
                            FLog.info(response.getText());
                            EventBus.handlerManager
                                    .fireEvent(new onFileLoadedEvent(response
                                            .getText(), eventID));
                        }
                    });
        } catch (RequestException e) {

        }

    }

    /**
     * This writes a string to local storage. It will log, but otherwise quietly
     * fail if local storage does not exist.
     * 
     * @param key
     *            The key to the key-value-pair in local storage.
     * @param stringToWrite
     *            The value to the key-value-pair in local storage.
     */
    public static void writeStringToLocalStorage(String key,
            String stringToWrite) {
        if (storage != null) {
            storage.setItem(key, stringToWrite);
        } else {
            FLog.error("Local storage doesn't work and there's no fallback!! writeStringToLocalStorage COMPLETELY FAILED");
        }
    }

    /**
     * This reads a string to local storage. On any failure this returns null.
     * It will log if local storage does not exist. It will log if the key given
     * does not exist or if its value is null.
     * 
     * @param key
     *            The key to the key-value-pair in local storage.
     */
    public static String readStringFromLocalStorage(String key) {
        String r = null;
        if (storage != null) {
            r = storage.getItem(key);
            if (r == null) {
                FLog.warn("The key given to localStorage does not have a value associated with it or does not exist.");
            }
        } else {
            FLog.error("Local storage doesn't work and there's no fallback!! readStringFromLocalStorage COMPLETELY FAILED");
        }
        return r;
    }

    /**
     * This removes a string from local storage. It will log, but otherwise
     * quietly fail if local storage does not exist.
     * 
     * @param key
     *            The key to the key-value-pair in local storage.
     */
    public static void removeStringFromLocalStorage(String key) {
        if (storage != null) {
            storage.removeItem(key);
        } else {
            FLog.error("Local storage doesn't work and there's no fallback!! removeStringFromLocalStorage COMPLETELY FAILED");
        }
    }

    /**
     * Clears local storage (in case you expected it to blow shit up)
     */
    public static void clearLocalStorage() {
        if (storage != null) storage.clear();
    }
}
