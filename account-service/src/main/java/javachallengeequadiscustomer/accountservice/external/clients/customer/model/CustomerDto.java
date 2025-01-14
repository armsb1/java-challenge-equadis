package javachallengeequadiscustomer.accountservice.external.clients.customer.model;

import lombok.Builder;

/**
 * The type Customer dto.
 */
@Builder
public record CustomerDto(Long userProfileId,
                          String firstName,
                          String lastName,
                          String email) {
}
