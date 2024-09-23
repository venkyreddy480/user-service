package com.app.infosys.bookstore.users.util;

import com.app.infosys.bookstore.users.dto.UserDTO;
import com.app.infosys.bookstore.users.entity.Role;
import com.app.infosys.bookstore.users.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    public UserDTO getUserDTO(UserEntity userEntity){
        UserDTO userDTO =new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        return userDTO;
    }


    public  UserEntity getUserEntity(UserDTO userDTO, Role role){
        UserEntity userEntity=new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setName(userDTO.getName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setRole(role);
        return userEntity;
    }





}
