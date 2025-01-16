package javachallengeequadistransaction.transactionservice.mapper;

import javachallengeequadistransaction.transactionservice.entity.Transaction;
import javachallengeequadistransaction.transactionservice.model.TransactionDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The type Transaction mapper.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TransactionMapper {

    /**
     * To transaction transaction.
     *
     * @param accountDto the account dto
     * @return the transaction
     */
    public static Transaction toTransaction(TransactionDto accountDto) {
        return Transaction.builder()
                .referenceId(accountDto.referenceId())
                .accountNumber(accountDto.accountNumber())
                .amount(accountDto.amount())
                .comments(accountDto.comments())
                .build();
    }

    /**
     * To transaction dto transaction dto.
     *
     * @param account the account
     * @return the transaction dto
     */
    public static TransactionDto toTransactionDTO(Transaction account) {
        return TransactionDto.builder()
                .referenceId(account.getReferenceId())
                .accountNumber(account.getAccountNumber())
                .amount(account.getAmount())
                .comments(account.getComments())
                .build();
    }

}
