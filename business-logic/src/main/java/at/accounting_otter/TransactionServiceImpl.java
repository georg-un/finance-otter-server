package at.accounting_otter;

import at.accounting_otter.dto.TransactionDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class TransactionServiceImpl implements TransactionService{

    @Inject
    private DataProvider dataProvider;


    @Override
    public TransactionDTO createTransaction(TransactionDTO transaction) throws ObjectNotFoundException {
        return dataProvider.createTransaction(transaction);
    }

    @Override
    public TransactionDTO getTransaction(int transactionId) {
        return dataProvider.getTransaction(transactionId);
    }

    @Override
    public List<TransactionDTO> getTransactions(int startIndex, int endIndex) throws IllegalArgumentException {
        if (endIndex > startIndex) {
            return dataProvider.getTransactions(startIndex, endIndex);
        } else {
            throw new IllegalArgumentException("endIndex must be greater than startIndex");
        }
    }

    @Override
    public TransactionDTO updateTransaction(TransactionDTO transactionUpdate) throws ObjectNotFoundException {
        // Make sure that the creator remains the owner of the transaction
        transactionUpdate.setUserId(
                dataProvider.getTransaction(transactionUpdate.getTransactionId()).getUserId()
        );
        return dataProvider.updateTransaction(transactionUpdate);
    }

    @Override
    public void deleteTransaction(int transactionId) throws ObjectNotFoundException {
        dataProvider.deleteTransaction(transactionId);
    }

}
