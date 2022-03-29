package com.liveguard.service;

import com.liveguard.domain.User;
import com.liveguard.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

    User getAuthenticatedAccount();

    UserDTO updateAuthenticatedAccount(UserDTO userDTO);

    void updateAuthenticatedAccountAvatar(MultipartFile multipartFile);

}
