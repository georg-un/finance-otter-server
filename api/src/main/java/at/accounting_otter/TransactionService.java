package at.accounting_otter;

import at.accounting_otter.entity.Transaction;
import javassist.NotFoundException;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    Transaction getTransaction(int transactionId);

    Transaction updateTransaction(Transaction transaction) throws NotFoundException;

    void deleteTransaction(int transactionId) throws NotFoundException;

}
