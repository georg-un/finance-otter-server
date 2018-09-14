package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

public interface OtterService {

    void createUserTable();

    void createTransactionTable();

    void createDebitTable();


    User createUser(String username);

    User getUser(long userId);

    User changeUsername(long userId, String newUsername);

    void removeUser(long userId);


    Payment createPayment(Payment payment);

    Payment getPayment(long transactionId);

    Payment updatePayment(Payment payment);

    void deletePayment(long transactionId);


}
