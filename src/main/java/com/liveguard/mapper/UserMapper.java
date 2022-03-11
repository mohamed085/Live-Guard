package com.liveguard.mapper;

import com.liveguard.domain.User;
import com.liveguard.dto.ChipDTO;
import com.liveguard.dto.UserDTO;
import com.liveguard.dto.VendorDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class UserMapper {

    public static User UserDTOToUser(UserDTO userDTO) {
        log.debug("UserMapper | UserDTOToUser | " + userDTO.toString());

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setDateOfBirth(userDTO.getDateOfBirth());

        return user;
    }

    public static UserDTO UserToUserDTO(User user) {
        log.debug("UserMapper | UserToUserDTO | " + user.toString());

        List<String> roles = new ArrayList<>();

        user.getRoles().forEach(role -> roles.add(role.getRole()));

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPassword(null);
        userDTO.setGender(user.getGender());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setRoles(roles);
        userDTO.setCreatedDate(user.getCreatedTime());
        userDTO.setEnable(user.getEnable());
        userDTO.setAccountNonLocked(user.getAccountNonLocked());
        userDTO.setCredentialsNonExpired(user.getCredentialsNonExpired());
        userDTO.setAccountNonExpired(user.getAccountNonExpired());
        userDTO.setFacebookUrl(user.getFacebookUrl());
        userDTO.setInstagramUrl(user.getInstagramUrl());
        userDTO.setTwitterUrl(user.getTwitterUrl());

        return userDTO;
    }

    public static VendorDTO userToVendorDTO(User user) {
        log.debug("UserMapper | userToVendorDTO | " + user.toString());

        VendorDTO vendor = new VendorDTO(
                user.getId(),
                user.getName(),
                user.getAvatar(),
                user.getAddress(),
                user.getPhone(),
                user.getFacebookUrl(),
                user.getTwitterUrl(),
                user.getInstagramUrl()
        );

        return vendor;
    }

}
