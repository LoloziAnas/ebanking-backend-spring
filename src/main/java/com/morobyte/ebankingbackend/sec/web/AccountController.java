package com.morobyte.ebankingbackend.sec.web;

import com.morobyte.ebankingbackend.sec.entity.AppRole;
import com.morobyte.ebankingbackend.sec.entity.AppUser;
import com.morobyte.ebankingbackend.sec.service.IAccountService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class AccountController {
    private final IAccountService accountService;

    @Autowired
    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok(accountService.getAppUsers());
    }

    @PostMapping(path = "/users")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        return new ResponseEntity<>(accountService.addUser(user), HttpStatus.CREATED);
    }

    @PostMapping(path = "/roles")
    public ResponseEntity<AppRole> saveRole(@RequestBody AppRole role) {
        return new ResponseEntity<>(accountService.addRole(role), HttpStatus.CREATED);
    }

    @PostMapping(path = "/addRoleToUser")
    public void addRoleToUser(@RequestBody RoleUserForm roleUserForm) {
        accountService.addRoleToUser(roleUserForm.getUsername(), roleUserForm.getRoleName());
    }
}

@Data
class RoleUserForm {
    private String username;
    private String roleName;
}
