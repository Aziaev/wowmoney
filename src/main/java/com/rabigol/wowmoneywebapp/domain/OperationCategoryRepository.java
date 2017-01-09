package com.rabigol.wowmoneywebapp.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OperationCategoryRepository extends CrudRepository<OperationCategory, Long> {
    @Query(value = "SELECT \"operationCategory_name\" from public.\"operationCategories\"", nativeQuery = true)
    List<String> getOperationCategory();
}
