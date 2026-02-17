package utiles;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    //Define the path to the properties file
    String filePath = "config.properties";
    public static String propertyReader(String filePath, String key) {
        String value = null;
        // Placeholder for property reading logic

        try (InputStream input = new FileInputStream(filePath)){

            Properties prop = new Properties();
            prop.load(input);
            // Get the property value
            value = prop.getProperty(key);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return value;
    }
}
