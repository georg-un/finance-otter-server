package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.entity.User;


public interface OtterService {

    void createUserTable();

    void createTransactionTable();

    void createDebitTable();


    User createUser(String username);

    User getUser(int userId);

    User changeUsername(int userId, String newUsername);

    void removeUser(long userId);


    Payment createPayment(Payment payment);

    Payment getPayment(long transactionId);

    Payment updatePayment(Payment payment);

    void deletePayment(long transactionId);


}
