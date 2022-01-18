package com.readutf.csprojectapi.utils;

import java.util.Optional;
import java.util.UUID;

public class UUIDParser {

    public static Optional<UUID> parseUUID(String uuidAsString) {
        try {
            return Optional.of(UUID.fromString(uuidAsString));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
