package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;

import javax.inject.Inject;
import java.util.List;

public class OtterServiceImpl implements OtterService {

    @Inject
    private DatabaseAdapter databaseAdapter;

    @Override
    public void createUserTable() {
        databaseAdapter.createUserTable();
    }

    @Override
    public void createTransactionTable() {
        databaseAdapter.createTransactionTable();
    }

    @Override
    public void createDebitTable() {
        databaseAdapter.createDebitTable();
    }


    @Override
    public User createUser(String username) {
        if (doesUsernameAlreadyExist(username)) {
            throw new RuntimeException("Username does already exist.");
        } else {
            User user = new User();
            user.setUsername(username);
            user = databaseAdapter.createUser(user);
            return user;
        }
    }

    @Override
    public User getUser(int userId) {
        return databaseAdapter.getUser(userId);
    }

    @Override
    public User changeUsername(int userId, String newUsername) throws RuntimeException {
        System.out.println("id: " + userId);
        System.out.println("username: " + newUsername);
        if (doesUsernameAlreadyExist(newUsername)) {
            throw new RuntimeException("Username does already exist.");
        } else {
            User user = databaseAdapter.getUser(userId);
            user.setUsername(newUsername);
            user = databaseAdapter.updateUser(user);
            return user;
        }
    }

    private boolean doesUsernameAlreadyExist(String username) {
        User user = databaseAdapter.findUserByUsername(username);
        return user != null;
    }

    @Override
    public void removeUser(int userId) {
        databaseAdapter.removeUser();
    }


    @Override
    public Payment createPayment(Payment payment) {

        Transaction transaction = databaseAdapter.createTransaction(payment.getTransaction());
        for (Debit debit : payment.getDebits()) {
            debit.setTransaction(transaction);
            databaseAdapter.createDebit(debit);
        }

        return getPayment(payment.getTransaction().getTransactionId());
    }

    @Override
    public Payment getPayment(int transactionId) {

        Payment payment = new Payment();
        payment.setTransaction(databaseAdapter.getTransaction(transactionId));
        payment.setDebits(databaseAdapter.getDebitsByTransactionId(transactionId));

        return payment;
    }

    @Override
    public Payment updatePayment(Payment payment) {

        // TODO: this method will have to check if any new debits are added or if any old debits are removed and create/delete them respectively
        // TODO: id's of debits and transactions should not be changeable

        Transaction transaction = payment.getTransaction();

        for (Debit debit : payment.getDebits()) {
            debit.setTransaction(transaction);
            databaseAdapter.updateDebit(debit);
        }
        databaseAdapter.updateTransaction(payment.getTransaction());

        return getPayment(payment.getTransaction().getTransactionId());
    }

    @Override
    public void deletePayment(int transactionId) {
        for (Debit debit : databaseAdapter.getDebitsByTransactionId(transactionId)) {
            databaseAdapter.deleteDebit(debit.getDebitId());
        }
        databaseAdapter.deleteTransaction(transactionId);
    }


}
