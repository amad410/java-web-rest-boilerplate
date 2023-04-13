package Utils;

import java.util.Properties;

/**
 * This is the Global Data Loader class used to provide data for getting specific API glo
 * globalized data. Here we store and fetch these parameters to/from config.properties.
 */
public class DataLoader {
    private final Properties _properties;
    private static DataLoader _dataLoader;

    private DataLoader(){
        _properties = PropertyUtils.propertyLoader(System.getProperty("user.dir") + "/src/main/resources/configs/data.properties");
    }
    public static DataLoader getInstance(){
        if(_dataLoader == null){
            _dataLoader = new DataLoader();
        }
        return _dataLoader;
    }
}
