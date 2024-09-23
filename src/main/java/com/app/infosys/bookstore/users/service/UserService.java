package com.app.infosys.bookstore.users.service;

import com.app.infosys.bookstore.users.dto.UserDTO;
import com.app.infosys.bookstore.users.exception.UserExistsException;
import com.app.infosys.bookstore.users.exception.UserNotFoundException;
import org.springframework.data.domain.Page;

public interface UserService {

    String login(UserDTO userDTO) throws UserNotFoundException;

    String signup(UserDTO userDTO) throws UserExistsException;

    void createUser(UserDTO userDTO) throws UserExistsException;

    void createAdminUser(UserDTO userDTO) throws UserExistsException;

    String updateUser(UserDTO userDTO);


    void deleteUser();


    void deleteUser(String userId);


    Page<UserDTO> getAllUsers(int page, int size);


    UserDTO getSelf();


    UserDTO getUser(String userId) throws UserNotFoundException;




}
