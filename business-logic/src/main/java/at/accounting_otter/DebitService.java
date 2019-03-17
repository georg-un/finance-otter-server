package at.accounting_otter;

import at.accounting_otter.dto.DebitDTO;

import java.util.List;


public interface DebitService {

    DebitDTO createDebit(DebitDTO debit) throws ObjectNotFoundException;

    DebitDTO getDebit(int debitId);

    List<DebitDTO> getDebitsByTransactionId(int transactionId);

    double getSumAmountByTransactionId(int transactionId);

    double getCreditByUserId(int userId) throws ObjectNotFoundException;

    double getLiabilityByUserId(int userId) throws ObjectNotFoundException;

    double getBalanceByUserId(int userId) throws ObjectNotFoundException;

    void deleteDebit(int debitId) throws ObjectNotFoundException;

}
