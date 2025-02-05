package com.search.specification.controller;

import com.search.specification.constants.AppConstants;
import com.search.specification.dao.entity.Employee;
import com.search.specification.model.Request.SearchFiler;
import com.search.specification.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "dhgatway/employee")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping(path = "filter/{operation}")
    public List<Employee> getFilteredEmployee(@PathVariable String operation, @RequestBody List<SearchFiler> searchFilers) {
        AppConstants.LogicalOperation operations = AppConstants.LogicalOperation.valueOf(operation);
        return employeeService.getFilteredList(searchFilers, operations);
    }
}
