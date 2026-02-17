package helper;

import java.io.File;
import java.util.Date;

public class Helper {

    public void CreateFolder(String path) {
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
