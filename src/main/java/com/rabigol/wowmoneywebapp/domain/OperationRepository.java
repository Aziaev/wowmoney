package com.rabigol.wowmoneywebapp.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationRepository extends CrudRepository<Operation, Long> {

    // RUB
    @Query(value = "SELECT SUM(value) FROM operations where owner_id = :userId AND currency = 'RUB'", nativeQuery = true)
    Double getSumRub(@Param("userId") Long userId);

    @Query(value = "SELECT currency_name FROM public.currencies t WHERE currency_id = 1", nativeQuery = true)
    String getCurrencyRub();

    // EUR
    @Query(value = "SELECT SUM(value) FROM operations where owner_id = :userId AND currency = 'EUR'", nativeQuery = true)
    Double getSumEur(@Param("userId") Long userId);

    @Query(value = "SELECT currency_name FROM public.currencies t WHERE currency_id = 3", nativeQuery = true)
    String getCurrencyEur();

    //USD
    @Query(value = "SELECT SUM(value) FROM operations where owner_id =  :userId AND currency = 'USD'", nativeQuery = true)
    Double getSumUsd(@Param("userId") Long userId);

    @Query(value = "SELECT currency_name FROM public.currencies t WHERE currency_id = 2", nativeQuery = true)
    String getCurrencyUsd();


    @Query(value = "SELECT * FROM operations where owner_id = :userId ORDER BY timestamp DESC", nativeQuery = true)
    List<Operation> findAllDescTimeStamp(@Param("userId") Long userId);

    // Get exhange rates
    @Query(value = "SELECT currency_rate FROM public.currencies t WHERE currency_name = :currency", nativeQuery = true)
    Double getExhangeRate(@Param("currency") String currency);

    // Method for accounts balance with defined currency
    @Query(value = "SELECT SUM(value) FROM operations where owner_id =  :userId AND currency = :currency AND account = :account", nativeQuery = true)
    Double getSum(@Param("userId") Long userId,
                  @Param("currency") String currency,
                  @Param("account") String account);

    //Method for return sum of values with defined currency
    @Query(value = "SELECT SUM(value) FROM operations where owner_id =  :userId AND currency = :currency", nativeQuery = true)
    Double getSum(@Param("userId") Long userId,
                  @Param("currency") String currency);

    //Method for return sum of income (no timestamp used)
    @Query(value = "SELECT SUM(value) FROM public.operations t WHERE owner_id = :userId AND operation_type = :operation_type AND currency = :currency", nativeQuery = true)
    Double getStatSum(@Param("userId") Long userId,
                      @Param("operation_type") String operation_type,
                      @Param("currency") String currency);
}

