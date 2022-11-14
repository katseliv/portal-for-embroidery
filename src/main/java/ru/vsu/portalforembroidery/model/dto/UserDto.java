package ru.vsu.portalforembroidery.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.vsu.portalforembroidery.annotation.ImageValid;

import javax.validation.constraints.*;

@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public class UserDto {

    @NotNull(message = "Username is null.")
    @NotBlank(message = "Username is blank.")
    @Pattern(regexp = ".*([A-Z]|[a-z]).*", message = "Username must contain a letter.")
    private final String username;

    @NotNull(message = "First Name is null.")
    @NotBlank(message = "First Name is blank.")
    @Pattern(regexp = "^([A-Z]|[a-z])+$", message = "First Name mustn't contain a number.")
    private final String firstName;

    @NotNull(message = "Last Name is null.")
    @NotBlank(message = "Last Name is blank.")
    @Pattern(regexp = "^([A-Z]|[a-z])+$", message = "Last Name mustn't contain a number.")
    private final String lastName;

    @NotNull(message = "Image is null.")
    @NotBlank(message = "Image is blank.")
    @ImageValid
    private final String base64StringImage;

    @NotNull(message = "Phone Number is null.")
    @NotBlank(message = "Phone Number is blank.")
    @Pattern(regexp = "^([0-9])+$", message = "Phone Number mustn't contain a letter.")
    @Size(min = 7, max = 11, message = "Phone Number is out of range {7, 11}.")
    private final String phoneNumber;

    @NotNull(message = "Role Id is null.")
    @Positive(message = "Role Id is negative ot zero.")
    private final Integer roleId;

}
