package com.app.infosys.bookstore.users.controller;

import com.app.infosys.bookstore.users.dto.ErrorDTO;
import com.app.infosys.bookstore.users.dto.UserDTO;
import com.app.infosys.bookstore.users.exception.UserExistsException;
import com.app.infosys.bookstore.users.exception.UserNotFoundException;
import com.app.infosys.bookstore.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    @Autowired
    private UserService userService;


    // Create a new user
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        try {
            userService.createUser(userDTO);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (UserExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdminUser(@RequestBody UserDTO userDTO) {
        try {
            userService.createAdminUser(userDTO);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (UserExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.updateUser(userDTO));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(new ErrorDTO(e.getMessage(), HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(new ErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }


    // Delete a user by ID
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get all users with pagination
    @GetMapping("/all")
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Page<UserDTO> users = userService.getAllUsers(page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get a user by ID
//    @GetMapping("/{userId}")
//    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
//        try {
//            UserDTO user = userService.getUser(userId);
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        } catch (UserNotFoundException e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }


}
