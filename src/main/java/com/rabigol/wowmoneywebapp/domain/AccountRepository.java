package com.rabigol.wowmoneywebapp.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query(value = "SELECT \"account_name\" from public.\"accounts\"", nativeQuery = true)
    List<String> getAccounts();
}
