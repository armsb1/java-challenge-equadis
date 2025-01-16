package javachallengeequadiscustomer.customerservice.controller;

import jakarta.validation.Valid;
import javachallengeequadiscustomer.customerservice.entity.Customer;
import javachallengeequadiscustomer.customerservice.model.CustomerDto;
import javachallengeequadiscustomer.customerservice.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * The type Customer controller.
 */
@RestController
@RequestMapping("api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Gets all customers.
     *
     * @return the all customers
     */
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok().body(customerService.findAllCustomers());
    }

    /**
     * Gets customer by id.
     *
     * @param userId the user id
     * @return the customer by id
     */
    @GetMapping("{userId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(customerService.findCustomerById(userId));
    }

    /**
     * Gets customer by user profile id.
     *
     * @param userProfileId the user profile id
     * @return the customer by user profile id
     */
    @GetMapping("/userProfileId/{userProfileId}")
    public ResponseEntity<CustomerDto> getCustomerByUserProfileId(@PathVariable("userProfileId") Long userProfileId) {
        return ResponseEntity.ok().body(customerService.findCustomerByUserProfileId(userProfileId));
    }

    /**
     * Create customer response entity.
     *
     * @param customerDto the customer dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Long> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        Customer createdCustomer = customerService.createCustomer(customerDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(createdCustomer.getUserId())
                .toUri();

        return ResponseEntity.created(location).body(createdCustomer.getUserProfileId());
    }
}
