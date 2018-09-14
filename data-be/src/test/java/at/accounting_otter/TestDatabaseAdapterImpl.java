package at.accounting_otter;

import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;

public class TestDatabaseAdapterImpl {

    private DatabaseAdapter databaseAdapter = new DatabaseAdapterImpl();

    private User test_user = new User();
    private Transaction test_transaction = new Transaction();


    // Clean up old test data
    private EntityManager em = Persistence.createEntityManagerFactory("otter_database").createEntityManager();

    @BeforeClass
    public void cleanUpOldTestData() {
        em.getTransaction().begin();
        //Query query = em.createNativeQuery("DELETE FROM debits;");
        //query.executeUpdate();
        Query query = em.createNativeQuery("DELETE FROM transactions;");
        query.executeUpdate();
        query = em.createNativeQuery("DELETE FROM users;");
        query.executeUpdate();
        em.getTransaction().commit();
    }


    @Test
    public void testCreateUserTable() {
        databaseAdapter.createUserTable();
    }

    @Test (dependsOnMethods = {"testCreateUserTable"})
    public void testCreateUser() {
        test_user.setUsername("fritz");
        test_user = databaseAdapter.createUser(test_user);
    }

    @Test (dependsOnMethods = {"testCreateUser"})
    public void testUpdateUser() {
        test_user.setUsername("banana_joe");
        System.out.print(test_user.getUsername());
        test_user = databaseAdapter.updateUser(test_user);
        System.out.print(test_user.getUsername());
    }

    @Test (dependsOnMethods = {"testUpdateUser"})
    public void testFindUser() {
        User user = databaseAdapter.findUserByUsername("banana_joe");
        System.out.print(user.getUserId() + ": " + user.getUsername());
    }

    @Test (dependsOnMethods = {"testCreateUserTable"})
    public void testCreateTransactionTable() {
        databaseAdapter.createTransactionTable();
    }

    @Test (dependsOnMethods = "testCreateTransactionTable")
    public void testCreateTransaction() {
        test_transaction.setUser(test_user);
        test_transaction.setShop("test_shop");
        test_transaction.setDescription("test_description");
        test_transaction.setDatetime(new Date());
        test_transaction.setBillId("test_id");

        databaseAdapter.createTransaction(test_transaction);
    }

    @Test (dependsOnMethods = "testCreateTransaction")
    public void testUpdateTransaction() {
        test_transaction.setShop("other_shop");

        test_transaction = databaseAdapter.updateTransaction(test_transaction);
    }

    @Test (dependsOnMethods = "testUpdateTransaction")
    public void testDeleteTransaction() {
        databaseAdapter.deleteTransaction(test_transaction.getTransactionId());
    }




}
