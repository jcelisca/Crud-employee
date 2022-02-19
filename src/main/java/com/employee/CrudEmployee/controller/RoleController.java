package com.employee.CrudEmployee.controller;

import java.util.ArrayList;

import com.employee.CrudEmployee.model.Role;
import com.employee.CrudEmployee.repositories.IRoleJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rol")
public class RoleController {

    @Autowired
    IRoleJpaRepository role;

    @GetMapping()
    public ArrayList<Role> gRoles(){
        return (ArrayList<Role>) role.findAll();
    }

    @PostMapping()
    public Role saveRole(@RequestBody Role rol){
        return this.role.save(rol);
    }
}
