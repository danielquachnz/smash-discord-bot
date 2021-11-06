package au.com.ausmash.model.validator;

import au.com.ausmash.model.Game;
import au.com.ausmash.rest.exception.ValidationException;
import au.com.ausmash.service.messageCreatedEvent.GamesCommandService;
import au.com.ausmash.service.messageCreatedEvent.MessageCreatedEventService;
import au.com.ausmash.service.messageCreatedEvent.RegionsCommandService;
import au.com.ausmash.util.ParameterUtil;
import org.apache.commons.lang3.EnumUtils;

public class GameValidator {
    private static final String INVALID_GAME_SHORT_NAME =
        ParameterUtil.GAME_SHORT
        + " [%s] is invalid. Try "
        + MessageCreatedEventService.COMMAND_PREFIX
        + GamesCommandService.COMMAND_NAME
        + " to get the relevant "
        + ParameterUtil.GAME_SHORT;

    public static void validateShortName(String shortName) {
        if (!EnumUtils.isValidEnum(Game.GameType.class, shortName.toUpperCase())) {
            throw new ValidationException(String.format(INVALID_GAME_SHORT_NAME, shortName));
        }
    }
}