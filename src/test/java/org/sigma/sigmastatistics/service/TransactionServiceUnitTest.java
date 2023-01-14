package org.sigma.sigmastatistics.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sigma.sigmastatistics.model.dto.StatisticsDTO;
import org.sigma.sigmastatistics.model.dto.TransactionDTO;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

class TransactionServiceUnitTest {

    private TransactionsService service;

    @BeforeEach
    void setUp() {
        service = new TransactionsService();
    }

    @Test
    void addTransaction_success() {
        TransactionDTO transaction = new TransactionDTO(BigDecimal.ONE, LocalDateTime.now(ZoneId.of("UTC")));
        service.add(transaction);
        Assertions.assertEquals(1, service.getStatistics().getCount());
    }

    @Test
    void addTransaction_fail_time__future() {
        TransactionDTO transaction = new TransactionDTO(BigDecimal.ONE, LocalDateTime.now(ZoneId.of("UTC")).plus(75, ChronoUnit.SECONDS));
        Assertions.assertThrows(ResponseStatusException.class, () -> service.add(transaction));
    }

    @Test
    void addTransaction_older_60sec() {
        TransactionDTO transaction = new TransactionDTO(BigDecimal.ONE, LocalDateTime.now(ZoneId.of("UTC")).minus(61, ChronoUnit.SECONDS));
        Assertions.assertThrows(ResponseStatusException.class, () -> service.add(transaction));
    }

    @Test
    void deleteAll_success() {
        TransactionDTO transaction = new TransactionDTO(BigDecimal.ONE, LocalDateTime.now(ZoneId.of("UTC")));
        service.add(transaction);
        service.deleteAll();
        Assertions.assertEquals(0, service.getStatistics().getCount());
    }

    @Test
    void deleteAll_empty_transactions() {
        service.deleteAll();
        Assertions.assertEquals(0, service.getStatistics().getCount());
    }

    @Test
    void getStatistics_empty_transactions() {
        StatisticsDTO statistics = service.getStatistics();
        Assertions.assertEquals(0, statistics.getCount());
    }

    @Test
    void getStatistics_successs() {
        TransactionDTO transaction = new TransactionDTO(BigDecimal.valueOf(10.0), LocalDateTime.now(ZoneId.of("UTC")));
        service.add(transaction);
        StatisticsDTO statistics = service.getStatistics();
        Assertions.assertEquals(1, statistics.getCount());
        Assertions.assertEquals(BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP), statistics.getSum());
    }
}
