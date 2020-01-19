package at.accounting_otter;

import at.accounting_otter.dto.DebitDTO;
import at.accounting_otter.dto.TransactionDTO;
import at.accounting_otter.dto.UserDTO;
import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class DataProviderImpl implements DataProvider {

    @Inject
    DatabaseAdapter databaseAdapter;
    @Inject
    EntityMapper entityMapper;

    public UserDTO createUser(UserDTO userDTO) {
        User user = databaseAdapter.createUser(
                entityMapper.userDTOToEntity(userDTO)
        );
        return entityMapper.userEntityToDTO(user);
    }

    public UserDTO getUser(int userId) {
        return entityMapper.userEntityToDTO(
                databaseAdapter.getUser(userId)
        );
    }

    public UserDTO getUser(String username) {
        return entityMapper.userEntityToDTO(
                databaseAdapter.getUser(username)
        );
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = databaseAdapter.getAllUsers();
        return users
                .stream()
                .map(user -> entityMapper.userEntityToDTO(user))
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(UserDTO userDTO) throws ObjectNotFoundException {
        if (databaseAdapter.getUser(userDTO.getUserId()) == null) {
            throw new ObjectNotFoundException("User with id " + userDTO.getUserId() + " not found.");
        }

        User user = databaseAdapter.updateUser(
                entityMapper.userDTOToEntity(userDTO)
        );
        return entityMapper.userEntityToDTO(user);
    }


    public TransactionDTO createTransaction(TransactionDTO transactionDTO) throws ObjectNotFoundException {
        if (databaseAdapter.getUser(transactionDTO.getUserId()) == null) {
            throw new ObjectNotFoundException("User with id " + transactionDTO.getUserId() + " not found.");
        }

        Transaction transaction = databaseAdapter.createTransaction(
                entityMapper.transactionDTOToEntity(transactionDTO)
        );
        return entityMapper.transactionEntityToDTO(transaction);
    }

    public TransactionDTO getTransaction(int transactionId) {
        return entityMapper.transactionEntityToDTO(
                databaseAdapter.getTransaction(transactionId)
        );
    }

    public List<TransactionDTO> getTransactions(int startIndex, int endIntex) {
        List<Transaction> transactions = databaseAdapter.getTransactions(startIndex, endIntex);
        return transactions
                .stream()
                .map(transaction -> entityMapper.transactionEntityToDTO(transaction))
                .collect(Collectors.toList());
    }

    public TransactionDTO updateTransaction(TransactionDTO transactionDTO) throws ObjectNotFoundException {
        if (databaseAdapter.getTransaction(transactionDTO.getTransactionId()) == null) {
            throw new ObjectNotFoundException("Transaction with id " + transactionDTO.getTransactionId() + " not found.");
        }
        if (databaseAdapter.getUser(transactionDTO.getUserId()) == null) {
            throw new ObjectNotFoundException("User with id " + transactionDTO.getUserId() + " not found.");
        }

        Transaction transaction = databaseAdapter.updateTransaction(
                entityMapper.transactionDTOToEntity(transactionDTO)
        );
        return entityMapper.transactionEntityToDTO(transaction);
    }

    public void deleteTransaction(int transactionId) throws ObjectNotFoundException {
        if (databaseAdapter.getTransaction(transactionId) == null) {
            throw new ObjectNotFoundException("Transaction with id " + transactionId + " not found.");
        }
        databaseAdapter.deleteTransaction(transactionId);
    }


    public DebitDTO createDebit(DebitDTO debitDTO) throws ObjectNotFoundException {
        if (databaseAdapter.getUser(debitDTO.getPayerId()) == null) {
            throw new ObjectNotFoundException("User with id " + debitDTO.getPayerId() + " not found.");
        } else if (databaseAdapter.getUser(debitDTO.getDebtorId()) == null) {
            throw new ObjectNotFoundException("User with id " + debitDTO.getDebtorId() + " not found.");
        }

        Debit debit = databaseAdapter.createDebit(
                entityMapper.debitDTOToEntity(debitDTO)
        );
        return entityMapper.debitEntityToDTO(debit);
    }

    public DebitDTO getDebit(int debitId) {
        return entityMapper.debitEntityToDTO(
                databaseAdapter.getDebit(debitId)
        );
    }

    public List<DebitDTO> getDebitsByTransactionId(int transactionId) {
        List<Debit> debits = databaseAdapter.getDebitsByTransactionId(transactionId);
        return debits
                .stream()
                .map(debit -> entityMapper.debitEntityToDTO(debit))
                .collect(Collectors.toList());
    }


    public double getSumAmountByTransactionId(int transactionId) {
        return databaseAdapter.getSumAmountByTransactionId(transactionId);
    }

    public double getCreditByUserId(int userId) {
        return databaseAdapter.getCreditByUserId(userId);
    }

    public double getLiabilityByUserId(int userId) {
        return databaseAdapter.getLiabilityByUserId(userId);
    }

    public void deleteDebit(int debitId) throws ObjectNotFoundException {
        if (databaseAdapter.getDebit(debitId) == null) {
            throw new ObjectNotFoundException("Debit with id " + debitId + " not found.");
        }
        databaseAdapter.deleteDebit(debitId);
    }

}
