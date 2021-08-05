package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>
{
  @Query(value = "Select e From Employee e Where e.employeeName=:name")	
  public List<Employee> readAllEmployeesByEmployeeName(@Param("name") String employeeName);
}