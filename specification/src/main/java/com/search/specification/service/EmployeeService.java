package com.search.specification.service;

import com.search.specification.constants.AppConstants;
import com.search.specification.dao.entity.Employee;
import com.search.specification.model.Request.SearchFiler;

import java.util.List;

public interface EmployeeService {
    List<Employee> getFilteredList(List<SearchFiler> searchFiler, AppConstants.LogicalOperation logicalOperation);
}
