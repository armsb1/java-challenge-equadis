package javachallengeequadiscustomer.accountservice.model;

import javachallengeequadiscustomer.accountservice.enumeration.AccountStatus;
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
