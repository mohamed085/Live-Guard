package com.liveguard.mapper;

import com.liveguard.domain.User;
import com.liveguard.dto.UserDTO;
import com.liveguard.util.DateConverterUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserMapper {

    public static UserDTO userToUserDTO(User user) {

        log.debug("UserMapper | userToUserDTO | user: " + user.toString());
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPassword(null);
        userDTO.setAbout(user.getAbout());
        userDTO.setPhone(user.getPhone());
        userDTO.setGender(user.getGender());
        userDTO.setAddress(user.getAddress());
        userDTO.setDateOfBirth(DateConverterUtil.convertLocalDateToDate(user.getDateOfBirth()));
        userDTO.setCreatedDate(DateConverterUtil.convertLocalDateTimeToDate(user.getCreatedTime()));
        userDTO.setEnable(user.getEnable());
        userDTO.setAccountNonLocked(user.getAccountNonLocked());
        userDTO.setCredentialsNonExpired(user.getCredentialsNonExpired());
        userDTO.setAccountNonExpired(user.getAccountNonExpired());
        userDTO.setFacebookUrl(user.getFacebookUrl());
        userDTO.setInstagramUrl(user.getInstagramUrl());
        userDTO.setTwitterUrl(user.getTwitterUrl());
        userDTO.setAvatar(user.getAvatar());

        List<String> roles = new ArrayList<>();

        user.getRoles().forEach(role -> roles.add(role.getRole()));
        userDTO.setRoles(roles);

        return userDTO;
    }

    public static User UserDTOToUser(UserDTO userDTO) {
        log.debug("UserMapper | UserDTOToUser | " + userDTO.toString());

        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());
        user.setPhone(userDTO.getPhone());
        user.setGender(userDTO.getGender());
        user.setAddress(userDTO.getAddress());
        user.setDateOfBirth(DateConverterUtil.convertDateToLocalDate(userDTO.getCreatedDate()));
        user.setCreatedTime(DateConverterUtil.convertDateToLocalDateTime(userDTO.getCreatedDate()));
        user.setEnable(userDTO.getEnable());
        user.setAccountNonExpired(userDTO.getAccountNonExpired());
        user.setCredentialsNonExpired(userDTO.getCredentialsNonExpired());
        user.setAccountNonLocked(userDTO.getAccountNonLocked());
        user.setFacebookUrl(userDTO.getFacebookUrl());
        user.setTwitterUrl(userDTO.getTwitterUrl());
        user.setInstagramUrl(userDTO.getInstagramUrl());
        user.setAvatar(userDTO.getAvatar());

        return user;
    }
}
