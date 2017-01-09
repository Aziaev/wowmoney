package com.rabigol.wowmoneywebapp.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OperationTypeRepository extends CrudRepository<OperationType, Long> {

    @Query(value = "SELECT \"operationType_name\" from public.\"operationTypes\"", nativeQuery = true)
    List<String> getOperationTypes();
}
