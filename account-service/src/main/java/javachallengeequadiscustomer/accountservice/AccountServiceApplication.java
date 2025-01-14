package javachallengeequadiscustomer.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The type Account service application.
 */
@SpringBootApplication
@EnableFeignClients
public class AccountServiceApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

}
