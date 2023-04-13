package Utils;

import com.fasterxml.jackson.databind.annotation.JsonAppend;

import java.util.Properties;

/**
 * This is the Global Config Loader class used to provide params for getting token
 *  Here we store and fetch these parameters to/from config.properties.
 *  Preferably, a more secure option would be to use ENV variables.
 */
public class ConfigLoader {

    private final Properties _properties;
    private static ConfigLoader _configLoader;

    private ConfigLoader(){
        _properties = PropertyUtils.propertyLoader(System.getProperty("user.dir") + "/src/main/resources/configs/global.properties");
    }
    public static ConfigLoader getInstance(){
        if(_configLoader == null){
            _configLoader = new ConfigLoader();
        }
        return _configLoader;
    }

    public String getClientId(){
        String prop = _properties.getProperty("client_id");
        if (prop != null)
                return prop;
        else throw new RuntimeException("property client_id is not specified in the global.properties file");
    }
    public String getClientSecret(){
        String prop = _properties.getProperty("client_secret");
        if (prop != null)
            return prop;
        else throw new RuntimeException("property client_secret is not specified in the global.properties file");
    }
    public String getGrantType(){
        String prop = _properties.getProperty("grant_type");
        if (prop != null)
            return prop;
        else throw new RuntimeException("property grant_type is not specified in the global.properties file");
    }

    public String getTokenUri(){
        String prop = _properties.getProperty("token_uri");
        if (prop != null)
            return prop;
        else throw new RuntimeException("property token_uri is not specified in the global.properties file");
    }
    public String getReportConfigPath(){
        String prop = _properties.getProperty("reportConfigPath");
        if (prop != null)
            return prop;
        else throw new RuntimeException("property reportConfigPath is not specified in the global.properties file");
    }



}
