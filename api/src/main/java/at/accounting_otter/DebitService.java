package at.accounting_otter;

import at.accounting_otter.entity.Debit;

import java.util.List;

public interface DebitService {

    Debit createDebit(Debit debit);

    Debit getDebit(int debitId);

    List<Debit> getDebitsByTransactionId(int transactionId);

    Debit updateDebit(Debit debit) throws ObjectNotFoundException;

    void deleteDebit(int debitId) throws ObjectNotFoundException;

}
