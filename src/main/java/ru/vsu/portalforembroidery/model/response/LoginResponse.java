package ru.vsu.portalforembroidery.model.response;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class LoginResponse {

    private final String accessToken;
    private final String refreshToken;

}
