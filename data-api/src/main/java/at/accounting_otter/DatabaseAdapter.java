package at.accounting_otter;

import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;

import java.util.List;

public interface DatabaseAdapter {

    void createUserTable();

    void createTransactionTable();

    void createDebitTable();


    User createUser(User user);

    User getUser(int userId);

    User updateUser(User user);

    void removeUser();

    User findUserByUsername(String username);


    Transaction createTransaction(Transaction transaction);

    Transaction getTransaction(int transactionId);

    List<Transaction> getTransactions(int startIndex, int endIntex);

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(int transactionId);


    Debit createDebit(Debit debit);

    Debit getDebit(int debitId);

    List<Debit> getDebitsByTransactionId(int transactionId);

    double getSumAmountByTransactionId(int trasactionId);

    double getCreditByUserId(int userId);

    double getLiabilityByUserId(int userId);

    Debit updateDebit(Debit debit);

    void deleteDebit(int debitId);

}
