package javachallengeequadiscustomer.customerservice.model;

import lombok.Builder;

@Builder
public record CustomerDto(Long userProfileId,
                          String firstName,
                          String lastName,
                          String email) {
}
