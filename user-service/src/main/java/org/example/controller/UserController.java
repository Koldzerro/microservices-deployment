package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API для управления пользователями")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Создать пользователя")
    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto requestDto) {
        UserResponseDto dto = userService.createUser(requestDto);
        addLinks(dto);
        return dto;
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        UserResponseDto dto = userService.getUserById(id);
        addLinks(dto);
        return dto;
    }

    @Operation(summary = "Получить всех пользователей")
    @GetMapping
    public CollectionModel<UserResponseDto> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        users.forEach(this::addLinks);
        Link selfLink = linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel();
        return CollectionModel.of(users, selfLink);
    }

    @Operation(summary = "Обновить пользователя")
    @PutMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
        UserResponseDto dto = userService.updateUser(id, requestDto);
        addLinks(dto);
        return dto;
    }

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    private void addLinks(UserResponseDto dto) {
        dto.add(linkTo(methodOn(UserController.class).getUserById(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));
        dto.add(linkTo(UserController.class).slash(dto.getId()).withRel("delete"));
    }
}