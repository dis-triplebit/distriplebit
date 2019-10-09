package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static Properties props;

    static {
        InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties");
        props = new Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProp(String key){
        return props.getProperty(key);
    }
}
