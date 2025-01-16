package javachallengeequadistransaction.transactionservice.external;

import javachallengeequadistransaction.transactionservice.external.config.FeignConfiguration;
import javachallengeequadistransaction.transactionservice.external.model.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * The interface Account external service.
 */
@FeignClient(name = "account-service", url = "${account-service.url}", configuration = FeignConfiguration.class)
public interface AccountExternalService {

    /**
     * Gets all accounts.
     *
     * @return the all accounts
     */
    @GetMapping
    ResponseEntity<List<AccountDto>> getAllAccounts();

    /**
     * Gets account by id.
     *
     * @param accountId the account id
     * @return the account by id
     */
    @GetMapping("{accountId}")
    ResponseEntity<AccountDto> getAccountById(@PathVariable("accountId") Long accountId);

    /**
     * Gets account by account number.
     *
     * @param accountNumber the account number
     * @return the account by account number
     */
    @GetMapping("/accountNumber/{accountNumber}")
    ResponseEntity<AccountDto> getAccountByAccountNumber(@PathVariable("accountNumber") Long accountNumber);

    /**
     * Gets all accounts by user profile id.
     *
     * @param userProfileId the user profile id
     * @return the all accounts by user profile id
     */
    @GetMapping("userId/{userProfileId}")
    ResponseEntity<List<AccountDto>> getAllAccountsByUserProfileId(@PathVariable("userProfileId") Long userProfileId);

    /**
     * Update account response entity.
     *
     * @param accountDto the update account request
     * @return the response entity
     */
    @PutMapping()
    ResponseEntity<Void> updateAccount(@RequestBody AccountDto accountDto);

    /**
     * Create account response entity.
     *
     * @param accountDto the account dto
     * @return the response entity
     */
    @PostMapping()
    ResponseEntity<Void> createAccount(@RequestBody AccountDto accountDto);
}
