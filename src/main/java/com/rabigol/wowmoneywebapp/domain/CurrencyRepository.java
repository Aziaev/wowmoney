package com.rabigol.wowmoneywebapp.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    @Query(value = "SELECT \"currency_name\" FROM PUBLIC.\"currencies\"", nativeQuery = true)
    List<String> getCurrencies();
}
