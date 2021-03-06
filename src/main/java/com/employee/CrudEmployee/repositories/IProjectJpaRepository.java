package com.employee.CrudEmployee.repositories;

import java.util.List;
import com.employee.CrudEmployee.model.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectJpaRepository extends JpaRepository<Project, Long> {
    List<Project> findByName(String name);
}