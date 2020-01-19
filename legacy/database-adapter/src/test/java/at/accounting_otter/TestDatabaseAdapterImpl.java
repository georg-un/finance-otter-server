package at.accounting_otter;

import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.*;
import java.util.Date;


// TODO: add tests for getter of not existing id's
// TODO: check if debit tests and methods are actually needed
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDatabaseAdapterImpl {

    private DatabaseAdapterImpl databaseAdapter = new DatabaseAdapterImpl();

    private static User test_user_1 = new User();
    private static User test_user_2 = new User();
    private static Transaction test_transaction = new Transaction();
    private static Debit test_debit = new Debit();
    private static final String USERNAME_1 = "username_1";
    private static final String USERNAME_2 = "username_2";


    // Load classes needed for test data cleanup
    private static EntityManager em =
            Persistence.createEntityManagerFactory("pers_unit_test").createEntityManager();

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
    }


    @Test
    public void test01CreateUserTable() {
        databaseAdapter.createUserTable();
    }

    @Test
    public void test02GetNonExistingUser() {
        Assert.assertNull(databaseAdapter.getUser(500));
    }

    @Test
    public void test03CreateUser() {
        test_user_1.setUsername(USERNAME_1);
        test_user_1.setFirstName("User");
        test_user_1.setLastName("One");
        test_user_1 = databaseAdapter.createUser(test_user_1);

        Assert.assertNotNull(databaseAdapter.getUser(test_user_1.getUserId()));
        Assert.assertEquals(USERNAME_1, databaseAdapter.getUser(test_user_1.getUserId()).getUsername());
        Assert.assertEquals("User", databaseAdapter.getUser(test_user_1.getUserId()).getFirstName());
        Assert.assertEquals("One", databaseAdapter.getUser(test_user_1.getUserId()).getLastName());
        Assert.assertTrue(
                databaseAdapter
                .getAllUsers()
                .stream()
                .anyMatch(user -> user.getUserId() == test_user_1.getUserId())
        );
    }

    @Test
    public void test04UpdateUser() {
        test_user_2.setUsername("temporary_username");
        test_user_2.setFirstName("Temporary");
        test_user_2.setLastName("Name");
        test_user_2 = databaseAdapter.createUser(test_user_2);

        Assert.assertNotNull(databaseAdapter.getUser(test_user_2.getUserId()));
        Assert.assertEquals(
                "temporary_username",
                databaseAdapter.getUser(test_user_2.getUserId()).getUsername());
        Assert.assertEquals("Temporary", databaseAdapter.getUser(test_user_2.getUserId()).getFirstName());
        Assert.assertEquals("Name", databaseAdapter.getUser(test_user_2.getUserId()).getLastName());
        Assert.assertTrue(
                databaseAdapter
                        .getAllUsers()
                        .stream()
                        .anyMatch(user -> user.getUserId() == test_user_2.getUserId())
        );


        test_user_2.setUsername(USERNAME_2);
        test_user_2.setFirstName("User");
        test_user_2.setLastName("Two");

        test_user_2 = databaseAdapter.updateUser(test_user_2);
        Assert.assertEquals(USERNAME_2, databaseAdapter.getUser(test_user_2.getUserId()).getUsername());
        Assert.assertEquals("User", databaseAdapter.getUser(test_user_2.getUserId()).getFirstName());
        Assert.assertEquals("Two", databaseAdapter.getUser(test_user_2.getUserId()).getLastName());
    }

    @Test
    public void test05FindUser() {
        User user = databaseAdapter.getUser(USERNAME_1);

        Assert.assertNotNull(user);
        Assert.assertEquals(test_user_1.getUserId(), user.getUserId());
        Assert.assertEquals(test_user_1.getUsername(), user.getUsername());
        Assert.assertEquals(USERNAME_1, user.getUsername());
    }

    @Test
    public void test06CreateTransactionTable() {
        databaseAdapter.createTransactionTable();
    }

    @Test
    public void test07CreateTransaction() {
        test_transaction.setUser(test_user_1);
        test_transaction.setShop("test_shop");
        test_transaction.setDescription("test_description");
        test_transaction.setDate(new Date());
        test_transaction.setBillId("test_id");

        test_transaction = databaseAdapter.createTransaction(test_transaction);

        Assert.assertNotNull(databaseAdapter.getTransaction(test_transaction.getTransactionId()));
        Assert.assertEquals(
                test_user_1.getUserId(),
                databaseAdapter.getTransaction(test_transaction.getTransactionId()).getUser().getUserId());
        Assert.assertEquals(
                "test_shop",
                databaseAdapter.getTransaction(test_transaction.getTransactionId()).getShop());
        Assert.assertEquals(
                "test_description",
                databaseAdapter.getTransaction(test_transaction.getTransactionId()).getDescription());
        Assert.assertEquals(
                test_transaction.getDate(),
                databaseAdapter.getTransaction(test_transaction.getTransactionId()).getDate());
        Assert.assertEquals(
                "test_id",
                databaseAdapter.getTransaction(test_transaction.getTransactionId()).getBillId());
    }

    @Test
    public void test08UpdateTransaction() {
        test_transaction.setShop("second_shop");

        test_transaction = databaseAdapter.updateTransaction(test_transaction);

        Assert.assertNotNull(databaseAdapter.getTransaction(test_transaction.getTransactionId()));
        Assert.assertEquals(
                "second_shop",
                databaseAdapter.getTransaction(test_transaction.getTransactionId()).getShop());
    }



    @Test
    public void test09CreateDebitTable() {
        databaseAdapter.createDebitTable();
    }

    @Test
    public void test10CreateDebit() {
        test_debit.setTransaction(test_transaction);
        test_debit.setPayer(test_user_1);
        test_debit.setDebtor(test_user_2);
        test_debit.setAmount(42);

        test_debit = databaseAdapter.createDebit(test_debit);

        Assert.assertNotNull(test_debit);
        Assert.assertNotEquals(0, test_debit.getDebitId());
        Assert.assertEquals(42.0, databaseAdapter.getDebit(test_debit.getDebitId()).getAmount(), 0);
        Assert.assertEquals(
                test_user_1.getUserId(),
                databaseAdapter.getDebit(test_debit.getDebitId()).getPayer().getUserId());
        Assert.assertEquals(
                test_user_2.getUserId(),
                databaseAdapter.getDebit(test_debit.getDebitId()).getDebtor().getUserId());

        // Check finding by transaction id
        Assert.assertNotNull(databaseAdapter.getDebitsByTransactionId(test_transaction.getTransactionId()));
        Assert.assertEquals(
                1,
                databaseAdapter.getDebitsByTransactionId(test_transaction.getTransactionId()).size());
        Assert.assertEquals(
                test_debit.getDebitId(),
                databaseAdapter.getDebitsByTransactionId(test_transaction.getTransactionId()).get(0).getDebitId());
    }

    @Test
    public void test11GetLiability() {
        Assert.assertEquals( 0, databaseAdapter.getLiabilityByUserId(test_user_1.getUserId()), 0);
        Assert.assertEquals(42, databaseAdapter.getLiabilityByUserId(test_user_2.getUserId()), 0);
    }

    @Test
    public void test12GetCredit() {
        Assert.assertEquals(42, databaseAdapter.getCreditByUserId(test_user_1.getUserId()), 0);
        Assert.assertEquals( 0, databaseAdapter.getCreditByUserId(test_user_2.getUserId()), 0);
    }

    @Test (expected = RollbackException.class)
    public void test13deleteTransactionWithExistingDebit() {
        databaseAdapter.deleteTransaction(test_transaction.getTransactionId());
    }

    @Test
    public void test14deleteDebit() {
        int debitId = test_debit.getDebitId();

        databaseAdapter.deleteDebit(test_debit.getDebitId());

        Assert.assertNull(databaseAdapter.getDebit(debitId));
        Assert.assertEquals(0, databaseAdapter.getLiabilityByUserId(test_user_1.getUserId()), 0);
        Assert.assertEquals(0, databaseAdapter.getLiabilityByUserId(test_user_2.getUserId()), 0);
    }

    @Test
    public void test15DeleteTransaction() {
        databaseAdapter.deleteTransaction(test_transaction.getTransactionId());

        Assert.assertNull(databaseAdapter.getTransaction(test_transaction.getTransactionId()));
    }

}
