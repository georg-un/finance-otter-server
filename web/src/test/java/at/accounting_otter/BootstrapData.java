package at.accounting_otter;

import at.accounting_otter.dto.DebitDTO;
import at.accounting_otter.dto.Payment;
import at.accounting_otter.dto.TransactionDTO;
import at.accounting_otter.dto.UserDTO;
import at.accounting_otter.rest.RestObjectMapper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BootstrapData {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "at.accounting_otter")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private UserService userService;

    @Inject
    private PaymentService paymentService;

    private static UserDTO user1;
    private static UserDTO user2;
    private static UserDTO user3;


    @Test
    public void bootstrap01User() {
        user1 = UserDTO.builder().username("alice").firstName("Alice").lastName("Cooper").build();
        user2 = UserDTO.builder().username("bob").firstName("Bob").lastName("Ross").build();
        user3 = UserDTO.builder().username("charlie").firstName("Charlie").lastName("Brown").build();

        user1 = userService.createUser(user1);
        Assert.assertNotNull(user1);
        user2 = userService.createUser(user2);
        Assert.assertNotNull(user2);
        user3 = userService.createUser(user3);
        Assert.assertNotNull(user3);

    }

    @Test
    public void bootstrap02Payments() throws ObjectNotFoundException {

        // Payment 1
        TransactionDTO transaction1 = new TransactionDTO();
        transaction1.setUserId(user1.getUserId());
        transaction1.setShop("Bio Shop");
        transaction1.setCategory("Groceries");
        transaction1.setDate(new Date());

        DebitDTO debit1 = new DebitDTO();
        debit1.setPayerId(user1.getUserId());
        debit1.setDebtorId(user2.getUserId());
        debit1.setAmount(20.43);

        DebitDTO debit2 = new DebitDTO();
        debit2.setPayerId(user1.getUserId());
        debit2.setDebtorId(user3.getUserId());
        debit2.setAmount(20.43);

        Payment payment1 = new Payment();
        payment1.setTransaction(transaction1);
        payment1.setDebits(Arrays.asList(debit1, debit2));

        payment1 = paymentService.createPayment(payment1);
        Assert.assertNotNull(payment1);
        Assert.assertNotNull(payment1.getTransaction());
        Assert.assertNotNull(payment1.getDebits());


        // Payment 2
        TransactionDTO transaction2 = new TransactionDTO();
        transaction2.setUserId(user2.getUserId());
        transaction2.setShop("Pet Store");
        transaction2.setCategory("Pets");
        transaction2.setDate(new Date());
        transaction2.setDescription("Bought new dog food");

        DebitDTO debit3 = new DebitDTO();
        debit3.setPayerId(user2.getUserId());
        debit3.setDebtorId(user1.getUserId());
        debit3.setAmount(32.01);

        DebitDTO debit4 = new DebitDTO();
        debit4.setPayerId(user2.getUserId());
        debit4.setDebtorId(user3.getUserId());
        debit4.setAmount(32.01);

        Payment payment2 = new Payment();
        payment2.setTransaction(transaction2);
        payment2.setDebits(Arrays.asList(debit3, debit4));

        payment2 = paymentService.createPayment(payment2);
        Assert.assertNotNull(payment2);
        Assert.assertNotNull(payment2.getTransaction());
        Assert.assertNotNull(payment2.getDebits());


        // Payment 3
        TransactionDTO transaction3 = new TransactionDTO();
        transaction3.setUserId(user3.getUserId());
        transaction3.setShop("Drugstore");
        transaction3.setCategory("Hygiene");
        transaction3.setDate(new Date());

        DebitDTO debit5 = new DebitDTO();
        debit5.setPayerId(user3.getUserId());
        debit5.setDebtorId(user1.getUserId());
        debit5.setAmount(8.12);

        DebitDTO debit6 = new DebitDTO();
        debit6.setPayerId(user3.getUserId());
        debit6.setDebtorId(user1.getUserId());
        debit6.setAmount(14.20);

        Payment payment3 = new Payment();
        payment3.setTransaction(transaction3);
        payment3.setDebits(Arrays.asList(debit5, debit6));

        payment3 = paymentService.createPayment(payment3);
        Assert.assertNotNull(payment3);
        Assert.assertNotNull(payment3.getTransaction());
        Assert.assertNotNull(payment3.getDebits());
    }

}
