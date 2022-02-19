package com.employee.CrudEmployee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.employee.CrudEmployee.model.Employee;
import com.employee.CrudEmployee.model.Project;
import com.employee.CrudEmployee.model.Role;
import com.employee.CrudEmployee.repositories.IProjectJpaRepository;
import com.employee.CrudEmployee.repositories.IRoleJpaRepository;
import com.employee.CrudEmployee.repositories.IEmployeeJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/update/{id}")
    public Employee updateEmployeeById(@PathVariable("id") long id, @RequestBody Employee emp){
        Optional<Employee> employeeId = employee.findById(id);
        Employee e = employeeId.get();
        e.setEmployeeid(emp.getEmployeeid());
        e.setFirstName(emp.getFirstName());
        e.setLastName(emp.getLastName());
        e.setRole(emp.getRole());
        List<Long> ids = new ArrayList<Long>();
        for(Project p: emp.getProjects()){
            ids.add(p.getId());
        }
        e.setProjects(project.findAllById(ids));
        return this.employee.save(e);
    }

    @GetMapping(path = "/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable("id") Long id){
        return employee.findById(id);
    }

    @GetMapping(path = "/employee/{firstName}")
    public ArrayList<Employee> getEmployeeByFirstName(@PathVariable("firstName") String firstName){
        return (ArrayList<Employee>)employee.findByFirstName(firstName);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable("id") Long id){
        try{
            employee.deleteById(id);
            return "Eliminated employee with id: "+ id;
        }catch(Exception err){
            return "Can't delete user with id: "+ id;
        }
    }

    @DeleteMapping("/delete/{firstName}")
    public String deleteEmployeeById(@PathVariable("firstName") String firstName){
        try{
            List<Employee> emp = employee.findByFirstName(firstName);
            employee.deleteAll(emp);
            return "Eliminated employee with name: "+ firstName;
        }catch(Exception err){
            return "Can't delete user with name: "+ firstName;
        }
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
