package org.social_media.pojo;

import java.sql.Timestamp;

public record UserDTO(
        long id,
        String firstName,
        String lastName,
        String email,
        String gender,
        Timestamp startDate
) {
}
