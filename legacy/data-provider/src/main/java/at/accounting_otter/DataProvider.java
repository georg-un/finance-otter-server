package at.accounting_otter;

import at.accounting_otter.dto.DebitDTO;
import at.accounting_otter.dto.TransactionDTO;
import at.accounting_otter.dto.UserDTO;

import java.util.List;


public interface DataProvider {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUser(int userId);

    UserDTO getUser(String username);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UserDTO userDTO) throws ObjectNotFoundException;


    TransactionDTO createTransaction(TransactionDTO transactionDTO) throws ObjectNotFoundException;

    TransactionDTO getTransaction(int transactionId);

    List<TransactionDTO> getTransactions(int startIndex, int endIntex);

    TransactionDTO updateTransaction(TransactionDTO transactionDTO) throws ObjectNotFoundException;

    void deleteTransaction(int transactionId) throws ObjectNotFoundException;


    DebitDTO createDebit(DebitDTO debitDTO) throws ObjectNotFoundException;

    DebitDTO getDebit(int debitId);

    List<DebitDTO> getDebitsByTransactionId(int transactionId);

    double getSumAmountByTransactionId(int transactionId);

    double getCreditByUserId(int userId);

    double getLiabilityByUserId(int userId);

    void deleteDebit(int debitId) throws ObjectNotFoundException;

}
