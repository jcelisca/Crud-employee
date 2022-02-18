package com.employee.CrudEmployee.repositories;

import java.util.List;

import com.employee.CrudEmployee.model.Employee;
import com.employee.CrudEmployee.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeJpaRepository extends JpaRepository<Employee, Long> {
    Employee findByEmployeeid(String employeeid);
    List<Employee> findByFirstName(String firstName);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByRole(Role role);

}
