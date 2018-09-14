package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

public interface DatabaseAdapter {

    void createUserTable();

    void createTransactionTable();

    void createDebitTable();


    User createUser(User user);

    User getUser(long userId);

    User updateUser(User user);

    void removeUser();

    User findUserByUsername(String username);


    Transaction createTransaction(Transaction transaction);

    Transaction getTransaction(long transactionId);

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(long transactionId);


    Debit createDebit(Debit debit);

    Debit getDebit(long debitId);

    List<Debit> getDebitsByTransactionId(long transactionId);

    Debit updateDebit(Debit debit);

    void deleteDebit(long debitId);

}
