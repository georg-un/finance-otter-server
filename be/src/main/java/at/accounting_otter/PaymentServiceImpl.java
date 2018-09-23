package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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

        // delete obsolete Debits
        for (Debit debit : getObsoleteDebits(payment)) {
            debitService.deleteDebit(debit.getDebitId());
        }

        for (Debit debit : payment.getDebits()) {
            // add Debits which have id = 0 (assuming that they are new)
            if (debit.getDebitId() == 0) {
                debit.setTransaction(transaction);  // set transaction id just to be sure
                debitService.createDebit(debit);
            }
            // update all other debits
            else {
                debit.setTransaction(transaction);
                debitService.updateDebit(debit);
            }
        }

        transactionService.updateTransaction(payment.getTransaction());

        return getPayment(transaction.getTransactionId());
    }

    private List<Debit> getObsoleteDebits(Payment payment) {
        // get the current state from the database and extract the Debit-id's
        List<Debit> currentDebits = debitService.getDebitsByTransactionId(payment.getTransaction().getTransactionId());
        List<Integer> obsoleteDebitIds = currentDebits.stream().map(Debit::getDebitId).collect(Collectors.toList());

        // remove any Debit-id that also occurs in the payment (that means that only obsolete Debits remain)
        payment.getDebits().forEach(debit -> obsoleteDebitIds.remove(new Integer(debit.getDebitId())));

        // return the obsolete Debits
        return currentDebits.stream().filter(debit -> obsoleteDebitIds.contains(debit.getDebitId())).collect(Collectors.toList());
    }

    @Override
    public void deletePayment(int transactionId) throws ObjectNotFoundException {
        for (Debit debit : debitService.getDebitsByTransactionId(transactionId)) {
            debitService.deleteDebit(debit.getDebitId());
        }
        transactionService.deleteTransaction(transactionId);
    }

}
