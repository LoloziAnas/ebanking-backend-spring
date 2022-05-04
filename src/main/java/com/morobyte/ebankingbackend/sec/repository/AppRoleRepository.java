package com.morobyte.ebankingbackend.sec.repository;

import com.morobyte.ebankingbackend.sec.entity.AppRole;
import com.morobyte.ebankingbackend.sec.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findAppRoleByRoleName(String roleName);
}
