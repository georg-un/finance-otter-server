package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;
import at.accounting_otter.entity.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestOtterServiceIntegration {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class).addPackage("at.accounting_otter")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        // System.out.println("This: " + jar.toString(true)); TODO: refine
        return jar;
    }

    @Inject
    private OtterService otterService;

    private static User testUser1 = new User();
    private static User testUser2 = new User();
    private static User testUser3 = new User();
    private static User testUser4 = new User();
    private static Payment payment = new Payment();


    // Load classes needed for test data cleanup
    private static EntityManager em = Persistence.createEntityManagerFactory("pers_unit_test").createEntityManager();
    private static DatabaseAdapter databaseAdapter = new DatabaseAdapterImpl();

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

        // Create all tables
        databaseAdapter.createUserTable();
        databaseAdapter.createTransactionTable();
        databaseAdapter.createDebitTable();

    }

    @Test
    public void test01CreateUsers() {
        // Create 4 test user
        testUser1.setUsername("test_user_1_initial");
        testUser1 = otterService.createUser(testUser1);
        Assert.assertNotNull(testUser1);
        Assert.assertEquals("test_user_1_initial", otterService.getUser(testUser1.getUserId()).getUsername());

        testUser2.setUsername("test_user_2");
        testUser2 = otterService.createUser(testUser2);
        Assert.assertNotNull(testUser2);
        Assert.assertEquals("test_user_2", otterService.getUser(testUser2.getUserId()).getUsername());

        testUser3.setUsername("test_user_3");
        testUser3 = otterService.createUser(testUser3);
        Assert.assertNotNull(testUser3);
        Assert.assertEquals("test_user_3", otterService.getUser(testUser3.getUserId()).getUsername());

        testUser4.setUsername("test_user_4");
        testUser4 = otterService.createUser(testUser4);
        Assert.assertNotNull(testUser4);
        Assert.assertEquals("test_user_4", otterService.getUser(testUser4.getUserId()).getUsername());
    }

    @Test (expected = RuntimeException.class)
    public void test02UpdateTakenUsernamme() {
        // Try to change the username to a username which is already taken. Expect an exception
        otterService.changeUsername(testUser1.getUserId(), "test_user_2");
    }

    @Test
    public void test03UpdateUsername() {
        // Update the username to a username which is NOT already taken
        testUser1 = otterService.changeUsername(testUser1.getUserId(), "test_user_1");
    }


    @Test
    public void test04CreatePayment() {
        // Set up a payment with 1 transaction and 2 debits
        Transaction transaction = new Transaction();
        transaction.setUser(testUser1);
        transaction.setShop("Super Nice Market");
        transaction.setBillId("bill_id_123");
        transaction.setCategory("groceries");
        transaction.setDescription("I had sooooo much fun buying this");
        transaction.setDatetime(new Date());

        Debit debit1 = new Debit();
        debit1.setTransaction(transaction);
        debit1.setPayer(testUser1);
        debit1.setDebtor(testUser2);
        debit1.setAmount(50.00);

        Debit debit2 = new Debit();
        debit2.setTransaction(transaction);
        debit2.setPayer(testUser1);
        debit2.setDebtor(testUser3);
        debit2.setAmount(30.00);

        Payment setUpPayment = new Payment();
        setUpPayment.setTransaction(transaction);
        setUpPayment.setDebits(new ArrayList<>(Arrays.asList(debit1, debit2)));

        // Write the payment to the database
        payment = otterService.createPayment(setUpPayment);

        Assert.assertNotNull(payment);
        Assert.assertEquals(setUpPayment, payment);
        Assert.assertEquals(payment, otterService.getPayment(payment.getTransaction().getTransactionId()));
        Assert.assertNotNull(payment.getTransaction());
        Assert.assertNotNull(payment.getDebits());

    }


    @Test
    public void test05UpdatePayment() {

        // Extract the transaction and the 2 debits from the former payment
        Debit debit1 = payment.getDebits().get(0);
        Debit debit2 = payment.getDebits().get(1);
        Transaction transaction = payment.getTransaction();

        // Change the properties of the previously extracted objects
        debit1.setAmount(100.00);

        debit2.setDebtor(testUser4);
        debit2.setAmount(300.00);

        transaction.setCategory("category_05");
        transaction.setShop("shop_05");
        transaction.setDescription("description_05");
        transaction.setDatetime(new Date());
        transaction.setBillId("bill_id_05");

        // Create updated payment
        Payment updatedPayment = new Payment();
        updatedPayment.setTransaction(transaction);
        updatedPayment.setDebits(new ArrayList<>(Arrays.asList(debit1, debit2)));

        // Update payment on database
        payment = otterService.updatePayment(updatedPayment);


        // Validate transaction
        Assert.assertEquals(transaction.getCategory(), payment.getTransaction().getCategory());
        Assert.assertEquals(transaction.getShop(), payment.getTransaction().getShop());
        Assert.assertEquals(transaction.getDescription(), payment.getTransaction().getDescription());
        Assert.assertEquals(transaction.getDatetime(), payment.getTransaction().getDatetime());
        Assert.assertEquals(transaction.getBillId(), payment.getTransaction().getBillId());


        // Validate debits
        Assert.assertEquals(2, payment.getDebits().size());

        Debit returnedDebit1 = payment.getDebits().stream()
                .filter(debit -> debit.getDebitId() == debit1.getDebitId())
                .findFirst().orElse(null);
        Debit returnedDebit2 = payment.getDebits().stream()
                .filter(debit -> debit.getDebitId() == debit2.getDebitId())
                .findFirst().orElse(null);

        Assert.assertNotNull(returnedDebit1);
        Assert.assertNotNull(returnedDebit2);


        // Debit 1 (expected to be the same)
        Assert.assertEquals(debit1.getAmount(), returnedDebit1.getAmount(), 0);
        Assert.assertEquals(debit1.getDebtor().getUserId(), returnedDebit1.getDebtor().getUserId());
        Assert.assertEquals(debit1.getPayer().getUserId(), returnedDebit1.getPayer().getUserId());
        Assert.assertEquals(debit1.getDebitId(), returnedDebit1.getDebitId());
        Assert.assertEquals(debit1.getTransaction().getTransactionId(), returnedDebit1.getTransaction().getTransactionId());

    }


    @Test
    public void test06UpdatePaymentAddDebit() {

        // Extract the transaction and the 2 debits from the former payment
        Debit debit1 = payment.getDebits().get(0);
        Debit debit2 = payment.getDebits().get(1);
        Transaction transaction = payment.getTransaction();

        // Create new Debit
        Debit debit3 = new Debit();

        // Set up the third debit so that no transaction is defined and payer and debtor are the same person
        // Expect no exceptions and that transaction gets set automatically
        debit3.setPayer(testUser1);
        debit3.setDebtor(testUser1);
        debit3.setAmount(25.00);

        // Change the properties of the previously extracted objects
        debit1.setAmount(200.00);

        debit2.setDebtor(testUser4);
        debit2.setAmount(600.00);

        transaction.setCategory("category_06");
        transaction.setShop("shop_06");
        transaction.setDescription("description_06");
        transaction.setDatetime(new Date());
        transaction.setBillId("bill_id_06");

        // Create updated payment
        Payment updatedPayment = new Payment();
        updatedPayment.setTransaction(transaction);
        updatedPayment.setDebits(new ArrayList<>(Arrays.asList(debit1, debit2, debit3)));

        // Update payment on database
        payment = otterService.updatePayment(updatedPayment);

        payment = otterService.getPayment(updatedPayment.getTransaction().getTransactionId());

        //payment = otterService.getPayment(updatedPayment.getTransaction().getTransactionId());

        // Validate transaction
        Assert.assertEquals(transaction.getCategory(), payment.getTransaction().getCategory());
        Assert.assertEquals(transaction.getShop(), payment.getTransaction().getShop());
        Assert.assertEquals(transaction.getDescription(), payment.getTransaction().getDescription());
        Assert.assertEquals(transaction.getDatetime(), payment.getTransaction().getDatetime());
        Assert.assertEquals(transaction.getBillId(), payment.getTransaction().getBillId());


        // Validate debits
        Assert.assertEquals(3, payment.getDebits().size());

        Debit returnedDebit1 = payment.getDebits().stream()
                .filter(debit -> debit.getDebitId() == debit1.getDebitId())
                .findFirst().orElse(null);
        Debit returnedDebit2 = payment.getDebits().stream()
                .filter(debit -> debit.getDebitId() == debit2.getDebitId())
                .findFirst().orElse(null);
        Debit returnedDebit3 = payment.getDebits().stream()
                .filter(debit -> debit.getDebitId() == debit3.getDebitId())
                .findFirst().orElse(null);

        Assert.assertNotNull(returnedDebit1);
        Assert.assertNotNull(returnedDebit2);
        Assert.assertNotNull(returnedDebit3);

        // Debit 1 (expected to be the same)
        Assert.assertEquals(debit1.getAmount(), returnedDebit1.getAmount(), 0);
        Assert.assertEquals(debit1.getDebtor().getUserId(), returnedDebit1.getDebtor().getUserId());
        Assert.assertEquals(debit1.getPayer().getUserId(), returnedDebit1.getPayer().getUserId());
        Assert.assertEquals(debit1.getDebitId(), returnedDebit1.getDebitId());
        Assert.assertEquals(debit1.getTransaction().getTransactionId(), returnedDebit1.getTransaction().getTransactionId());

        // Debit 3 (expected to be the same except of transaction id)
        Assert.assertEquals(debit3.getAmount(), returnedDebit3.getAmount(), 0);
        Assert.assertEquals(debit3.getDebtor().getUserId(), returnedDebit3.getDebtor().getUserId());
        Assert.assertEquals(debit3.getPayer().getUserId(), returnedDebit3.getPayer().getUserId());
        Assert.assertEquals(debit3.getDebitId(), returnedDebit3.getDebitId());

        Assert.assertNotNull(returnedDebit3.getTransaction());
        Assert.assertEquals(transaction.getTransactionId(), returnedDebit3.getTransaction().getTransactionId());

    }


    @Test
    public void test07UpdatePaymentRemoveDebit() {

        // Extract the transaction and the 2 debits from the former payment
        Debit debit1 = payment.getDebits().get(0);
        // debit 2 gets not extracted !!
        Debit debit3 = payment.getDebits().get(2);

        Transaction transaction = payment.getTransaction();

        // Change the properties of the previously extracted objects
        debit1.setAmount(300.00);

        debit3.setAmount(1);

        transaction.setCategory("category_07");
        transaction.setShop("shop_07");
        transaction.setDescription("description_07");
        transaction.setDatetime(new Date());
        transaction.setBillId("bill_id_07");

        // Create updated payment
        Payment updatedPayment = new Payment();
        updatedPayment.setTransaction(transaction);
        updatedPayment.setDebits(new ArrayList<>(Arrays.asList(debit1, debit3)));

        // Update payment on database
        payment = otterService.updatePayment(updatedPayment);


        // Validate transaction
        Assert.assertEquals(transaction.getCategory(), payment.getTransaction().getCategory());
        Assert.assertEquals(transaction.getShop(), payment.getTransaction().getShop());
        Assert.assertEquals(transaction.getDescription(), payment.getTransaction().getDescription());
        Assert.assertEquals(transaction.getDatetime(), payment.getTransaction().getDatetime());
        Assert.assertEquals(transaction.getBillId(), payment.getTransaction().getBillId());


        // Validate debits
        Assert.assertEquals(2, payment.getDebits().size());

        Debit returnedDebit1 = payment.getDebits().stream()
                .filter(debit -> debit.getDebitId() == debit1.getDebitId())
                .findFirst().orElse(null);
        Debit returnedDebit3 = payment.getDebits().stream()
                .filter(debit -> debit.getDebitId() == debit3.getDebitId())
                .findFirst().orElse(null);

        Assert.assertNotNull(returnedDebit1);
        Assert.assertNotNull(returnedDebit3);


        // Debit 1 (expected to be the same)
        Assert.assertEquals(debit1.getAmount(), returnedDebit1.getAmount(), 0);
        Assert.assertEquals(debit1.getDebtor().getUserId(), returnedDebit1.getDebtor().getUserId());
        Assert.assertEquals(debit1.getPayer().getUserId(), returnedDebit1.getPayer().getUserId());
        Assert.assertEquals(debit1.getDebitId(), returnedDebit1.getDebitId());
        Assert.assertEquals(debit1.getTransaction().getTransactionId(), returnedDebit1.getTransaction().getTransactionId());


        // Debit 3 (expected to be the same except of transaction id)
        Assert.assertEquals(debit3.getAmount(), returnedDebit3.getAmount(), 0);
        Assert.assertEquals(debit3.getDebtor().getUserId(), returnedDebit3.getDebtor().getUserId());
        Assert.assertEquals(debit3.getPayer().getUserId(), returnedDebit3.getPayer().getUserId());
        Assert.assertEquals(debit3.getDebitId(), returnedDebit3.getDebitId());
        Assert.assertEquals(debit3.getTransaction().getTransactionId(), returnedDebit3.getTransaction().getTransactionId());

    }

    @Test
    public void test08DeletePayment() {
        otterService.deletePayment(payment.getTransaction().getTransactionId());

        Payment deletedPayment = otterService.getPayment(payment.getTransaction().getTransactionId());
        Assert.assertNull(deletedPayment.getTransaction());
        Assert.assertEquals(0, deletedPayment.getDebits().size());
    }


}
