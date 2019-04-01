package at.accounting_otter;

import at.accounting_otter.dto.DebitDTO;
import at.accounting_otter.dto.Payment;
import at.accounting_otter.dto.TransactionDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

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

        if (userService.getUser(payment.getTransaction().getUserId()) == null) {
            throw new ObjectNotFoundException("User with id " + payment.getTransaction().getUserId() + " not found");
        }
        TransactionDTO transaction = transactionService.createTransaction(payment.getTransaction());

        for (DebitDTO debit : payment.getDebits()) {

            if (userService.getUser(debit.getDebtorId()) == null) {
                throw new ObjectNotFoundException("User with id " + debit.getDebtorId() + " not found.");
            } else if (userService.getUser(debit.getPayerId()) == null) {
                throw new ObjectNotFoundException("User with id " + debit.getDebtorId() + " not found.");
            }

            debit.setTransactionId(transaction.getTransactionId());
            debitService.createDebit(debit);
        }

        return getPayment(transaction.getTransactionId());
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

        TransactionDTO transaction = payment.getTransaction();

        // delete old Debits
        for (DebitDTO debit : debitService.getDebitsByTransactionId(transaction.getTransactionId())) {
            debitService.deleteDebit(debit.getDebitId());
        }

        for (DebitDTO debit : payment.getDebits()) {
            debit.setDebitId(0); // set debitId to 0 just to be sure
            debit.setTransactionId(transaction.getTransactionId());  // set transaction id just to be sure
            debitService.createDebit(debit);
        }

        transaction = transactionService.updateTransaction(payment.getTransaction());

        return getPayment(transaction.getTransactionId());
    }

    @Override
    public void deletePayment(int transactionId) throws ObjectNotFoundException {
        for (DebitDTO debit : debitService.getDebitsByTransactionId(transactionId)) {
            debitService.deleteDebit(debit.getDebitId());
        }
        transactionService.deleteTransaction(transactionId);
    }

}
