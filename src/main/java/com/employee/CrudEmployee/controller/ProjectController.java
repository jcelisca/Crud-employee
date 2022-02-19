package com.employee.CrudEmployee.controller;

import java.util.ArrayList;
import com.employee.CrudEmployee.model.Project;
import com.employee.CrudEmployee.repositories.IProjectJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    IProjectJpaRepository project;

    @GetMapping()
    public ArrayList<Project> gProjects(){
        return (ArrayList<Project>) project.findAll();
    }

    @PostMapping()
    public Project saveProject(@RequestBody Project proj){
        return this.project.save(proj);
    }
}
