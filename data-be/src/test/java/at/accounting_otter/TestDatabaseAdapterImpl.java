package at.accounting_otter;

import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;


// TODO: switch to JUnit
// TODO: add tests for getter of not existing id's
public class TestDatabaseAdapterImpl {

    private DatabaseAdapter databaseAdapter = new DatabaseAdapterImpl();

    private User test_user = new User();
    private Transaction test_transaction = new Transaction();


    // Load classes needed for test data cleanup
    private static EntityManager em = Persistence.createEntityManagerFactory("pers_unit_test").createEntityManager();

    @BeforeClass
    public static void cleanUpOldTestData() {
        // Drop all old tables
        em.getTransaction().begin();
        Query query = em.createNativeQuery("DROP TABLE IF EXISTS debits;");
        query.executeUpdate();
        query = em.createNativeQuery("DROP TABLE IF EXISTS transactions;");
        query.executeUpdate();
        query = em.createNativeQuery("DROP TABLE IF EXISTS users;");
        query.executeUpdate();
        em.getTransaction().commit();

        // TODO: put into own TestHelper class
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
        test_transaction.setDate(new Date());
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
