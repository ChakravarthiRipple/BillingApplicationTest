package utils;

import constants.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties props = new Properties();

    private ConfigReader() { }

    static {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new RuntimeException(CONFIG_FILE + " not found on classpath (expected in src/test/resources)");
            }
            props.load(in);
            log.info("{} loaded.", CONFIG_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load " + CONFIG_FILE + ": " + e.getMessage(), e);
        }
    }

    /** Resolution order: -Dkey=value (JVM/CI) first, then config.properties. */
    public static String get(String key) {
        String value = System.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            value = props.getProperty(key);
        }
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Missing or empty key in config: " + key);
        }
        return value.trim();
    }

    // ---------------- General ----------------
    public static String getBaseUrl()  { return get("base.url"); }
    public static String getBrowser()  { return get("browser"); }
    public static boolean isHeadless() { return Boolean.parseBoolean(get("headless")); }

    // ---------------- Role-based ----------------
    public static String getUsername(UserRole role)      { return get(roleKey(role) + ".username"); }
    public static String getPassword(UserRole role)      { return get(roleKey(role) + ".password"); }
    public static String getDashboardPath(UserRole role) { return get(roleKey(role) + ".dashboard.path"); }

    // ---------------- Default role ----------------
    public static UserRole getDefaultRole() {
        return UserRole.valueOf(get("default.role").toUpperCase());
    }
    public static String getUsername() { return getUsername(getDefaultRole()); }
    public static String getPassword() { return getPassword(getDefaultRole()); }

    private static String roleKey(UserRole role) {
        return role.name().toLowerCase();
    }
}