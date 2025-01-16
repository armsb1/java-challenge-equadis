package javachallengeequadistransaction.transactionservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;


/**
 * The type Transaction dto.
 */
@Builder
public record TransactionDto(Long referenceId,
                             @NotNull
                             Long accountNumber,
                             @Positive
                             BigDecimal amount,
                             @Size(max = 25)
                             String comments) {
}
