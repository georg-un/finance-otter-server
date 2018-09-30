package at.accounting_otter;

import at.accounting_otter.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction) throws ObjectNotFoundException;

    Transaction getTransaction(int transactionId);

    List<Transaction> getTransactions(int startIndex, int endIndex) throws IllegalArgumentException;

    Transaction updateTransaction(Transaction transaction) throws ObjectNotFoundException;

    void deleteTransaction(int transactionId) throws ObjectNotFoundException;

}
