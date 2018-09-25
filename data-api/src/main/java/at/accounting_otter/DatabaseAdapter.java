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

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(int transactionId);


    Debit createDebit(Debit debit);

    Debit getDebit(int debitId);

    List<Debit> getDebitsByTransactionId(int transactionId);

    double getSumAmountByTransactionId(int trasactionId);  // TODO: write unit test for this method

    Debit updateDebit(Debit debit);

    void deleteDebit(int debitId);

}
