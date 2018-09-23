package at.accounting_otter;

import at.accounting_otter.entity.Transaction;

import javax.inject.Inject;

public class TransactionServiceImpl implements TransactionService{

    @Inject
    private DatabaseAdapter databaseAdapter;


    @Override
    public Transaction createTransaction(Transaction transaction) {
        return databaseAdapter.createTransaction(transaction);
    }

    @Override
    public Transaction getTransaction(int transactionId) {
        return databaseAdapter.getTransaction(transactionId);
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) throws ObjectNotFoundException {
        if (databaseAdapter.getTransaction(transaction.getTransactionId()) == null) {
            throw new ObjectNotFoundException("Transaction with id " + transaction.getTransactionId() + " not found.");
        } else {
            return databaseAdapter.updateTransaction(transaction);
        }
    }

    @Override
    public void deleteTransaction(int transactionId) throws ObjectNotFoundException {
        if (databaseAdapter.getTransaction(transactionId) == null) {
            throw new ObjectNotFoundException("Transaction with id " + transactionId + " not found.");
        } else {
            databaseAdapter.deleteTransaction(transactionId);
        }
    }

}
