package at.accounting_otter;

import at.accounting_otter.entity.Debit;
import javassist.NotFoundException;

import javax.inject.Inject;
import java.util.List;

public class DebitServiceImpl implements DebitService {

    @Inject
    DatabaseAdapter databaseAdapter;

    @Override
    public Debit createDebit(Debit debit) {
        return databaseAdapter.createDebit(debit);
    }

    @Override
    public Debit getDebit(int debitId) {
        return databaseAdapter.getDebit(debitId);
    }

    @Override
    public List<Debit> getDebitsByTransactionId(int transactionId) {
        return databaseAdapter.getDebitsByTransactionId(transactionId);
    }

    @Override
    public Debit updateDebit(Debit debit) throws NotFoundException {
        if (databaseAdapter.getDebit(debit.getDebitId()) == null) {
            throw new NotFoundException("Debit with id " + debit.getDebitId() + " not found.");
        } else {
            return databaseAdapter.updateDebit(debit);
        }
    }

    @Override
    public void deleteDebit(int debitId) throws NotFoundException {
        if (databaseAdapter.getDebit(debitId) == null) {
            throw new NotFoundException("Debit with id " + debitId + " not found.");
        } else {
            databaseAdapter.deleteDebit(debitId);
        }
    }

}
