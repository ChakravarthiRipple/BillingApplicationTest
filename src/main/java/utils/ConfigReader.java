package utils;

import constants.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties props = new Properties();
    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    static {
        try {
            props.load(new FileInputStream(CONFIG_PATH));
            log.info("config.properties loaded.");
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config.properties: " + e.getMessage());
        }
    }

    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) throw new RuntimeException("Missing key in config: " + key);
        return value.trim();
    }

    // General
    public static String getBaseUrl()  { return get("base.url"); }
    public static String getBrowser()  { return System.getProperty("browser", get("browser")); }
    public static boolean isHeadless() { return Boolean.parseBoolean(System.getProperty("headless", get("headless"))); }

    // Default credentials (no role) - used by HomePageTest
    public static String getUsername() { return get("username"); }
    public static String getPassword() { return get("password"); }

    // Role-based credentials
    public static String getUsername(UserRole role) { return get(roleKey(role) + ".username"); }
    public static String getPassword(UserRole role) { return get(roleKey(role) + ".password"); }

    private static String roleKey(UserRole role) {
        return role.name().toLowerCase();
    }
}