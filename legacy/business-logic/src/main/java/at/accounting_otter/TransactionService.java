package at.accounting_otter;

import at.accounting_otter.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transaction) throws ObjectNotFoundException;

    TransactionDTO getTransaction(int transactionId);

    List<TransactionDTO> getTransactions(int startIndex, int endIndex) throws IllegalArgumentException;

    TransactionDTO updateTransaction(TransactionDTO transaction) throws ObjectNotFoundException;

    void deleteTransaction(int transactionId) throws ObjectNotFoundException;

}
