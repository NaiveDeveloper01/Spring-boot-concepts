package com.search.specification.service.implementation;

import com.search.specification.constants.AppConstants;
import com.search.specification.dao.EmployeeRepository;
import com.search.specification.dao.entity.Employee;
import com.search.specification.model.Request.SearchFiler;
import com.search.specification.service.EmployeeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getFilteredList(List<SearchFiler> searchFilers, AppConstants.LogicalOperation logicalOperation) {
        return employeeRepository.findAll(getPredicates(searchFilers, logicalOperation));
    }

    private Specification<Employee> getPredicates(List<SearchFiler> searchFilers, AppConstants.LogicalOperation logicalOperation) {
        return new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                for (SearchFiler s : searchFilers) {
                    Predicate predicate = null;
                    switch (s.getOperation()) {
                        case LIKE -> {
                            predicate = criteriaBuilder.like(root.get(s.getKey()), "%" + s.getValue() + "%");
                        }
                        case EQUALS -> {
                            predicate = criteriaBuilder.equal(root.get(s.getKey()), s.getValue());
                        }
                        case BETWEEN -> {
                            String[] ranges = s.getValue().split(":");
                            predicate = criteriaBuilder.between(root.get(s.getKey()), ranges[0], ranges[1]);
                        }
                        default -> {
                            throw new RuntimeException();
                        }
                    }
                    predicates.add(predicate);
                }
                switch (logicalOperation) {
                    case AND -> {
                        return criteriaBuilder.and(predicates.toArray(predicates.toArray(new Predicate[0])));
                    }
                    case OR -> {
                        return criteriaBuilder.or(predicates.toArray(predicates.toArray(new Predicate[0])));
                    }
                }
                return null;
            }
        };
    }
}
