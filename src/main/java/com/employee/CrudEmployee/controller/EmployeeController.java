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

/**
 * Clase controlador desde el cual se generan todas las peticiones de la api
 */
public class EmployeeController {

    @Autowired
    IEmployeeJpaRepository employee;

    @Autowired
    IProjectJpaRepository project;

    @Autowired
    IRoleJpaRepository role;

    /**
     * Método que devuelve una lista con todos lo empleados existentes en la base de datos
     * @return Lista de empleados con sus atributos
     */
    @GetMapping()
    public ArrayList<Employee> gEmployees(){
        return (ArrayList<Employee>) employee.findAll();
    }

    /**
     * Métododo que agrega a la base de datos un objeto de tipo empleado
     * @param emp Objeto de tipo empleado con sus atributos
     * @return Lista de atributos del objeto empleado con sus valores
     */
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

    /**
     * Método que actualiza los valores de un empleado existente por medio de su ID
     * @param id Identificador del empleado que se quiere actualizar
     * @param emp Objeto empleado con sus nuevos valores de atributos
     * @return Lista de atributos del objeto empleado con sus valores
     */
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

    /**
     * Método que devulve un objeto empleado con sus valores de atributos, por medio de su ID
     * @param id Identificador del empleado a buscar
     * @return Lista de atributos del objeto empleado con sus valores
     */
    @GetMapping(path = "/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable("id") Long id){
        return employee.findById(id);
    }

    /**
     * Método que devulve un objeto empleado con sus valores de atributos, por medio de su nombre
     * @param firstName Nombre del empleado a buscar
     * @return Lista de empleados con sus valores de atributos
     */
    @GetMapping(path = "/employee/{firstName}")
    public ArrayList<Employee> getEmployeeByFirstName(@PathVariable("firstName") String firstName){
        return (ArrayList<Employee>)employee.findByFirstName(firstName);
    }

    /**
     * Método para eliminar un empleado por medio de su ID
     * @param id Identificador numérico del empleado a eliminar
     * @return Mensaje con información si se pudo eliminar el empleado o no
     */
    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable("id") Long id){
        try{
            employee.deleteById(id);
            return "Eliminated employee with id: "+ id;
        }catch(Exception err){
            return "Can't delete user with id: "+ id;
        }
    }

    /**
     * Método para eliminar un empleado por medio de su nombre
     * @param firstName Nombre del empleado a ser eliminado
     * @return Mensaje con información si se pudo eliminar el empleado o no
     */
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

    /**
     * Método que devuelde la lista de objetos con la información de los proyectos existentes
     * @return Lista de proyectos existentes en la base de datos
     */
    @GetMapping("/projects")
    public ArrayList<Project> gProjects(){
        return (ArrayList<Project>) project.findAll();
    }

    /**
     * Método para agregar un nuevo objeto de tipo proyecto a la base de datos
     * @param proj Lista de atributos del objeto project
     * @return Objeto project con sus valores de atributos
     */
    @PostMapping("/projects")
    public Project saveProject(@RequestBody Project proj){
        return this.project.save(proj);
    }

    /**
     * Método que devuelve la lista de objetos tipo Role existentes en la base de datos
     * @return Objetos Role con valores de atributos
     */
    @GetMapping("/roles")
    public ArrayList<Role> gRoles(){
        return (ArrayList<Role>) role.findAll();
    }

    /**
     * Método que permite agregar un nuevo objeto rol a la base de datos
     * @param rol Objeto Role con valores de atributos
     * @return Objeto guardado con sus valores de atributos
     */
    @PostMapping("/roles")
    public Role saveRole(@RequestBody Role rol){
        return this.role.save(rol);
    }

    /**
     * Método que permite actualizar los valores de un objeto rol, por medio de su ID
     * @param id Identificador numérico del objeto Role a modificar
     * @param rol Nuevos valores de atributos para el objeto Role
     * @return Valores de atributos del objeto actualizado
     */
    @PutMapping("/roles/update/{id}")
    public Role updateRoleById(@PathVariable("id") long id, @RequestBody Role rol){
        Optional<Role> roleId = role.findById(id);
        Role r = roleId.get();
        r.setName(rol.getName());
        return this.role.save(r);
    }

    /**
     * Método para actualizar un objeto project por medio de su ID
     * @param id Identificador numérico del proyecto a modificar
     * @param proj Valores de atributos para el objeto
     * @return Valores de atributos del objeto actualizado
     */
    @PutMapping("/projects/update/{id}")
    public Project updateProjectById(@PathVariable("id") long id, @RequestBody Project proj){
        Optional<Project> projId = project.findById(id);
        Project p = projId.get();
        p.setName(proj.getName());
        return this.project.save(p);
    }

}
