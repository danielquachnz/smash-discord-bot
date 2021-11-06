package au.com.ausmash.model.validator;

import au.com.ausmash.model.Region;
import au.com.ausmash.rest.exception.ValidationException;
import org.apache.commons.lang3.EnumUtils;

public class RegionValidator {
    private static final String INVALID_REGION_SHORT_NAME = "Region code [%s] is invalid. Try !regions to get the relevant region code";

    public static void validateShortName(String shortName) {
        if (!EnumUtils.isValidEnum(Region.RegionType.class, shortName.toUpperCase())) {
            throw new ValidationException(String.format(INVALID_REGION_SHORT_NAME, shortName));
        }
    }
}