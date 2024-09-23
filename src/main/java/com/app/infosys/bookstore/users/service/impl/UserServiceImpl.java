package com.app.infosys.bookstore.users.service.impl;

import com.app.infosys.bookstore.users.dto.UserDTO;
import com.app.infosys.bookstore.users.entity.Role;
import com.app.infosys.bookstore.users.entity.UserEntity;
import com.app.infosys.bookstore.users.exception.UserExistsException;
import com.app.infosys.bookstore.users.exception.UserNotFoundException;
import com.app.infosys.bookstore.users.repository.RoleRepository;
import com.app.infosys.bookstore.users.repository.UserRepository;
import com.app.infosys.bookstore.users.service.UserService;
import com.app.infosys.bookstore.users.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper  modelMapper;




    @Override
    public String login(UserDTO userDTO) throws UserNotFoundException {
        UserEntity user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + userDTO.getEmail()));
        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new UserNotFoundException("Invalid password for user: " + userDTO.getEmail());
        }
        return "Login successful for " + user.getName();
    }

    @Override
    public String signup(UserDTO userDTO) throws UserExistsException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserExistsException("User already exists");
        }
        Role role = roleRepository.findByName("ROLE_USER");
        UserEntity userEntity = modelMapper.getUserEntity(userDTO,role);
        userRepository.save(userEntity);
        return "Signup successful for " + userEntity.getName();
    }

    @Override
    public void createUser(UserDTO userDTO) throws UserExistsException {
        Role role = roleRepository.findByName("ROLE_USER");
        UserEntity  userEntity = modelMapper.getUserEntity(userDTO,role);
        if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new UserExistsException("User already exists with email: " + userDTO.getEmail());
        }
        userRepository.save(userEntity);
    }

    @Override
    public void createAdminUser(UserDTO userDTO) throws UserExistsException {
        Role role = roleRepository.findByName("ROLE_ADMIN");
        UserEntity  userEntity = modelMapper.getUserEntity(userDTO,role);
        if (userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            throw new UserExistsException("User already exists with email: " + userDTO.getEmail());
        }
        userRepository.save(userEntity);
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        UserEntity existingUser = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        existingUser.setName(userDTO.getName());
        existingUser.setPassword(userDTO.getPassword());
        userRepository.save(existingUser);
        return "User updated successfully";
    }

    @Override
    public void deleteUser() {

    }


    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByEmail(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + userId));

        userRepository.delete(userEntity);
    }


    @Override
    public Page<UserDTO> getAllUsers(int page, int size) {
        Page<UserEntity> userList =userRepository.findAll(PageRequest.of(page, size));
        return  userList.map(userEntity -> modelMapper.getUserDTO(userEntity));
    }



    @Override
    public UserDTO getSelf() {
        return null;
    }

    @Override
    public UserDTO getUser(String userId) throws UserNotFoundException {
        UserEntity userEntity= userRepository.findByEmail(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return  modelMapper.getUserDTO(userEntity);
    }
}
