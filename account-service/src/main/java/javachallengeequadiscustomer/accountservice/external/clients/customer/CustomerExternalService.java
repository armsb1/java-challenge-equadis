package javachallengeequadiscustomer.accountservice.external.clients.customer;

import javachallengeequadiscustomer.accountservice.external.clients.customer.model.CustomerDto;
import javachallengeequadiscustomer.accountservice.external.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * The interface Customer external service.
 */
@FeignClient(name = "customer-service", url = "${customer-service.url}", configuration = FeignConfiguration.class)
public interface CustomerExternalService {

    /**
     * Gets all customers.
     *
     * @return the all customers
     */
    @GetMapping
    ResponseEntity<List<CustomerDto>> getAllCustomers();

    /**
     * Gets customer by id.
     *
     * @param userId the user id
     * @return the customer by id
     */
    @GetMapping("{userId}")
    ResponseEntity<CustomerDto> getCustomerById(@PathVariable("userId") Long userId);

    /**
     * Gets customer by user profile id.
     *
     * @param userProfileId the user profile id
     * @return the customer by user profile id
     */
    @GetMapping("/userProfileId/{userProfileId}")
    ResponseEntity<CustomerDto> getCustomerByUserProfileId(@PathVariable("userProfileId") Long userProfileId);

    /**
     * Create customer response entity.
     *
     * @param customerDto the customer dto
     * @return the response entity
     */
    @PostMapping
    ResponseEntity<Void> createCustomer(@RequestBody CustomerDto customerDto);
}
