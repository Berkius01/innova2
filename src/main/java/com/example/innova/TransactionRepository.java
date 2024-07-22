package com.example.innova;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId")
    Double findTotalSpendingByUserId(Long userId);
    List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);
    //@Query("SELECT t FROM Transaction t where t.user_id = :id")
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :id")
    List<Transaction> getAllByUserModel(Long id);


}