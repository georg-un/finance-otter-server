package at.accounting_otter.rest;

import at.accounting_otter.DebitService;
import at.accounting_otter.ObjectNotFoundException;
import at.accounting_otter.UserService;
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

    @Inject
    private UserService userService;


    public TransactionToGet internalToGetTransaction(Transaction transaction) {
        TransactionToGet transactionToGet = new TransactionToGet();
        transactionToGet.setTransactionId(transaction.getTransactionId());
        transactionToGet.setUserId(transaction.getUser().getUserId());
        transactionToGet.setUsername(transaction.getUser().getUsername());
        transactionToGet.setDate(transaction.getDate());
        transactionToGet.setCategory(transaction.getCategory());
        transactionToGet.setShop(transaction.getShop());
        transactionToGet.setDescription(transaction.getDescription());
        transactionToGet.setBillId(transaction.getBillId());
        transactionToGet.setSumAmount(debitService.getSumAmountByTransactionId(transaction.getTransactionId()));

        return transactionToGet;
    }

    public List<TransactionToGet> listInternalToListGetTransaction(List<Transaction> transactions) {
        List<TransactionToGet> transactionsToGet = new ArrayList<>();

        for (Transaction transaction : transactions) {
            transactionsToGet.add(internalToGetTransaction(transaction));
        }
        return  transactionsToGet;
    }


    public UserToGet internalToGetUser(User user) throws ObjectNotFoundException {
        UserToGet userToGet = new UserToGet();
        userToGet.setUserId(user.getUserId());
        userToGet.setUsername(user.getUsername());
        userToGet.setSumDebitAmounts(debitService.getBalanceByUserId(user.getUserId()));

        return userToGet;
    }

    public List<UserToGet> listInternalToListGetUser(List<User> users) throws ObjectNotFoundException {
        List<UserToGet> usersToGet = new ArrayList<>();
        for (User user : users) {
            usersToGet.add( internalToGetUser(user) );
        }
        return usersToGet;
    }


    private DebitToGet internalToGetDebit(Debit debit) {
        DebitToGet debitToGet = new DebitToGet();
        debitToGet.setDebitId(debit.getDebitId());
        debitToGet.setDebtorId(debit.getDebtor().getUserId());
        debitToGet.setDebtorName(debit.getDebtor().getUsername());
        debitToGet.setAmount(debit.getAmount());

        return debitToGet;
    }

    private Debit postToInternalDebit(DebitToPost debitToPost, int payerId) throws ObjectNotFoundException {
        if (userService.getUser(debitToPost.getDebtorId()) == null) {
            throw new ObjectNotFoundException("User with id " + debitToPost.getDebtorId() + " not found.");
        } else if (userService.getUser(payerId) == null) {
            throw new ObjectNotFoundException("User with id " + payerId + " not found.");
        }
        Debit debit = new Debit();
        debit.setPayer(userService.getUser(payerId));
        debit.setDebtor(userService.getUser(debitToPost.getDebtorId()));
        debit.setAmount(debitToPost.getAmount());

        return debit;
    }

    public PaymentToGet internalToRestPayment(Payment payment, boolean includeDebits) {
        PaymentToGet paymentToGet = new PaymentToGet();
        paymentToGet.setTransactionId(payment.getTransaction().getTransactionId());
        paymentToGet.setUserId(payment.getTransaction().getUser().getUserId());
        paymentToGet.setUsername(payment.getTransaction().getUser().getUsername());
        paymentToGet.setDate(payment.getTransaction().getDate());
        paymentToGet.setCategory(payment.getTransaction().getCategory());
        paymentToGet.setShop(payment.getTransaction().getShop());
        paymentToGet.setDescription(payment.getTransaction().getDescription());
        paymentToGet.setBillId(payment.getTransaction().getBillId());
        paymentToGet.setSumAmount(debitService.getSumAmountByTransactionId(payment.getTransaction().getTransactionId()));

        if (includeDebits) {
            List<DebitToGet> debits = new ArrayList<>();
            for (Debit debit : payment.getDebits()) {
                debits.add(internalToGetDebit(debit));
            }
            paymentToGet.setDebits(debits);
        } else {
            paymentToGet.setDebits(Collections.emptyList());
        }

        return paymentToGet;
    }

    public Payment postToInternalPayment(PaymentToPost paymentToPost) throws ObjectNotFoundException {
        Transaction transaction = new Transaction();
        transaction.setUser(userService.getUser(paymentToPost.getUserId()));
        transaction.setDate(paymentToPost.getDate());
        transaction.setCategory(paymentToPost.getCategory());
        transaction.setShop(paymentToPost.getShop());
        transaction.setDescription(paymentToPost.getDescription());
        transaction.setBillId(paymentToPost.getBillId());

        List<Debit> debits = new ArrayList<>();
        for (DebitToPost postedDebit : paymentToPost.getDebits()) {
            debits.add( postToInternalDebit(postedDebit, paymentToPost.getUserId()) );
        }

        return new Payment(transaction, debits);
    }

    public Payment putToInternalPayment(PaymentToPut paymentToPut) throws ObjectNotFoundException {
        Payment payment = postToInternalPayment(paymentToPut);
        payment.getTransaction().setTransactionId(paymentToPut.getTransactionId());

        return payment;
    }

}
