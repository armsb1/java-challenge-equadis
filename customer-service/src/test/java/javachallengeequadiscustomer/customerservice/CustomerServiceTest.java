package javachallengeequadiscustomer.customerservice;

import javachallengeequadiscustomer.customerservice.entity.Customer;
import javachallengeequadiscustomer.customerservice.exception.DuplicateResourceException;
import javachallengeequadiscustomer.customerservice.exception.ResourceNotFoundException;
import javachallengeequadiscustomer.customerservice.model.CustomerDto;
import javachallengeequadiscustomer.customerservice.repository.CustomerRepository;
import javachallengeequadiscustomer.customerservice.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllCustomers() {
        List<Customer> customers = List.of(
                new Customer(1L, 1L, "John", "Doe", "john.doe@example.com"),
                new Customer(2L, 2L, "Jane", "Doe", "jane.doe@example.com")
        );

        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDto> result = customerService.findAllCustomers();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).firstName());
        assertEquals("Jane", result.get(1).firstName());
    }

    @Test
    void testFindCustomerById() {
        Customer customer = new Customer(1L, 1L, "John", "Doe", "john.doe@example.com");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        CustomerDto result = customerService.findCustomerById(1L);

        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());
        assertEquals("john.doe@example.com", result.email());
    }

    @Test
    void testFindCustomerById_NotFound() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.findCustomerById(1L));
    }

    @Test
    void testFindCustomerByUserProfileId() {
        Customer customer = new Customer(1L, 1L, "John", "Doe", "john.doe@example.com");

        when(customerRepository.findByUserProfileId(anyLong())).thenReturn(Optional.of(customer));

        CustomerDto result = customerService.findCustomerByUserProfileId(1L);

        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());
        assertEquals("john.doe@example.com", result.email());
    }

    @Test
    void testFindCustomerByUserProfileId_NotFound() {
        when(customerRepository.findByUserProfileId(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.findCustomerByUserProfileId(1L));
    }

    @Test
    void testCreateCustomer_Success() {
        CustomerDto customerDto = new CustomerDto(null, "John", "Doe", "john.doe@example.com");
        Customer savedCustomer = new Customer(1L, 12345L, "John", "Doe", "john.doe@example.com");

        when(customerRepository.existsByEmail(anyString())).thenReturn(false);
        when(customerRepository.existsByUserProfileId(anyLong())).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        Customer result = customerService.createCustomer(customerDto);

        assertEquals("John", result.getFirstName());
        assertEquals(12345L, result.getUserProfileId());
    }

    @Test
    void testCreateCustomer_EmailAlreadyExists() {
        CustomerDto customerDto = new CustomerDto(null, "John", "Doe", "john.doe@example.com");

        when(customerRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> customerService.createCustomer(customerDto));
    }

    @Test
    void testCreateCustomer_UserProfileIdAlreadyExists() {
        CustomerDto customerDto = new CustomerDto(12345L, "John", "Doe", "john.doe@example.com");

        when(customerRepository.existsByEmail(anyString())).thenReturn(false);
        when(customerRepository.existsByUserProfileId(anyLong())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> customerService.createCustomer(customerDto));
    }
}

