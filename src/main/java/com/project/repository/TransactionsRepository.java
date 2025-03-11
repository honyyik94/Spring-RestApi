package com.project.repository;

import com.project.entity.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TransactionsRepository extends JpaRepository<Transactions, String>{

    @Query("SELECT t FROM Transactions t " +
            "WHERE (:accountNumber IS NULL OR t.accountNumber = :accountNumber) " +
            "AND  (:customerId IS NULL OR t.customerId = :customerId) " +
            "AND   (:description IS NULL OR t.description = :description) ")
    Page<Transactions> findTransactionByKeywords( @Param("accountNumber") Long accountNumber,
                                                  @Param("customerId")  Long customerId,
                                                  @Param("description") String description,
                                                  Pageable pageable);
}

