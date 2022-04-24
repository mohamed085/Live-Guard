package com.liveguard.service;

import com.liveguard.domain.User;
import com.liveguard.dto.UserDTO;
import com.liveguard.payload.UpdateAccountRequest;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

    User getAuthenticatedAccount();

    void updateAuthenticatedAccount(UpdateAccountRequest updateAccountRequest);

    void updateAuthenticatedAccountAvatar(MultipartFile multipartFile);

}
