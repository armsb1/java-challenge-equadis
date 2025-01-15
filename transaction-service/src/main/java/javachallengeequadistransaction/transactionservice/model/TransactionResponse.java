package javachallengeequadistransaction.transactionservice.model;

import javachallengeequadistransaction.transactionservice.enumeration.TransactionStatus;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * The type Transaction response.
 */
@Builder
public record TransactionResponse(Long referenceId,
                                  LocalDateTime transactionDate,
                                  TransactionStatus status) {
}
