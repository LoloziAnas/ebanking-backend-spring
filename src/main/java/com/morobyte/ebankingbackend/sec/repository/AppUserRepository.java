package com.morobyte.ebankingbackend.sec.repository;

import com.morobyte.ebankingbackend.sec.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByUsername(String username);
}
