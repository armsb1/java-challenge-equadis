package javachallengeequadiscustomer.customerservice;

import javachallengeequadiscustomer.customerservice.controller.CustomerController;
import javachallengeequadiscustomer.customerservice.entity.Customer;
import javachallengeequadiscustomer.customerservice.model.CustomerDto;
import javachallengeequadiscustomer.customerservice.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testGetAllCustomers() throws Exception {
        List<CustomerDto> customers = List.of(
                new CustomerDto(1L, "John", "Doe", "john.doe@example.com"),
                new CustomerDto(2L, "Jane", "Doe", "jane.doe@example.com")
        );

        when(customerService.findAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void testGetCustomerById() throws Exception {
        CustomerDto customer = new CustomerDto(1L, "John", "Doe", "john.doe@example.com");

        when(customerService.findCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testGetCustomerByUserProfileId() throws Exception {
        CustomerDto customer = new CustomerDto(1L, "John", "Doe", "john.doe@example.com");

        when(customerService.findCustomerByUserProfileId(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/userProfileId/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testCreateCustomer() throws Exception {
        Customer createdCustomer = new Customer(1L, 2L, "John", "Doe", "john.doe@example.com");

        when(customerService.createCustomer(any(CustomerDto.class))).thenReturn(createdCustomer);

        mockMvc.perform(post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "userProfileId": 2,
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "email": "john.doe@example.com"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/customers/1"))
                .andExpect(content().string("2"));
    }
}
