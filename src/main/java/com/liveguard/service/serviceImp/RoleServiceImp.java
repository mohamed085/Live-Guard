package com.liveguard.service.serviceImp;

import com.liveguard.domain.Role;
import com.liveguard.exception.BusinessException;
import com.liveguard.repository.RoleRepository;
import com.liveguard.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Long id) {
        log.debug("RoleService | findAll");

        try {
            return roleRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Role not found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RoleService | findAll | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Role findByRole(String role) {
        log.debug("RoleService | findAll");

        try {
            return roleRepository.findByRole(role)
                    .orElseThrow(() -> new BusinessException("Role not found", HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RoleService | findAll | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Role> findAll() {
        log.debug("RoleService | findAll");

        try {
            return roleRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("RoleService | findAll | error");
            throw new BusinessException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
