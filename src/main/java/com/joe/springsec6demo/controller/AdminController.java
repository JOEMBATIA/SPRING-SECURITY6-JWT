package com.joe.springsec6demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping
    @Secured("admin:get")
    public String getAdmin(){
        return "SECURED GET :: Role -> ADMIN";
    }

    @PostMapping
    @Secured("admin:post")
    public String createAdmin(){
        return "SECURED POST :: Role -> ADMIN";
    }

    @PutMapping
    @Secured("admin:update")
    public String updateAdmin(){
        return "SECURED UPDATE :: Role -> ADMIN";
    }

    @DeleteMapping
    @PreAuthorize("hasRole('admin:delete'")
    public String deleteAdmin(){
        return "SECURED DELETE :: Role -> ADMIN";
    }


}
