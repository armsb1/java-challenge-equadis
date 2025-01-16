package javachallengeequadiscustomer.customerservice.service;

import javachallengeequadiscustomer.customerservice.entity.Customer;
import javachallengeequadiscustomer.customerservice.exception.DuplicateResourceException;
import javachallengeequadiscustomer.customerservice.exception.ResourceNotFoundException;
import javachallengeequadiscustomer.customerservice.mapper.CustomerMapper;
import javachallengeequadiscustomer.customerservice.model.CustomerDto;
import javachallengeequadiscustomer.customerservice.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Customer service.
 */
@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    /**
     * Find all customers list.
     *
     * @return the list
     */
    public List<CustomerDto> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toCustomerDTO)
                .toList();
    }

    /**
     * Find customer by id customer dto.
     *
     * @param id the id
     * @return the customer dto
     */
    public CustomerDto findCustomerById(Long id) {
        return CustomerMapper.toCustomerDTO(
                customerRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("There is no customer with the id " + id)));
    }

    /**
     * Find customer by user profile id customer dto.
     *
     * @param userProfileId the user profile id
     * @return the customer dto
     */
    public CustomerDto findCustomerByUserProfileId(Long userProfileId) {
        return CustomerMapper.toCustomerDTO(
                customerRepository
                        .findByUserProfileId(userProfileId)
                        .orElseThrow(() -> new ResourceNotFoundException("There is no customer with the userProfileId " + userProfileId)));
    }

    /**
     * Create customer customer.
     *
     * @param customerDto the customer dto
     * @return the customer
     */
    public Customer createCustomer(CustomerDto customerDto) {
        if (customerRepository.existsByEmail(customerDto.email())) {
            throw new DuplicateResourceException("There is already customer with this email");
        }

        Long userProfileId = customerDto.userProfileId() != null
                ? customerDto.userProfileId()
                : generateUserProfileId();

        if (customerRepository.existsByUserProfileId(userProfileId)) {
            throw new DuplicateResourceException("Customer with number " + userProfileId + " already exists.");
        }

        Customer customer = CustomerMapper.toCustomer(customerDto);
        customer.setUserProfileId(userProfileId);
        return customerRepository.save(customer);
    }

    private Long generateUserProfileId() {
        return UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE;
    }
}
