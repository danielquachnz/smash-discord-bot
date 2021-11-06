package au.com.ausmash.model.validator;

import au.com.ausmash.model.Region;
import au.com.ausmash.rest.exception.ValidationException;
import au.com.ausmash.service.messageCreatedEvent.MessageCreatedEventService;
import au.com.ausmash.service.messageCreatedEvent.RegionsCommandService;
import au.com.ausmash.util.ParameterUtil;
import org.apache.commons.lang3.EnumUtils;

public class RegionValidator {
    private static final String INVALID_REGION_SHORT_NAME =
        ParameterUtil.REGION_SHORT
        + " [%s] is invalid. Try "
        + MessageCreatedEventService.COMMAND_PREFIX
        + RegionsCommandService.COMMAND_NAME
        + " to get the relevant "
        + ParameterUtil.REGION_SHORT;

    public static void validateShortName(String shortName) {
        if (!EnumUtils.isValidEnum(Region.RegionType.class, shortName.toUpperCase())) {
            throw new ValidationException(String.format(INVALID_REGION_SHORT_NAME, shortName));
        }
    }
}