package constants;

/**
 * UserRole - Enum for all 6 application roles.
 * Each role maps to its own credentials section in config.properties.
 */
public enum UserRole {
    SUPER_ADMIN,
    SUPER_USER,
    ADMIN_PREPAID,
    ADMIN_POSTPAID,
    CONSUMER_PREPAID,
    CONSUMER_POSTPAID
}
