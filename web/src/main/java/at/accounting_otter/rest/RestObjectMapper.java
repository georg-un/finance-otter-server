package at.accounting_otter.rest;

import at.accounting_otter.DebitService;
import at.accounting_otter.ObjectNotFoundException;
import at.accounting_otter.dto.Payment;
import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RestObjectMapper {

    @Inject
    private DebitService debitService;


    public TransactionToGet toRestTransaction(Transaction transaction) {
        TransactionToGet transactionToGet = new TransactionToGet();
        transactionToGet.setTransactionId(transaction.getTransactionId());
        transactionToGet.setUserId(transaction.getUser().getUserId());
        transactionToGet.setUsername(transaction.getUser().getUsername());
        transactionToGet.setDatetime(transaction.getDatetime());
        transactionToGet.setCategory(transaction.getCategory());
        transactionToGet.setShop(transaction.getShop());
        transactionToGet.setDescription(transaction.getDescription());
        transactionToGet.setBillId(transaction.getBillId());
        transactionToGet.setSumAmount(debitService.getSumAmountByTransactionId(transaction.getTransactionId()));

        return transactionToGet;
    }


    public UserToGet toRestUser(User user) throws ObjectNotFoundException {
        UserToGet userToGet = new UserToGet();
        userToGet.setUserId(user.getUserId());
        userToGet.setUsername(user.getUsername());
        userToGet.setSumDebitAmounts(debitService.getBalanceByUserId(user.getUserId()));

        return userToGet;
    }


    private DebitToGet toRestDebit(Debit debit) {
        DebitToGet debitToGet = new DebitToGet();
        debitToGet.setDebtorId(debit.getDebtor().getUserId());
        debitToGet.setDebtorName(debit.getDebtor().getUsername());
        debitToGet.setAmount(debit.getAmount());

        return debitToGet;
    }


    public PaymentToGet toRestPayment(Payment payment, boolean includeDebits) {
        PaymentToGet paymentToGet = new PaymentToGet();
        paymentToGet.setTransactionId(payment.getTransaction().getTransactionId());
        paymentToGet.setUserId(payment.getTransaction().getUser().getUserId());
        paymentToGet.setUsername(payment.getTransaction().getUser().getUsername());
        paymentToGet.setDatetime(payment.getTransaction().getDatetime());
        paymentToGet.setCategory(payment.getTransaction().getCategory());
        paymentToGet.setShop(payment.getTransaction().getShop());
        paymentToGet.setDescription(payment.getTransaction().getDescription());
        paymentToGet.setBillId(payment.getTransaction().getBillId());
        paymentToGet.setSumAmount(debitService.getSumAmountByTransactionId(payment.getTransaction().getTransactionId()));

        if (includeDebits) {
            List<DebitToGet> debits = new ArrayList<>();
            for (Debit debit : payment.getDebits()) {
                debits.add(toRestDebit(debit));
            }
            paymentToGet.setDebits(debits);
        } else {
            paymentToGet.setDebits(Collections.emptyList());
        }

        return paymentToGet;
    }

}
