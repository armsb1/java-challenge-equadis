package javachallengeequadiscustomer.accountservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import javachallengeequadiscustomer.accountservice.enumeration.AccountStatus;
import lombok.Builder;

import java.math.BigDecimal;

/**
 * The type Account dto.
 */
@Builder
public record AccountDto(Long accountNumber,
                         AccountStatus accountStatus,
                         @Positive(message = "Account balance must be positive")
                         BigDecimal totalBalance,
                         @NotNull(message = "Account cannot be null")
                         Long userProfileId) {
}
