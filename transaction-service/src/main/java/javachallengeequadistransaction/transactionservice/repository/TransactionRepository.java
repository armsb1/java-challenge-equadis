package javachallengeequadistransaction.transactionservice.repository;

import javachallengeequadistransaction.transactionservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Transaction repository.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

     /**
      * Find all by account number list.
      *
      * @param accountNumber the account number
      * @return the list
      */
     List<Transaction> findAllByAccountNumber(Long accountNumber);
}
