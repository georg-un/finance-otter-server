package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class PaymentServiceImpl implements PaymentService{

    @Inject
    TransactionService transactionService;

    @Inject
    DebitService debitService;

    @Inject
    UserService userService;


    @Override
    public Payment createPayment(Payment payment) throws ObjectNotFoundException {

        if (userService.getUser(payment.getTransaction().getUser().getUserId()) == null) {
            throw new ObjectNotFoundException("User with id " + payment.getTransaction().getUser().getUserId() + " not found");
        }
        Transaction transaction = transactionService.createTransaction(payment.getTransaction());

        for (Debit debit : payment.getDebits()) {

            if (userService.getUser(debit.getDebtor().getUserId()) == null) {
                throw new ObjectNotFoundException("User with id " + debit.getDebtor().getUserId() + " not found.");
            } else if (userService.getUser(debit.getPayer().getUserId()) == null) {
                throw new ObjectNotFoundException("User with id " + debit.getDebtor().getUserId() + " not found.");
            }

            debit.setTransaction(transaction);
            debitService.createDebit(debit);
        }

        return getPayment(payment.getTransaction().getTransactionId());
    }

    @Override
    public Payment getPayment(int transactionId) {

        Payment payment = new Payment();
        payment.setTransaction(transactionService.getTransaction(transactionId));
        payment.setDebits(debitService.getDebitsByTransactionId(transactionId));

        return payment;
    }

    // TODO: rollback if there occurs an error during method execution
    @Override
    public Payment updatePayment(Payment payment) throws ObjectNotFoundException {

        Transaction transaction = payment.getTransaction();

        // delete old Debits
        for (Debit debit : debitService.getDebitsByTransactionId(payment.getTransaction().getTransactionId())) {
            debitService.deleteDebit(debit.getDebitId());
        }

        for (Debit debit : payment.getDebits()) {
            debit.setTransaction(transaction);  // set transaction id just to be sure
            debitService.createDebit(debit);
        }

        transactionService.updateTransaction(payment.getTransaction());

        return getPayment(transaction.getTransactionId());
    }

    @Override
    public void deletePayment(int transactionId) throws ObjectNotFoundException {
        for (Debit debit : debitService.getDebitsByTransactionId(transactionId)) {
            debitService.deleteDebit(debit.getDebitId());
        }
        transactionService.deleteTransaction(transactionId);
    }

}
