import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

// Enum for ValueType
public enum ValueType {
    LONG,
    BIGDECIMAL
}

// Validator class
public class AppConfigValidator {

    // Method to validate a single AppConfig based on the specified ValueType
    public static boolean validateAppConfig(AppConfig appConfig, ValueType type) {
        if (appConfig == null) {
            return false;
        }

        // Validate map values based on the specified type
        if (appConfig.getStringMap() != null) {
            for (Map.Entry<String, String> entry : appConfig.getStringMap().entrySet()) {
                String mapValue = entry.getValue();
                if (!isValidValue(mapValue, type)) {
                    return false;
                }
            }
        }

        // Validate the object value based on the specified type
        return isValidObjectValue(appConfig.getValue(), type);
    }

    // Method to validate a list of AppConfig
    public static boolean validateAppConfigList(List<AppConfig> appConfigList, ValueType type) {
        if (appConfigList == null || appConfigList.isEmpty()) {
            return false;
        }

        for (AppConfig appConfig : appConfigList) {
            if (!validateAppConfig(appConfig, type)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidValue(String value, ValueType type) {
        if (type == null) {
            return false;
        }

        switch (type) {
            case BIGDECIMAL:
                return isDecimal(value);
            case LONG:
                return isLong(value);
            default:
                return false;
        }
    }

    private static boolean isValidObjectValue(Object value, ValueType type) {
        if (type == null) {
            return false;
        }

        switch (type) {
            case BIGDECIMAL:
                return value instanceof BigDecimal;
            case LONG:
                return value instanceof Long;
            default:
                return false;
        }
    }

    private static boolean isDecimal(String value) {
        try {
            new BigDecimal(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
