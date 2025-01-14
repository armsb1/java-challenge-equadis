package javachallengeequadiscustomer.customerservice.controller;

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

@RestController
@RequestMapping("api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok().body(customerService.findAllCustomers());
    }

    @GetMapping("{userId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(customerService.findCustomerById(userId));
    }
    @GetMapping("/userProfileId/{userProfileId}")
    public ResponseEntity<CustomerDto> getCustomerByUserProfileId(@PathVariable("userProfileId") Long userProfileId) {
        return ResponseEntity.ok().body(customerService.findCustomerByUserProfileId(userProfileId));
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDto customerDto) {
        Customer createdCustomer = customerService.createCustomer(customerDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(createdCustomer.getUserId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
