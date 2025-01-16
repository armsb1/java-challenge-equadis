package javachallengeequadiscustomer.accountservice.repository;

import javachallengeequadiscustomer.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Account repository.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Find by account number optional.
     *
     * @param accountNumber the account number
     * @return the optional
     */
    Optional<Account> findByAccountNumber(Long accountNumber);

    /**
     * Find all by user profile id list.
     *
     * @param userProfileId the user profile id
     * @return the list
     */
    List<Account> findAllByUserProfileId(Long userProfileId);

    /**
     * Exists by account number boolean.
     *
     * @param accountNumber the account number
     * @return the boolean
     */
    boolean existsByAccountNumber(Long accountNumber);
}
