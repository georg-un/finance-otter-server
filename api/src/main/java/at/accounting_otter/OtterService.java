package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.entity.User;


public interface OtterService {

    void createUserTable();

    void createTransactionTable();

    void createDebitTable();


    User createUser(User user);

    User getUser(int userId);

    User changeUsername(int userId, String newUsername) throws RuntimeException;

    void removeUser(int userId);


    Payment createPayment(Payment payment);

    Payment getPayment(int transactionId);

    Payment updatePayment(Payment payment);

    void deletePayment(int transactionId);


}
