package ru.vsu.portalforembroidery.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vsu.portalforembroidery.annotation.UsernameValid;
import ru.vsu.portalforembroidery.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UsernameValidator implements ConstraintValidator<UsernameValid, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        final List<String> messages = new ArrayList<>();
        if (userRepository.existsByUsername(username)) {
            messages.add("Username exists.");
        }
        final String messageTemplate = String.join(", ", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return messages.size() == 0;
    }

}
