package helper;

import java.io.File;
import java.util.Date;

public class Helper { // This is a helper class that contains utility methods for the test framework.
    // It provides methods for creating folders and generating timestamps.

    public void CreateFolder(String path) {// This method creates a folder at the specified path if it does not already exist.
        // If the folder already exists, it prints a message indicating that the folder already exists.
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            System.out.println("Folder already exists at: " + path);
        }
    }
    public static String Timestamp() {
        Date now = new Date();
        String Timestamp = now.toString().replace(":", "-");
        return Timestamp;
    }
}
