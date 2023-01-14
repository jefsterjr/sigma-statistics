package org.sigma.sigmastatistics.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(BigDecimal amount, LocalDateTime timestamp) {
}
