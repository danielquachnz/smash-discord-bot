package au.com.ausmash.model.validator;

import au.com.ausmash.model.Region;
import au.com.ausmash.rest.exception.ValidationException;
import au.com.ausmash.service.messageCreatedEvent.MessageCreatedEventService;
import au.com.ausmash.service.messageCreatedEvent.NzRegionsCommandService;
import au.com.ausmash.util.ParameterUtil;
import org.apache.commons.lang3.EnumUtils;

public class NzRegionValidator {
    private static final String INVALID_NZ_PR_REGION
        = ParameterUtil.NZ_PR_REGION
        + " [%s] is invalid. Try "
        + MessageCreatedEventService.COMMAND_PREFIX
        + NzRegionsCommandService.COMMAND_NAME
        + " to get the relevant "
        + ParameterUtil.NZ_PR_REGION;

    public static void validateShortName(String shortName) {
        if (!EnumUtils.isValidEnum(Region.RegionType.class, shortName.toUpperCase())) {
            throw new ValidationException(String.format(INVALID_NZ_PR_REGION, shortName));
        }
    }
}