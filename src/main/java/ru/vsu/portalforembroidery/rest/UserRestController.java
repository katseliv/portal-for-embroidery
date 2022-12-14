package ru.vsu.portalforembroidery.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.portalforembroidery.model.Provider;
import ru.vsu.portalforembroidery.model.dto.UserDto;
import ru.vsu.portalforembroidery.model.dto.UserRegistrationDto;
import ru.vsu.portalforembroidery.model.dto.view.UserForListDto;
import ru.vsu.portalforembroidery.model.dto.view.UserViewDto;
import ru.vsu.portalforembroidery.model.dto.view.ViewListPage;
import ru.vsu.portalforembroidery.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody @Valid final UserRegistrationDto userRegistrationDto) {
        final int id = userService.createUser(userRegistrationDto, Provider.LOCAL);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Integer> createUser(@RequestBody @Valid final UserRegistrationDto userRegistrationDto) {
        final int id = userService.createUser(userRegistrationDto, Provider.LOCAL);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserViewDto getUser(@PathVariable final int id) {
        return userService.getUserViewById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody @Valid final UserDto userDto, @PathVariable final int id) {
        userService.updateUserById(userDto, id);
        return new ResponseEntity<>("User was updated!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable final int id) {
        userService.deleteUserById(id);
    }

    @GetMapping
    public ViewListPage<UserForListDto> getUsers(@RequestParam(required = false) final Map<String, String> allParams) {
        return userService.getViewListPage(allParams.get("page"), allParams.get("size"));
    }

}
