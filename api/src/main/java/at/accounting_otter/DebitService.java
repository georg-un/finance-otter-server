package at.accounting_otter;

import at.accounting_otter.entity.Debit;

import java.util.List;


public interface DebitService {

    Debit createDebit(Debit debit) throws ObjectNotFoundException;

    Debit getDebit(int debitId);

    List<Debit> getDebitsByTransactionId(int transactionId);

    double getSumAmountByTransactionId(int transactionId);

    double getCreditByUserId(int userId) throws ObjectNotFoundException;

    double getLiabilityByUserId(int userId) throws ObjectNotFoundException;

    double getBalanceByUserId(int userId) throws ObjectNotFoundException;

    void deleteDebit(int debitId) throws ObjectNotFoundException;

}
