package com.morobyte.ebankingbackend.sec.service;

import com.morobyte.ebankingbackend.sec.entity.AppRole;
import com.morobyte.ebankingbackend.sec.entity.AppUser;
import com.morobyte.ebankingbackend.sec.repository.AppRoleRepository;
import com.morobyte.ebankingbackend.sec.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountService implements IAccountService {
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AccountService(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public AppUser addUser(AppUser user) {
        String pwd = user.getPassword();
        user.setPassword(passwordEncoder.encode(pwd));
        return appUserRepository.save(user);
    }

    @Override
    public AppRole addRole(AppRole role) {
        return appRoleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findAppUserByUsername(username);
        AppRole appRole = appRoleRepository.findAppRoleByRoleName(roleName);
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return appUserRepository.findAppUserByUsername(username);
    }

    @Override
    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }
}
