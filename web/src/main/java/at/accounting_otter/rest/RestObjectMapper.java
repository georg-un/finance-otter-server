package at.accounting_otter.rest;

import at.accounting_otter.DebitService;
import at.accounting_otter.UserService;
import at.accounting_otter.entity.Transaction;

import javax.inject.Inject;

public class RestObjectMapper {

    @Inject
    private UserService userService;

    @Inject
    private DebitService debitService;

    // TODO: Continue here: injected services are null. fix!

    public Transaction toInternalTransaction(TransactionToPost transactionToPost) {
        Transaction transaction = new Transaction();
        transaction.setUser(userService.getUser(transactionToPost.getUserId()));
        transaction.setDatetime(transactionToPost.getDatetime());
        transaction.setCategory(transactionToPost.getCategory());
        transaction.setShop(transactionToPost.getShop());
        transaction.setDescription(transactionToPost.getDescription());
        transaction.setBillId(transactionToPost.getBillId());

        return transaction;
    }

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
}
