package com.example.innova;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import com.example.innova.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Component
public class Job {

    @Autowired
    private TransactionRepository transactionRepository;

    // Günlük aggregate job
    @Scheduled(cron = "0 0 0 * * ?") // Her gün gece 12'de çalışır
    public void dailyAggregate() {
        aggregateSpending("daily");
    }

    // Haftalık aggregate job
    @Scheduled(cron = "0 0 0 * * 1") // Her pazartesi gece 12'de çalışır
    public void weeklyAggregate() {
        aggregateSpending("weekly");
    }

    // Aylık aggregate job
    @Scheduled(cron = "0 0 0 1 * ?") // Her ayın 1'inde gece 12'de çalışır
    public void monthlyAggregate() {
        aggregateSpending("monthly");
    }

    private void aggregateSpending(String period) {
        LocalDate now = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate = now;

        switch (period) {
            case "daily":
                startDate = now.minusDays(1);
                break;
            case "weekly":
                startDate = now.minusWeeks(1).with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
                break;
            case "monthly":
                startDate = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }

        List<Transaction> transactions = transactionRepository.findByDateBetween(startDate, endDate);
        double totalSpending = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        System.out.println("Total spending for " + period + ": " + totalSpending);
    }
}