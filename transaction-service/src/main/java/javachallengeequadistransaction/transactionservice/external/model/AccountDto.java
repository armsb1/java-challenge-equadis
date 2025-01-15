package javachallengeequadistransaction.transactionservice.external.model;

import javachallengeequadistransaction.transactionservice.external.enumeration.AccountStatus;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * The type Account dto.
 */
@Builder
public record AccountDto(Long accountNumber,
                         AccountStatus accountStatus,
                         BigDecimal totalBalance,
                         Long userProfileId) {
}
