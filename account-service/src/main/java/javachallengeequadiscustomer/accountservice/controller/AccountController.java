package javachallengeequadiscustomer.accountservice.controller;

import javachallengeequadiscustomer.accountservice.entity.Account;
import javachallengeequadiscustomer.accountservice.model.AccountDto;
import javachallengeequadiscustomer.accountservice.model.UpdateAccountRequest;
import javachallengeequadiscustomer.accountservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * The type Account controller.
 */
@RestController
@RequestMapping("api/v1/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Gets all accounts.
     *
     * @return the all accounts
     */
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok().body(accountService.findAllAccounts());
    }

    /**
     * Gets account by id.
     *
     * @param accountId the account id
     * @return the account by id
     */
    @GetMapping("{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("accountId") Long accountId) {
        return ResponseEntity.ok().body(accountService.findAccountById(accountId));
    }

    /**
     * Gets account by account number.
     *
     * @param accountNumber the account number
     * @return the account by account number
     */
    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<AccountDto> getAccountByAccountNumber(@PathVariable("accountNumber") Long accountNumber) {
        return ResponseEntity.ok().body(accountService.findByAccountNumber(accountNumber));
    }

    /**
     * Gets all accounts by user profile id.
     *
     * @param userProfileId the user profile id
     * @return the all accounts by user profile id
     */
    @GetMapping("userId/{userProfileId}")
    public ResponseEntity<List<AccountDto>> getAllAccountsByUserProfileId(@PathVariable("userProfileId") Long userProfileId) {
        return ResponseEntity.ok().body(accountService.findByAllAccountsByUserProfileId(userProfileId));
    }

    /**
     * Update account response entity.
     *
     * @param updateAccountRequest the update account request
     * @return the response entity
     */
    @PutMapping()
    public ResponseEntity<Void> updateAccount(@RequestBody UpdateAccountRequest updateAccountRequest) {
        accountService.updateAccount(updateAccountRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * Create account response entity.
     *
     * @param accountDto the account dto
     * @return the response entity
     */
    @PostMapping()
        public ResponseEntity<Void> createAccount(@RequestBody AccountDto accountDto) {
            Account createdAccount = accountService.createAccount(accountDto);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{accountId}")
                    .buildAndExpand(createdAccount.getAccountId())
                    .toUri();

            return ResponseEntity.created(location).build();

        }
}
