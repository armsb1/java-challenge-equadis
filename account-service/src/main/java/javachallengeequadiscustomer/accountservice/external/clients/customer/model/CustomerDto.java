package javachallengeequadiscustomer.accountservice.external.clients.customer.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * The type Customer dto.
 */
@Builder
public record CustomerDto(Long userProfileId,
                          @NotBlank(message = "First name cannot be blank")
                          @Size(max = 50, message = "First name must be at most 50 characters")
                          String firstName,
                          @NotBlank(message = "Last name cannot be blank")
                          @Size(max = 50, message = "Last name must be at most 50 characters")
                          String lastName,
                          @Email(message = "Email must be a valid email address")
                          String email) {
}
