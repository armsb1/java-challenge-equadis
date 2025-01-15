package javachallengeequadistransaction.transactionservice.model;

import lombok.Builder;

import java.math.BigDecimal;


/**
 * The type Transaction dto.
 */
@Builder
public record TransactionDto(Long accountNumber,
                             BigDecimal amount,
                             String comments) {
}
