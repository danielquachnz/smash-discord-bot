package au.com.ausmash.model.validator;

import au.com.ausmash.model.Game;
import au.com.ausmash.rest.exception.ValidationException;
import org.apache.commons.lang3.EnumUtils;

public class GameValidator {
    private static final String INVALID_GAME_SHORT_NAME = "Game code [%s] is invalid. Try !games to get the relevant game code";

    public static void validateShortName(String shortName) {
        if (!EnumUtils.isValidEnum(Game.GameType.class, shortName.toUpperCase())) {
            throw new ValidationException(String.format(INVALID_GAME_SHORT_NAME, shortName));
        }
    }
}