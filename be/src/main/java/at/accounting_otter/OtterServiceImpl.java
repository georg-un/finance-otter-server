package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


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

    // TODO: rollback if there occurs an error during method execution
    @Override
    public Payment updatePayment(Payment payment) {

        Transaction transaction = payment.getTransaction();

        // delete obsolete Debits
        for (Debit debit : getObsoleteDebits(payment)) {
            databaseAdapter.deleteDebit(debit.getDebitId());
        }

        for (Debit debit : payment.getDebits()) {
            // add Debits which have id = 0 (assuming that they are new)
            if (debit.getDebitId() == 0) {
                debit.setTransaction(transaction);  // set transaction id just to be sure
                databaseAdapter.createDebit(debit);
            }
            // update all other debits
            else {
                debit.setTransaction(transaction);
                databaseAdapter.updateDebit(debit);
            }
        }

        databaseAdapter.updateTransaction(payment.getTransaction());

        return getPayment(transaction.getTransactionId());
    }

    private List<Debit> getObsoleteDebits(Payment payment) {
        // get the current state from the database and extract the Debit-id's
        List<Debit> currentDebits = databaseAdapter.getDebitsByTransactionId(payment.getTransaction().getTransactionId());
        List<Integer> obsoleteDebitIds = currentDebits.stream().map(Debit::getDebitId).collect(Collectors.toList());

        // remove any Debit-id that also occurs in the payment (that means that only obsolete Debits remain)
        payment.getDebits().forEach(debit -> obsoleteDebitIds.remove(new Integer(debit.getDebitId())));

        // return the obsolete Debits
        return currentDebits.stream().filter(debit -> obsoleteDebitIds.contains(debit.getDebitId())).collect(Collectors.toList());
    }

    @Override
    public void deletePayment(int transactionId) {
        for (Debit debit : databaseAdapter.getDebitsByTransactionId(transactionId)) {
            databaseAdapter.deleteDebit(debit.getDebitId());
        }
        databaseAdapter.deleteTransaction(transactionId);
    }


}
