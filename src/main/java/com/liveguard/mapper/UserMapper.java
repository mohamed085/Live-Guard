package com.liveguard.mapper;

import com.liveguard.domain.User;
import com.liveguard.dto.ChipDTO;
import com.liveguard.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
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

        Set<ChipDTO> chips = new HashSet<>();

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPassword(null);
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setAvatar(user.getAvatar());
        user.getChips().forEach(chip -> {
            chips.add(ChipMapper.chipToChipDTO(chip));
        });
        userDTO.setChips(chips);

        return userDTO;
    }

}
