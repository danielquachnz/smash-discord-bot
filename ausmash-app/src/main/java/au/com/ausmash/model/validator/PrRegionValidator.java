package au.com.ausmash.model.validator;

import au.com.ausmash.model.NzRegion;
import au.com.ausmash.model.Region;
import au.com.ausmash.rest.exception.ValidationException;
import au.com.ausmash.service.messageCreatedEvent.MessageCreatedEventService;
import au.com.ausmash.service.messageCreatedEvent.NzRegionsCommandService;
import au.com.ausmash.service.messageCreatedEvent.RegionsCommandService;
import au.com.ausmash.util.ParameterUtil;
import org.apache.commons.lang3.EnumUtils;

public class PrRegionValidator {
    private static final String INVALID_PR_REGION
        = ParameterUtil.REGION_SHORT
        + " [%s] is invalid. Try "
        + MessageCreatedEventService.COMMAND_PREFIX
        + RegionsCommandService.COMMAND_NAME
        + " or "
        + MessageCreatedEventService.COMMAND_PREFIX
        + NzRegionsCommandService.COMMAND_NAME
        + " to get the relevant "
        + ParameterUtil.REGION_SHORT;

    public static void validateShortName(String shortName) {
        if (!EnumUtils.isValidEnum(Region.RegionType.class, shortName.toUpperCase())
            && !EnumUtils.isValidEnum(NzRegion.class, shortName.toUpperCase())) {
            throw new ValidationException(String.format(INVALID_PR_REGION, shortName));
        }
    }
}