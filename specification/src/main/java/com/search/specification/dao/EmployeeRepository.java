package com.search.specification.dao;

import com.search.specification.dao.entity.Employee;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface EmployeeRepository extends JpaRepositoryImplementation<Employee, Integer> {
}