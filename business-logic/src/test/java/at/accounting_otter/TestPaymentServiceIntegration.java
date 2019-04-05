package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.dto.UserDTO;
import at.accounting_otter.dto.DebitDTO;
import at.accounting_otter.dto.TransactionDTO;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.*;
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
public class TestPaymentServiceIntegration {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "at.accounting_otter")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    UserService userService;

    @Inject
    PaymentService paymentService;


    private static UserDTO testUser1 = new UserDTO();
    private static UserDTO testUser2 = new UserDTO();
    private static UserDTO testUser3 = new UserDTO();
    private static UserDTO testUser4 = new UserDTO();
    private static Payment payment = new Payment();


    // Load classes needed for test data cleanup
    private static EntityManager em = Persistence.createEntityManagerFactory("pers_unit_test").createEntityManager();
    private static DatabaseAdapter databaseAdapter = new DatabaseAdapterImpl();

    @BeforeClass
    @AfterClass
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
        testUser1.setFirstName("Anna");
        testUser1.setLastName("Adler");
        testUser1 = userService.createUser(testUser1);
        Assert.assertNotNull(testUser1);
        Assert.assertEquals("test_user_1_initial", userService.getUser(testUser1.getUserId()).getUsername());
        Assert.assertEquals("Anna", userService.getUser(testUser1.getUserId()).getFirstName());
        Assert.assertEquals("Adler", userService.getUser(testUser1.getUserId()).getLastName());

        testUser2.setUsername("test_user_2");
        testUser2.setFirstName("Bernd");
        testUser2.setLastName("Bieber");
        testUser2 = userService.createUser(testUser2);
        Assert.assertNotNull(testUser2);
        Assert.assertEquals("test_user_2", userService.getUser(testUser2.getUserId()).getUsername());

        testUser3.setUsername("test_user_3");
        testUser3.setFirstName("Claudia");
        testUser3.setLastName("Chamäleon");
        testUser3 = userService.createUser(testUser3);
        Assert.assertNotNull(testUser3);
        Assert.assertEquals("test_user_3", userService.getUser(testUser3.getUserId()).getUsername());

        testUser4.setUsername("test_user_4");
        testUser4.setFirstName("Dietrich");
        testUser4.setLastName("Dachs");
        testUser4 = userService.createUser(testUser4);
        Assert.assertNotNull(testUser4);
        Assert.assertEquals("test_user_4", userService.getUser(testUser4.getUserId()).getUsername());
    }

    @Test (expected = RuntimeException.class)
    public void test02UpdateTakenUsernamme() throws ObjectNotFoundException {
        // Try to change the username to a username which is already taken. Expect an exception
        userService.changeUsername(testUser1.getUserId(), "test_user_2");
    }

    @Test
    public void test03UpdateUsername() throws ObjectNotFoundException {
        // Update the username to a username which is NOT already taken
        testUser1 = userService.changeUsername(testUser1.getUserId(), "test_user_1");
    }


    @Test
    public void test04CreatePayment() throws ObjectNotFoundException {
        // Set up a payment with 1 transaction and 2 debits
        TransactionDTO transaction = new TransactionDTO();
        transaction.setUserId(testUser1.getUserId());
        transaction.setShop("Super Nice Market");
        transaction.setBillId("bill_id_123");
        transaction.setCategory("groceries");
        transaction.setDescription("I had sooooo much fun buying this");
        transaction.setDate(new Date());

        DebitDTO debit1 = new DebitDTO();
        debit1.setTransactionId(transaction.getTransactionId());
        debit1.setPayerId(testUser1.getUserId());
        debit1.setDebtorId(testUser2.getUserId());
        debit1.setAmount(10.00);

        DebitDTO debit2 = new DebitDTO();
        debit2.setTransactionId(transaction.getTransactionId());
        debit2.setPayerId(testUser1.getUserId());
        debit2.setDebtorId(testUser3.getUserId());
        debit2.setAmount(20.00);

        Payment setUpPayment = new Payment();
        setUpPayment.setTransaction(transaction);
        setUpPayment.setDebits(new ArrayList<>(Arrays.asList(debit1, debit2)));

        // Write the payment to the database
        payment = paymentService.createPayment(setUpPayment);

        Assert.assertNotNull(payment);
        Assert.assertEquals(setUpPayment.getTransaction().getDate(), payment.getTransaction().getDate());
        Assert.assertEquals(payment, paymentService.getPayment(payment.getTransaction().getTransactionId()));
        Assert.assertNotNull(payment.getTransaction());
        Assert.assertNotNull(payment.getDebits());

    }


    @Test
    public void test05UpdatePayment() throws ObjectNotFoundException {

        // Extract the transaction and the 2 debits from the former payment
        DebitDTO debit1 = payment.getDebits().get(0);
        DebitDTO debit2 = payment.getDebits().get(1);
        TransactionDTO transaction = payment.getTransaction();

        // Change the properties of the previously extracted objects
        debit1.setAmount(100.00);

        debit2.setDebtorId(testUser4.getUserId());
        debit2.setAmount(200.00);

        transaction.setCategory("category_05");
        transaction.setShop("shop_05");
        transaction.setDescription("description_05");
        transaction.setDate(new Date());
        transaction.setBillId("bill_id_05");

        // Create updated payment
        Payment updatedPayment = new Payment();
        updatedPayment.setTransaction(transaction);
        updatedPayment.setDebits(new ArrayList<>(Arrays.asList(debit1, debit2)));

        // Update payment on database
        payment = paymentService.updatePayment(updatedPayment);


        // Validate transaction
        Assert.assertEquals(transaction.getCategory(), payment.getTransaction().getCategory());
        Assert.assertEquals(transaction.getShop(), payment.getTransaction().getShop());
        Assert.assertEquals(transaction.getDescription(), payment.getTransaction().getDescription());
        Assert.assertEquals(transaction.getDate(), payment.getTransaction().getDate());
        Assert.assertEquals(transaction.getBillId(), payment.getTransaction().getBillId());


        // Validate debits
        Assert.assertEquals(2, payment.getDebits().size());

        DebitDTO returnedDebit1 = payment.getDebits().stream()
                .filter(debit -> debit.getAmount() == debit1.getAmount())
                .findFirst().orElse(null);
        DebitDTO returnedDebit2 = payment.getDebits().stream()
                .filter(debit -> debit.getAmount() == debit2.getAmount())
                .findFirst().orElse(null);

        Assert.assertNotNull(returnedDebit1);
        Assert.assertNotNull(returnedDebit2);


        // Debit 1 (expected to be the same except of debit id)
        Assert.assertEquals(debit1.getDebtorId(), returnedDebit1.getDebtorId());
        Assert.assertEquals(debit1.getPayerId(), returnedDebit1.getPayerId());
        Assert.assertEquals(debit1.getTransactionId(), returnedDebit1.getTransactionId());
        Assert.assertNotEquals(debit1.getDebitId(), returnedDebit1.getDebitId());

    }


    @Test
    public void test06UpdatePaymentAddDebit() throws ObjectNotFoundException {

        // Extract the transaction and the 2 debits from the former payment
        DebitDTO debit1 = payment.getDebits().get(0);
        DebitDTO debit2 = payment.getDebits().get(1);
        TransactionDTO transaction = payment.getTransaction();

        // Create new Debit
        DebitDTO debit3 = new DebitDTO();

        // Set up the third debit so that no transaction is defined and payer and debtor are the same person
        // Expect no exceptions and that transaction gets set automatically
        debit3.setPayerId(testUser1.getUserId());
        debit3.setDebtorId(testUser1.getUserId());
        debit3.setAmount(25.00);

        // Change the properties of the previously extracted objects
        debit1.setAmount(250.00);

        debit2.setDebtorId(testUser4.getUserId());
        debit2.setAmount(500.00);

        transaction.setCategory("category_06");
        transaction.setShop("shop_06");
        transaction.setDescription("description_06");
        transaction.setDate(new Date());
        transaction.setBillId("bill_id_06");

        // Create updated payment
        Payment updatedPayment = new Payment();
        updatedPayment.setTransaction(transaction);
        updatedPayment.setDebits(new ArrayList<>(Arrays.asList(debit1, debit2, debit3)));

        // Update payment on database
        paymentService.updatePayment(updatedPayment);
        payment = paymentService.getPayment(updatedPayment.getTransaction().getTransactionId());

        // Validate transaction
        Assert.assertEquals(transaction.getCategory(), payment.getTransaction().getCategory());
        Assert.assertEquals(transaction.getShop(), payment.getTransaction().getShop());
        Assert.assertEquals(transaction.getDescription(), payment.getTransaction().getDescription());
        Assert.assertEquals(transaction.getDate(), payment.getTransaction().getDate());
        Assert.assertEquals(transaction.getBillId(), payment.getTransaction().getBillId());


        // Validate debits
        Assert.assertEquals(3, payment.getDebits().size());

        DebitDTO returnedDebit1 = payment.getDebits().stream()
                .filter(debit -> debit.getAmount() == debit1.getAmount())
                .findFirst().orElse(null);
        DebitDTO returnedDebit2 = payment.getDebits().stream()
                .filter(debit -> debit.getAmount() == debit2.getAmount())
                .findFirst().orElse(null);
        DebitDTO returnedDebit3 = payment.getDebits().stream()
                .filter(debit -> debit.getAmount() == debit3.getAmount())
                .findFirst().orElse(null);

        Assert.assertNotNull(returnedDebit1);
        Assert.assertNotNull(returnedDebit2);
        Assert.assertNotNull(returnedDebit3);

        // Debit 1 (expected to be the same except of debit it)
        Assert.assertEquals(debit1.getDebtorId(), returnedDebit1.getDebtorId());
        Assert.assertEquals(debit1.getPayerId(), returnedDebit1.getPayerId());
        Assert.assertEquals(debit1.getTransactionId(), returnedDebit1.getTransactionId());
        Assert.assertNotEquals(debit1.getDebitId(), returnedDebit1.getDebitId());

        // Debit 3 (expected to be the same except of debit it & transaction id)
        Assert.assertEquals(debit3.getDebtorId(), returnedDebit3.getDebtorId());
        Assert.assertEquals(debit3.getPayerId(), returnedDebit3.getPayerId());
        Assert.assertNotEquals(debit3.getDebitId(), returnedDebit3.getDebitId());

        Assert.assertNotEquals(0, returnedDebit3.getTransactionId());
        Assert.assertEquals(transaction.getTransactionId(), returnedDebit3.getTransactionId());

    }


    @Test
    public void test07UpdatePaymentRemoveDebit() throws ObjectNotFoundException {

        // Extract the transaction and the 2 debits from the former payment
        DebitDTO debit1 = payment.getDebits().get(0);
        // debit 2 gets not extracted !!
        DebitDTO debit3 = payment.getDebits().get(2);

        TransactionDTO transaction = payment.getTransaction();

        // Change the properties of the previously extracted objects
        debit1.setAmount(30.00);

        debit3.setAmount(40.00);

        transaction.setCategory("category_07");
        transaction.setShop("shop_07");
        transaction.setDescription("description_07");
        transaction.setDate(new Date());
        transaction.setBillId("bill_id_07");

        // Create updated payment
        Payment updatedPayment = new Payment();
        updatedPayment.setTransaction(transaction);
        updatedPayment.setDebits(new ArrayList<>(Arrays.asList(debit1, debit3)));

        // Update payment on database
        payment = paymentService.updatePayment(updatedPayment);


        // Validate transaction
        Assert.assertEquals(transaction.getCategory(), payment.getTransaction().getCategory());
        Assert.assertEquals(transaction.getShop(), payment.getTransaction().getShop());
        Assert.assertEquals(transaction.getDescription(), payment.getTransaction().getDescription());
        Assert.assertEquals(transaction.getDate(), payment.getTransaction().getDate());
        Assert.assertEquals(transaction.getBillId(), payment.getTransaction().getBillId());


        // Validate debits
        Assert.assertEquals(2, payment.getDebits().size());

        DebitDTO returnedDebit1 = payment.getDebits().stream()
                .filter(debit -> debit.getAmount() == debit1.getAmount())
                .findFirst().orElse(null);
        DebitDTO returnedDebit3 = payment.getDebits().stream()
                .filter(debit -> debit.getAmount() == debit3.getAmount())
                .findFirst().orElse(null);

        Assert.assertNotNull(returnedDebit1);
        Assert.assertNotNull(returnedDebit3);


        // Debit 1 (expected to be the same except of debit id)
        Assert.assertEquals(debit1.getDebtorId(), returnedDebit1.getDebtorId());
        Assert.assertEquals(debit1.getPayerId(), returnedDebit1.getPayerId());
        Assert.assertEquals(debit1.getTransactionId(), returnedDebit1.getTransactionId());
        Assert.assertNotEquals(debit1.getDebitId(), returnedDebit1.getDebitId());


        // Debit 3 (expected to be the same except of debit id & transaction id)
        Assert.assertEquals(debit3.getDebtorId(), returnedDebit3.getDebtorId());
        Assert.assertEquals(debit3.getPayerId(), returnedDebit3.getPayerId());
        Assert.assertEquals(debit3.getTransactionId(), returnedDebit3.getTransactionId());
        Assert.assertNotEquals(debit3.getDebitId(), returnedDebit3.getDebitId());

    }

    @Test
    public void test08DeletePayment() throws ObjectNotFoundException {
        paymentService.deletePayment(payment.getTransaction().getTransactionId());

        Payment deletedPayment = paymentService.getPayment(payment.getTransaction().getTransactionId());
        Assert.assertNull(deletedPayment.getTransaction());
        Assert.assertEquals(0, deletedPayment.getDebits().size());
    }


}
