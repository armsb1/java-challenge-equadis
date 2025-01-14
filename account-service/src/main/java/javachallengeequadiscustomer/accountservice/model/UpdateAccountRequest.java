package javachallengeequadiscustomer.accountservice.model;

import lombok.Builder;

import java.math.BigDecimal;

/**
 * The type Update account request.
 */
@Builder
public record UpdateAccountRequest(Long accountNumber,
                                   BigDecimal totalBalance,
                                   Long userProfileId) {
}
