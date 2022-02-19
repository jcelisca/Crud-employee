package com.employee.CrudEmployee.controller;

import java.util.ArrayList;
import java.util.List;

import com.employee.CrudEmployee.model.Employee;
import com.employee.CrudEmployee.model.Project;
import com.employee.CrudEmployee.model.Role;
import com.employee.CrudEmployee.repositories.IProjectJpaRepository;
import com.employee.CrudEmployee.repositories.IRoleJpaRepository;
import com.employee.CrudEmployee.repositories.IEmployeeJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    IEmployeeJpaRepository employee;

    @Autowired
    IProjectJpaRepository project;

    @Autowired
    IRoleJpaRepository role;

    @GetMapping()
    public ArrayList<Employee> gEmployees(){
        return (ArrayList<Employee>) employee.findAll();
    }

    @PostMapping("/newemployee")
    public Employee saveEmployee(@RequestBody Employee emp){
        Employee em = new Employee(emp.getFirstName(), emp.getLastName(), emp.getEmployeeid(), emp.getRole());
        List<Long> ids = new ArrayList<Long>();
        for(Project p: emp.getProjects()){
            ids.add(p.getId());
        }
        em.setProjects(project.findAllById(ids));

        return this.employee.save(em);

    }

    @GetMapping("/projects")
    public ArrayList<Project> gProjects(){
        return (ArrayList<Project>) project.findAll();
    }

    @PostMapping("/projects")
    public Project saveProject(@RequestBody Project proj){
        return this.project.save(proj);
    }

    @GetMapping("/roles")
    public ArrayList<Role> gRoles(){
        return (ArrayList<Role>) role.findAll();
    }

    @PostMapping("/roles")
    public Role saveRole(@RequestBody Role rol){
        return this.role.save(rol);
    }

}
