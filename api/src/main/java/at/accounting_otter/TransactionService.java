package at.accounting_otter;

import at.accounting_otter.entity.Transaction;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    Transaction getTransaction(int transactionId);

    Transaction updateTransaction(Transaction transaction) throws ObjectNotFoundException;

    void deleteTransaction(int transactionId) throws ObjectNotFoundException;

}
