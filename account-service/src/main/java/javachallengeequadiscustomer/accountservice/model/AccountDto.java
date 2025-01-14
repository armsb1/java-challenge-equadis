package javachallengeequadiscustomer.accountservice.model;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * The type Account dto.
 */
@Builder
public record AccountDto(Long accountNumber,
                         BigDecimal totalBalance,
                         Long userProfileId) {
}
