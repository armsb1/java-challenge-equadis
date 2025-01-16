package javachallengeequadiscustomer.accountservice.mapper;

import javachallengeequadiscustomer.accountservice.entity.Account;
import javachallengeequadiscustomer.accountservice.model.AccountDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The type Account mapper.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountMapper {

    /**
     * To account
     *
     * @param accountDto the account dto
     * @return the account
     */
    public static Account toAccount(AccountDto accountDto) {
        return Account.builder()
                .accountNumber(accountDto.accountNumber())
                .accountStatus(accountDto.accountStatus())
                .totalBalance(accountDto.totalBalance())
                .userProfileId(accountDto.userProfileId())
                .build();
    }

    /**
     * To account dto
     *
     * @param account the account
     * @return the account dto
     */
    public static AccountDto toAccountDTO(Account account) {
        return AccountDto.builder()
                .accountNumber(account.getAccountNumber())
                .accountStatus(account.getAccountStatus())
                .totalBalance(account.getTotalBalance())
                .userProfileId(account.getUserProfileId())
                .build();
    }

    /**
     * Update account.
     *
     * @param account    the account
     * @param accountDto the account dto
     */
    public static Account updateAccount(Account account, AccountDto accountDto) {
        account.setAccountNumber(accountDto.accountNumber());
        account.setTotalBalance(accountDto.totalBalance());
        account.setUserProfileId(accountDto.userProfileId());
        return account;
    }

}
