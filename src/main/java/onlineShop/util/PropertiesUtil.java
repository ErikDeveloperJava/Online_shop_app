package onlineShop.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties load(String fileName){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(fileName));
        } catch (IOException e) {
            System.out.println("failed load properties");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return properties;
    }
}
