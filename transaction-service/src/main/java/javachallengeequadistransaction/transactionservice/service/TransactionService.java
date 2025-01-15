package javachallengeequadistransaction.transactionservice.service;

import javachallengeequadistransaction.transactionservice.entity.Transaction;
import javachallengeequadistransaction.transactionservice.enumeration.TransactionStatus;
import javachallengeequadistransaction.transactionservice.external.AccountExternalService;
import javachallengeequadistransaction.transactionservice.external.enumeration.AccountStatus;
import javachallengeequadistransaction.transactionservice.external.model.AccountDto;
import javachallengeequadistransaction.transactionservice.mapper.TransactionMapper;
import javachallengeequadistransaction.transactionservice.model.TransactionDto;
import javachallengeequadistransaction.transactionservice.model.TransactionResponse;
import javachallengeequadistransaction.transactionservice.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The type Transaction service.
 */
@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountExternalService accountExternalService;

    /**
     * Find all transactions list.
     *
     * @return the list
     */
    public List<TransactionDto> findAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionMapper::toTransactionDTO)
                .toList();
    }

    /**
     * Find all by account number list.
     *
     * @param accountNumber the account number
     * @return the list
     */
    public List<TransactionDto> findAllByAccountNumber(Long accountNumber) {
        return transactionRepository.findAllByAccountNumber(accountNumber)
                .stream()
                .map(TransactionMapper::toTransactionDTO)
                .toList();
    }

    /**
     * Deposit transaction transaction response.
     *
     * @param transactionDto the transaction dto
     * @return the transaction response
     */
    public TransactionResponse depositTransaction(TransactionDto transactionDto) {
        AccountDto transactionAccount = getValidatedTransactionAccountDto(transactionDto);
        BigDecimal updatedBalance = transactionAccount.totalBalance().add(transactionDto.amount());

        return getTransactionResponse(transactionDto, transactionAccount, updatedBalance);
    }

    /**
     * Withdraw transaction response.
     *
     * @param transactionDto the transaction dto
     * @return the transaction response
     */
    public TransactionResponse withdrawTransaction(TransactionDto transactionDto) {
        AccountDto transactionAccount = getValidatedTransactionAccountDto(transactionDto);

        BigDecimal updatedBalance = transactionAccount.totalBalance().subtract(transactionDto.amount());
        if (updatedBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Can't withdraw transaction because amount is higher than current balance");
        }

        return getTransactionResponse(transactionDto, transactionAccount, updatedBalance);
    }

    private AccountDto getValidatedTransactionAccountDto(TransactionDto transactionDto) {
        ResponseEntity<AccountDto> transactionAccountResponse = accountExternalService.getAccountByAccountNumber(transactionDto.accountNumber());

        if (!transactionAccountResponse.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException("Problems with the request");
        }

        AccountDto transactionAccount = transactionAccountResponse.getBody();
        if(transactionAccount == null) {
            throw new IllegalArgumentException("Account doesn't exist");
        }
        
        if (transactionAccount.accountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException("Account is not active and cannot make transaction");
        }
        return transactionAccount;
    }

    private TransactionResponse getTransactionResponse(TransactionDto transactionDto, AccountDto transactionAccount, BigDecimal updatedBalance) {

        AccountDto updatedAccount = AccountDto.builder()
                .accountNumber(transactionAccount.accountNumber())
                .accountStatus(transactionAccount.accountStatus())
                .totalBalance(updatedBalance)
                .userProfileId(transactionAccount.userProfileId())
                .build();

        boolean isUpdateSuccessful = accountExternalService.updateAccount(updatedAccount).getStatusCode().is2xxSuccessful();

        Transaction transaction = TransactionMapper.toTransaction(transactionDto);
        transaction.setStatus(isUpdateSuccessful ? TransactionStatus.COMPLETED : TransactionStatus.PENDING);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setReferenceId(generateTransactionReference());
        transactionRepository.save(transaction);

        return TransactionResponse.builder()
                .transactionDate(transaction.getTransactionDate())
                .referenceId(transaction.getReferenceId())
                .status(transaction.getStatus())
                .build();
    }


    private static Long generateTransactionReference() {
        return UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE;
    }

}
