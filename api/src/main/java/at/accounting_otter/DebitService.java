package at.accounting_otter;

import at.accounting_otter.entity.Debit;
import javassist.NotFoundException;

import java.util.List;

public interface DebitService {

    Debit createDebit(Debit debit);

    Debit getDebit(int debitId);

    List<Debit> getDebitsByTransactionId(int transactionId);

    Debit updateDebit(Debit debit) throws NotFoundException;

    void deleteDebit(int debitId) throws NotFoundException;

}
