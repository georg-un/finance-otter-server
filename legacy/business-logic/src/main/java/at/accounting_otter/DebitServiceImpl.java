package at.accounting_otter;

import at.accounting_otter.dto.DebitDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;


@RequestScoped
public class DebitServiceImpl implements DebitService {

    @Inject
    private DataProvider dataProvider;

    @Override
    public DebitDTO createDebit(DebitDTO debit) throws ObjectNotFoundException{
        return dataProvider.createDebit(debit);
    }

    @Override
    public DebitDTO getDebit(int debitId) {
        return dataProvider.getDebit(debitId);
    }

    @Override
    public List<DebitDTO> getDebitsByTransactionId(int transactionId) {
        return dataProvider.getDebitsByTransactionId(transactionId);
    }

    @Override
    public double getSumAmountByTransactionId(int transactionId) {
        if (dataProvider.getDebitsByTransactionId(transactionId).size() > 0) {
            return dataProvider.getSumAmountByTransactionId(transactionId);
        } else {
            return 0.00;
        }
    }

    @Override
    public double getCreditByUserId(int userId) throws ObjectNotFoundException{
        if (dataProvider.getUser(userId) == null) {
            throw new ObjectNotFoundException("User with id " + userId + " not found.");
        } else {
            return dataProvider.getCreditByUserId(userId);
        }
    }

    @Override
    public double getLiabilityByUserId(int userId) throws ObjectNotFoundException {
        if (dataProvider.getUser(userId) == null) {
            throw new ObjectNotFoundException("User with id " + userId + " not found.");
        } else {
            return dataProvider.getLiabilityByUserId(userId);
        }
    }

    @Override
    public double getBalanceByUserId(int userId) throws ObjectNotFoundException {
        return getCreditByUserId(userId) - getLiabilityByUserId(userId);
    }

    @Override
    public void deleteDebit(int debitId) throws ObjectNotFoundException {
        if (dataProvider.getDebit(debitId) == null) {
            throw new ObjectNotFoundException("Debit with id " + debitId + " not found.");
        } else {
            dataProvider.deleteDebit(debitId);
        }
    }

}
