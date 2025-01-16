package javachallengeequadiscustomer.customerservice.mapper;

import javachallengeequadiscustomer.customerservice.entity.Customer;
import javachallengeequadiscustomer.customerservice.model.CustomerDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The type Customer mapper.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomerMapper {

    /**
     * To customer.
     *
     * @param customerDto the customer dto
     * @return the customer
     */
    public static Customer toCustomer(CustomerDto customerDto) {
        return Customer.builder()
                .userProfileId(customerDto.userProfileId())
                .email(customerDto.email())
                .firstName(customerDto.firstName())
                .lastName(customerDto.lastName())
                .build();
    }

    /**
     * To customer dto.
     *
     * @param customer the customer
     * @return the customer dto
     */
    public static CustomerDto toCustomerDTO(Customer customer) {
        return CustomerDto.builder()
                .userProfileId(customer.getUserProfileId())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }
}
