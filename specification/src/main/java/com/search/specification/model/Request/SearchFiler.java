package com.search.specification.model.Request;

import com.search.specification.constants.AppConstants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchFiler {
    private String key;
    private String value;
    private AppConstants.AppOperations operation;
}
