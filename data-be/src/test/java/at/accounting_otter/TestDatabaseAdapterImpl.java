package at.accounting_otter;

import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;


// TODO: add tests for getter of not existing id's
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDatabaseAdapterImpl {

    private DatabaseAdapterImpl databaseAdapter = new DatabaseAdapterImpl();

    private static User test_user = new User();
    private static Transaction test_transaction = new Transaction();
    private static final String USERNAME_1 = "fritz";
    private static final String USERNAME_2 = "banana_joe";


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
    public void test01CreateUserTable() {
        databaseAdapter.createUserTable();
    }

    @Test
    public void test02CreateUser() {
        test_user.setUsername(USERNAME_1);
        test_user.setFirstName("Fritz");
        test_user.setLastName("Phantom");
        test_user = databaseAdapter.createUser(test_user);

        Assert.assertNotNull(databaseAdapter.getUser(test_user.getUserId()));
        Assert.assertEquals(USERNAME_1, databaseAdapter.getUser(test_user.getUserId()).getUsername());
        Assert.assertEquals("Fritz", databaseAdapter.getUser(test_user.getUserId()).getFirstName());
        Assert.assertEquals("Phantom", databaseAdapter.getUser(test_user.getUserId()).getLastName());
        Assert.assertTrue(
                databaseAdapter
                .getAllUsers()
                .stream()
                .anyMatch(user -> user.getUserId() == test_user.getUserId())
        );
    }

    @Test
    public void test03UpdateUser() {
        test_user.setUsername(USERNAME_2);
        test_user.setFirstName("Banana");
        test_user.setLastName("Joe");
        Assert.assertNotNull(databaseAdapter.getUser(test_user.getUserId()));
        Assert.assertEquals(USERNAME_1, databaseAdapter.getUser(test_user.getUserId()).getUsername());

        test_user = databaseAdapter.updateUser(test_user);
        Assert.assertEquals(USERNAME_2, databaseAdapter.getUser(test_user.getUserId()).getUsername());
        Assert.assertEquals("Banana", databaseAdapter.getUser(test_user.getUserId()).getFirstName());
        Assert.assertEquals("Joe", databaseAdapter.getUser(test_user.getUserId()).getLastName());
    }

    @Test
    public void test04FindUser() {
        User user = databaseAdapter.getUser(USERNAME_2);

        Assert.assertNotNull(user);
        Assert.assertEquals(test_user.getUserId(), user.getUserId());
        Assert.assertEquals(test_user.getUsername(), user.getUsername());
        Assert.assertEquals(USERNAME_2, user.getUsername());
    }

    @Test
    public void test05CreateTransactionTable() {
        databaseAdapter.createTransactionTable();
    }

    @Test
    public void test06CreateTransaction() {
        test_transaction.setUser(test_user);
        test_transaction.setShop("test_shop");
        test_transaction.setDescription("test_description");
        test_transaction.setDate(new Date());
        test_transaction.setBillId("test_id");

        test_transaction = databaseAdapter.createTransaction(test_transaction);

        Assert.assertNotNull(databaseAdapter.getTransaction(test_transaction.getTransactionId()));
        Assert.assertEquals(test_user.getUserId(), databaseAdapter.getTransaction(test_transaction.getTransactionId()).getUser().getUserId());
        Assert.assertEquals("test_shop", databaseAdapter.getTransaction(test_transaction.getTransactionId()).getShop());
        Assert.assertEquals("test_description", databaseAdapter.getTransaction(test_transaction.getTransactionId()).getDescription());
        Assert.assertEquals(test_transaction.getDate(), databaseAdapter.getTransaction(test_transaction.getTransactionId()).getDate());
        Assert.assertEquals("test_id", databaseAdapter.getTransaction(test_transaction.getTransactionId()).getBillId());
    }

    @Test
    public void test07UpdateTransaction() {
        test_transaction.setShop("other_shop");

        test_transaction = databaseAdapter.updateTransaction(test_transaction);

        Assert.assertNotNull(databaseAdapter.getTransaction(test_transaction.getTransactionId()));
        Assert.assertEquals("other_shop", databaseAdapter.getTransaction(test_transaction.getTransactionId()).getShop());
    }

    @Test
    public void test08DeleteTransaction() {
        databaseAdapter.deleteTransaction(test_transaction.getTransactionId());

        Assert.assertNull(databaseAdapter.getTransaction(test_transaction.getTransactionId()));
    }




}
