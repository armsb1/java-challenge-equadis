package javachallengeequadiscustomer.customerservice.repository;

import javachallengeequadiscustomer.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Customer repository.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find by user profile id optional.
     *
     * @param userProfileId the user profile id
     * @return the optional
     */
    Optional<Customer> findByUserProfileId(Long userProfileId);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);

    /**
     * Exists by user profile id boolean.
     *
     * @param userProfileId the user profile id
     * @return the boolean
     */
    boolean existsByUserProfileId(Long userProfileId);
}
