package com.morobyte.ebankingbackend.sec.service;

import com.morobyte.ebankingbackend.sec.entity.AppRole;
import com.morobyte.ebankingbackend.sec.entity.AppUser;

import java.util.List;

public interface IAccountService {

    AppUser addUser(AppUser user);

    AppRole addRole(AppRole role);

    void addRoleToUser(String username, String roleName);

    AppUser getUserByUsername(String username);

    List<AppUser> getAppUsers();
}
