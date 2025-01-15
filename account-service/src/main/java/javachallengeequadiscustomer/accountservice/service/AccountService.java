package javachallengeequadiscustomer.accountservice.service;

import javachallengeequadiscustomer.accountservice.entity.Account;
import javachallengeequadiscustomer.accountservice.enumeration.AccountStatus;
import javachallengeequadiscustomer.accountservice.external.clients.customer.CustomerExternalService;
import javachallengeequadiscustomer.accountservice.external.clients.customer.model.CustomerDto;
import javachallengeequadiscustomer.accountservice.mapper.AccountMapper;
import javachallengeequadiscustomer.accountservice.model.AccountDto;
import javachallengeequadiscustomer.accountservice.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Account service.
 */
@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerExternalService customerExternalService;


    /**
     * Find all accounts list.
     *
     * @return the list
     */
    public List<AccountDto> findAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMapper::toAccountDTO)
                .toList();
    }

    /**
     * Find account by id account dto.
     *
     * @param id the id
     * @return the account dto
     */
    public AccountDto findAccountById(Long id) {
        return AccountMapper.toAccountDTO(accountRepository.findById(id).orElseThrow());
    }

    /**
     * Find by account number account dto.
     *
     * @param accountNumber the account number
     * @return the account dto
     */
    public AccountDto findByAccountNumber(Long accountNumber) {
        return AccountMapper.toAccountDTO(accountRepository.findByAccountNumber(accountNumber).orElseThrow());
    }

    /**
     * Find by all accounts by user profile id list.
     *
     * @param userProfileId the user profile id
     * @return the list
     */
    public List<AccountDto> findByAllAccountsByUserProfileId(Long userProfileId) {
        return accountRepository.findAllByUserProfileId(userProfileId)
                .stream()
                .map(AccountMapper::toAccountDTO)
                .toList();
    }

    /**
     * Update account.
     *
     * @param accountDto the update account request
     */
    public void updateAccount(AccountDto accountDto) {
        Account accountToUpdate = accountRepository.findByAccountNumber(accountDto.accountNumber())
                .orElseThrow(() -> new IllegalArgumentException("Account doesn't exist"));

        AccountMapper.updateAccount(accountToUpdate, accountDto);
    }

    /**
     * Create account
     *
     * @param accountDto the account dto
     * @return the account
     */
    public Account createAccount(AccountDto accountDto) {
        ResponseEntity<CustomerDto> customerToCreateAccount = customerExternalService.getCustomerByUserProfileId(accountDto.userProfileId());
        if (!customerToCreateAccount.getStatusCode().is2xxSuccessful() ||
                customerToCreateAccount.getBody() == null) {
            throw new IllegalArgumentException("user doesn't exist");
        }

        Long accountNumber = accountDto.accountNumber() != null
                ? accountDto.accountNumber()
                : generateAccountNumber();

        if (accountRepository.existsByAccountNumber(accountNumber)) {
            throw new IllegalArgumentException("Account with that number already exists.");
        }

        Account account = AccountMapper.toAccount(accountDto);
        account.setAccountNumber(accountNumber);
        account.setAccountStatus(AccountStatus.ACTIVE);
        return accountRepository.save(account);
    }

    /**
     * Generate account number long.
     *
     * @return the long
     */
    public static Long generateAccountNumber() {
        return UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE;
    }


}
