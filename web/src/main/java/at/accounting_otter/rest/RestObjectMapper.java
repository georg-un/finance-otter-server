package at.accounting_otter.rest;

import at.accounting_otter.DebitService;
import at.accounting_otter.ObjectNotFoundException;
import at.accounting_otter.UserService;
import at.accounting_otter.dto.Payment;
import at.accounting_otter.dto.DebitDTO;
import at.accounting_otter.dto.TransactionDTO;
import at.accounting_otter.dto.UserDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RestObjectMapper {

    @Inject
    private DebitService debitService;

    @Inject
    private UserService userService;


    public TransactionToGet internalToGetTransaction(TransactionDTO transaction) {
        TransactionToGet transactionToGet = new TransactionToGet();
        transactionToGet.setTransactionId(transaction.getTransactionId());
        transactionToGet.setUserId(transaction.getUserId());
        transactionToGet.setUsername(userService.getUser(transaction.getUserId()).getUsername());
        transactionToGet.setFirstName(userService.getUser(transaction.getUserId()).getFirstName());
        transactionToGet.setLastName(userService.getUser(transaction.getUserId()).getLastName());
        transactionToGet.setDate(transaction.getDate());
        transactionToGet.setCategory(transaction.getCategory());
        transactionToGet.setShop(transaction.getShop());
        transactionToGet.setDescription(transaction.getDescription());
        transactionToGet.setBillId(transaction.getBillId());
        transactionToGet.setSumAmount(debitService.getSumAmountByTransactionId(transaction.getTransactionId()));

        return transactionToGet;
    }

    public List<TransactionToGet> listInternalToListGetTransaction(List<TransactionDTO> transactions) {
        List<TransactionToGet> transactionsToGet = new ArrayList<>();

        for (TransactionDTO transaction : transactions) {
            transactionsToGet.add(internalToGetTransaction(transaction));
        }
        return  transactionsToGet;
    }


    public UserToGet internalToGetUser(UserDTO user) throws ObjectNotFoundException {
        UserToGet userToGet = new UserToGet();
        userToGet.setUserId(user.getUserId());
        userToGet.setUsername(user.getUsername());
        userToGet.setFirstName(user.getFirstName());
        userToGet.setLastName(user.getLastName());
        userToGet.setSumDebitAmounts(debitService.getBalanceByUserId(user.getUserId()));

        return userToGet;
    }

    public List<UserToGet> listInternalToListGetUser(List<UserDTO> users) throws ObjectNotFoundException {
        List<UserToGet> usersToGet = new ArrayList<>();
        for (UserDTO user : users) {
            usersToGet.add( internalToGetUser(user) );
        }
        return usersToGet;
    }


    private DebitToGet internalToGetDebit(DebitDTO debit) {
        DebitToGet debitToGet = new DebitToGet();
        debitToGet.setDebitId(debit.getDebitId());
        debitToGet.setDebtorId(debit.getDebtorId());
        debitToGet.setDebtorName(userService.getUser(debit.getDebtorId()).getUsername());
        debitToGet.setDebtorFirstName(userService.getUser(debit.getDebtorId()).getFirstName());
        debitToGet.setDebtorLastName(userService.getUser(debit.getDebtorId()).getLastName());
        debitToGet.setAmount(debit.getAmount());

        return debitToGet;
    }

    private DebitDTO postToInternalDebit(DebitToPost debitToPost, int payerId) throws ObjectNotFoundException {
        if (userService.getUser(debitToPost.getDebtorId()) == null) {
            throw new ObjectNotFoundException("User with id " + debitToPost.getDebtorId() + " not found.");
        } else if (userService.getUser(payerId) == null) {
            throw new ObjectNotFoundException("User with id " + payerId + " not found.");
        }
        DebitDTO debit = new DebitDTO();
        debit.setPayerId(payerId);
        debit.setDebtorId(debitToPost.getDebtorId());
        debit.setAmount(debitToPost.getAmount());

        return debit;
    }

    public PaymentToGet internalToRestPayment(Payment payment, boolean includeDebits) {
        PaymentToGet paymentToGet = new PaymentToGet();
        paymentToGet.setTransactionId(payment.getTransaction().getTransactionId());
        paymentToGet.setUserId(payment.getTransaction().getUserId());
        paymentToGet.setUsername(userService.getUser(payment.getTransaction().getUserId()).getUsername());
        paymentToGet.setFirstName(userService.getUser(payment.getTransaction().getUserId()).getFirstName());
        paymentToGet.setLastName(userService.getUser(payment.getTransaction().getUserId()).getLastName());
        paymentToGet.setDate(payment.getTransaction().getDate());
        paymentToGet.setCategory(payment.getTransaction().getCategory());
        paymentToGet.setShop(payment.getTransaction().getShop());
        paymentToGet.setDescription(payment.getTransaction().getDescription());
        paymentToGet.setBillId(payment.getTransaction().getBillId());
        paymentToGet.setSumAmount(debitService.getSumAmountByTransactionId(payment.getTransaction().getTransactionId()));

        if (includeDebits) {
            List<DebitToGet> debits = new ArrayList<>();
            for (DebitDTO debit : payment.getDebits()) {
                debits.add(internalToGetDebit(debit));
            }
            paymentToGet.setDebits(debits);
        } else {
            paymentToGet.setDebits(Collections.emptyList());
        }

        return paymentToGet;
    }

    public Payment postToInternalPayment(PaymentToPost paymentToPost, int userId) throws ObjectNotFoundException {
        TransactionDTO transaction = new TransactionDTO();
        transaction.setDate(paymentToPost.getDate());
        transaction.setCategory(paymentToPost.getCategory());
        transaction.setShop(paymentToPost.getShop());
        transaction.setDescription(paymentToPost.getDescription());
        transaction.setBillId(paymentToPost.getBillId());

        List<DebitDTO> debits = new ArrayList<>();
        for (DebitToPost postedDebit : paymentToPost.getDebits()) {
            debits.add( postToInternalDebit(postedDebit, userId) );
        }

        return new Payment(transaction, debits);
    }

    public Payment putToInternalPayment(PaymentToPut paymentToPut, int userId) throws ObjectNotFoundException {
        TransactionDTO transaction = new TransactionDTO();
        transaction.setDate(paymentToPut.getDate());
        transaction.setCategory(paymentToPut.getCategory());
        transaction.setShop(paymentToPut.getShop());
        transaction.setDescription(paymentToPut.getDescription());
        transaction.setBillId(paymentToPut.getBillId());
        transaction.setTransactionId(paymentToPut.getTransactionId());

        List<DebitDTO> debits = new ArrayList<>();
        for (DebitToPost postedDebit : paymentToPut.getDebits()) {
            debits.add( postToInternalDebit(postedDebit, userId) );
        }

        return new Payment(transaction, debits);
    }

}
