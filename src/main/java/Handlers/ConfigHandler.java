package Handlers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHandler {
    private String propertyFile = null;
    private Properties property = null;

    public void retrieveEnvConfiguration(String targetedEnvironment) throws Exception {
        property = new Properties();

        try{
            switch(targetedEnvironment)
            {
                case "DEV":
                    propertyFile = System.getProperty("user.dir") + "/src/main/resources/configs/dev.properties";
                    break;
                case "QA":
                    propertyFile = System.getProperty("user.dir") + "/src/main/resources/configs/qa.properties";
                    break;
                case "UAT":
                    propertyFile = System.getProperty("user.dir") + "/src/main/resources/configs/uat.properties";
                    break;
                case "Sample":
                    propertyFile = System.getProperty("user.dir") + "/src/main/resources/configs/sample.properties";
                    break;
                case "default":
                    propertyFile = System.getProperty("user.dir") + "/src/main/resources/configs/local.properties";
                    break;
            }
            InputStream input = new FileInputStream(propertyFile);
            property.load(input);
        } catch (FileNotFoundException e) {
            throw new Exception(String.format("Property File not found for %s" + targetedEnvironment));
        } catch (IOException e) {
            throw new Exception("Issue loading config properties \r\n" + e.getMessage());
        }
    }
    public String ReadConfigProperties(String key) throws Exception {
        String propValue = null;
        try{
            propValue = property.getProperty(key);
        }
        catch (Exception e)
        {
            throw new Exception(String.format("Issue reading key %s from property file", key));
        }
        return propValue;
    }
    public String GetBaseURL() throws Exception {
        String propValue = null;
        try{
            propValue = property.getProperty("baseUrl");
        }
        catch (Exception e)
        {
            throw new Exception("Issue reading baseUrl from property file");
        }
        return propValue;
    }
    public String GetBaseURI() throws Exception {
        String propValue = null;
        try{
            propValue = property.getProperty("baseUri");
        }
        catch (Exception e)
        {
            throw new Exception("Issue reading baseAPIUrl from property file");
        }
        return propValue;
    }
}
