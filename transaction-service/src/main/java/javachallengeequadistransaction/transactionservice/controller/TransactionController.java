package javachallengeequadistransaction.transactionservice.controller;

import jakarta.validation.Valid;
import javachallengeequadistransaction.transactionservice.model.TransactionDto;
import javachallengeequadistransaction.transactionservice.model.TransactionResponse;
import javachallengeequadistransaction.transactionservice.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Transaction controller.
 */
@RestController
@RequestMapping("api/v1/transactions")
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;

    /**
     * Gets all transactions.
     *
     * @return the all transactions
     */
    @GetMapping()
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return ResponseEntity.ok().body(transactionService.findAllTransactions());
    }

    /**
     * Gets transactions by account number.
     *
     * @param accountNumber the account number
     * @return the transactions by account number
     */
    @GetMapping("{accountNumber}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByAccountNumber(@PathVariable("accountNumber") Long accountNumber) {
        return ResponseEntity.ok().body(transactionService.findAllByAccountNumber(accountNumber));
    }

    /**
     * Deposit transaction response entity.
     *
     * @param transactionDto the transaction dto
     * @return the response entity
     */
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> depositTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok().body(transactionService.depositTransaction(transactionDto));
    }

    /**
     * Withdraw transaction response entity.
     *
     * @param transactionDto the transaction dto
     * @return the response entity
     */
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdrawTransaction(@Valid @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok().body(transactionService.withdrawTransaction(transactionDto));
    }
}
