package at.accounting_otter;

import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DatabaseAdapterImpl implements DatabaseAdapter {

    private EntityManager em = Persistence.createEntityManagerFactory("test_database").createEntityManager();

    @Override
    public void createUserTable() {

        em.getTransaction().begin();

        Query query = em.createNativeQuery("CREATE TABLE IF NOT EXISTS users (user_id SERIAL PRIMARY KEY, username varchar(256) UNIQUE);");
        query.executeUpdate();

        em.getTransaction().commit();

    }

    @Override
    public void createTransactionTable() {

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Query query = em.createNativeQuery("CREATE TABLE IF NOT EXISTS transactions (" +
                "transaction_id SERIAL PRIMARY KEY, " +
                "user_id integer REFERENCES users," +
                "datetime timestamp," +
                "category varchar(256)," +
                "shop varchar(256)," +
                "description text," +
                "bill_id varchar(512)" +
                ");");
        query.executeUpdate();

        transaction.commit();

    }

    @Override
    public void createDebitTable() {

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Query query = em.createNativeQuery("CREATE TABLE IF NOT EXISTS debits (" +
                "debit_id SERIAL PRIMARY KEY, " +
                "transaction_id integer REFERENCES transactions," +
                "payer_id integer REFERENCES users," +
                "debtor_id integer REFERENCES users," +
                "amount double precision" +
                ");");
        query.executeUpdate();

        transaction.commit();

    }


    @Override
    public User createUser(User user) {

        em.getTransaction().begin();
        em.persist( user );
        em.getTransaction( ).commit( );

        return user;
    }

    @Override
    public User getUser(int userId) {

        em.getTransaction().begin();
        List<User> user = em.createQuery("SELECT user FROM User user WHERE user.userId = :value1", User.class)
                .setParameter("value1", userId).getResultList();
        em.getTransaction().commit();

        if (user.isEmpty()) {
            return null;
        } else {
            return user.get(0);
        }
    }

    @Override
    public User updateUser(User user) {

        em.getTransaction().begin();
        User updated_user = em.merge(user);
        em.getTransaction().commit();

        return updated_user;
    }

    @Override
    public User findUserByUsername(String username) throws IndexOutOfBoundsException {

        em.getTransaction().begin();
        List<User> user = em.createQuery("SELECT user FROM User user WHERE user.username = :value1", User.class)
                .setParameter("value1", username).getResultList();
        em.getTransaction().commit();

        if (user.isEmpty()) {
            return null;
        } else {
            return user.get(0);
        }
    }

    @Override
    public void removeUser() {
        System.out.print("removeUser() is not implemented yet");
        // TODO: delete from database or just set inactive?

    }



    @Override
    public Transaction createTransaction(Transaction transaction) {

        em.getTransaction().begin();
        em.persist( transaction );
        em.getTransaction( ).commit( );

        return transaction;

    }

    @Override
    public Transaction getTransaction(int transactionId) {

        em.getTransaction().begin();
        Transaction transaction = em.createQuery("SELECT transaction FROM Transaction transaction WHERE transaction.transactionId = :value1",
                Transaction.class)
                .setParameter("value1", transactionId).getSingleResult();
        em.getTransaction().commit();

        return transaction;
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {

        em.getTransaction().begin();
        Transaction updated_transaction = em.merge(transaction);
        em.getTransaction().commit();

        return updated_transaction;

    }

    @Override
    public void deleteTransaction(int transactionId) {

        em.getTransaction().begin();
        Transaction transaction = em.find(Transaction.class, transactionId);
        em.remove(transaction);
        em.getTransaction().commit();

    }



    @Override
    public Debit createDebit(Debit debit) {

        em.getTransaction().begin();
        em.persist( debit );
        em.getTransaction( ).commit( );

        return debit;

    }

    @Override
    public Debit getDebit(int debitId) {

        em.getTransaction().begin();
        Debit debit = em.createQuery("SELECT debit FROM Debit debit WHERE debit.debitId = :value1", Debit.class)
                .setParameter("value1", debitId).getSingleResult();
        em.getTransaction().commit();

        return debit;
    }

    @Override
    public List<Debit> getDebitsByTransactionId(int transactionId) {

        em.getTransaction().begin();
        List<Debit> debits = em.createQuery("SELECT debit FROM Debit debit WHERE debit.transaction.transactionId = :value1", Debit.class)
                .setParameter("value1", transactionId).getResultList();
        em.getTransaction().commit();

        return debits;

    }

    @Override
    public Debit updateDebit(Debit debit) {

        em.getTransaction().begin();
        Debit updated_debit = em.merge(debit);
        em.getTransaction().commit();

        return updated_debit;

    }

    @Override
    public void deleteDebit(int debitId) {

        em.getTransaction().begin();
        Debit debit = em.find(Debit.class, debitId);
        em.remove(debit);
        em.getTransaction().commit();

    }

}
