package at.accounting_otter;

import at.accounting_otter.dto.DebitDTO;
import at.accounting_otter.dto.TransactionDTO;
import at.accounting_otter.dto.UserDTO;
import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class EntityMapper {

    @Inject
    DatabaseAdapter databaseAdapter;

    UserDTO userEntityToDTO(User user) {
        if (user == null) {
            return null;
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUsername(user.getUsername());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setUserPic(user.getUserPic());
            return userDTO;
        }
    }

    User userDTOToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUserPic(userDTO.getUserPic());
        return user;
    }

    TransactionDTO transactionEntityToDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        } else {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setTransactionId(transaction.getTransactionId());
            transactionDTO.setUserId(transaction.getUser().getUserId());
            transactionDTO.setDate(transaction.getDate());
            transactionDTO.setCategory(transaction.getCategory());
            transactionDTO.setShop(transaction.getShop());
            transactionDTO.setDescription(transaction.getDescription());
            transactionDTO.setBillId(transaction.getBillId());
            return transactionDTO;
        }
    }

    Transaction transactionDTOToEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionDTO.getTransactionId());
        transaction.setUser(databaseAdapter.getUser(transactionDTO.getUserId()));
        transaction.setDate(transactionDTO.getDate());
        transaction.setCategory(transactionDTO.getCategory());
        transaction.setShop(transactionDTO.getShop());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setBillId(transactionDTO.getBillId());
        return transaction;
    }

    DebitDTO debitEntityToDTO(Debit debit) {
        if (debit == null) {
            return null;
        } else {
            DebitDTO debitDTO = new DebitDTO();
            debitDTO.setDebitId(debit.getDebitId());
            debitDTO.setTransactionId(debit.getTransaction().getTransactionId());
            debitDTO.setPayerId(debit.getPayer().getUserId());
            debitDTO.setDebtorId(debit.getDebtor().getUserId());
            debitDTO.setAmount(debit.getAmount());
            return debitDTO;
        }
    }

    Debit debitDTOToEntity(DebitDTO debitDTO) {
        Debit debit = new Debit();
        debit.setDebitId(debitDTO.getDebitId());
        debit.setTransaction(databaseAdapter.getTransaction(debitDTO.getTransactionId()));
        debit.setPayer(databaseAdapter.getUser(debitDTO.getPayerId()));
        debit.setDebtor(databaseAdapter.getUser(debitDTO.getDebtorId()));
        debit.setAmount(debitDTO.getAmount());
        return debit;
    }

}
