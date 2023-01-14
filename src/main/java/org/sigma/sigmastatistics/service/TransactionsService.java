package org.sigma.sigmastatistics.service;

import org.sigma.sigmastatistics.model.dto.StatisticsDTO;
import org.sigma.sigmastatistics.model.dto.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransactionsService {

    private Map<Long, TransactionDTO> transactionMap;


    public TransactionsService() {
        this.transactionMap = new ConcurrentHashMap<>();
    }


    public void add(TransactionDTO transaction) {
        long until = transaction.timestamp().until(LocalDateTime.now(ZoneId.of("UTC")), ChronoUnit.SECONDS);
        if (until >= 0 && until <= 60L) {
            transactionMap.put(transaction.timestamp().toEpochSecond(ZoneOffset.UTC), transaction);
        } else if (until < 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

    }

    public StatisticsDTO getStatistics() {
        if (transactionMap.size() == 0) {
            return new StatisticsDTO(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0);
        }

        DoubleSummaryStatistics statsSummary = transactionMap
                .values()
                .stream()
                .filter(transaction -> isTimestampOlderThan1Min(transaction.timestamp()))
                .mapToDouble(transaction -> transaction.amount().doubleValue())
                .summaryStatistics();

        if (statsSummary.getCount() == 0) {
            return new StatisticsDTO(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0);
        }
        return new StatisticsDTO(BigDecimal.valueOf(statsSummary.getSum()),
                BigDecimal.valueOf(statsSummary.getAverage()),
                BigDecimal.valueOf(statsSummary.getMax()),
                BigDecimal.valueOf(statsSummary.getMin()),
                statsSummary.getCount());
    }

    public void deleteAll() {
        transactionMap = new ConcurrentHashMap<>();
    }


    private boolean isTimestampOlderThan1Min(LocalDateTime timestamp) {
        final long ONE_MINUTE = 60000L;
        return System.currentTimeMillis() - timestamp.toEpochSecond(ZoneOffset.UTC) > ONE_MINUTE;
    }
}
