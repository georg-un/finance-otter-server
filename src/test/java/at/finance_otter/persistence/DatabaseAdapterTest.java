package at.finance_otter.persistence;

import at.finance_otter.persistence.entity.User;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@Transactional
@TestMethodOrder(MethodOrderer.Random.class)
public class DatabaseAdapterTest {

    @Inject
    DatabaseAdapter databaseAdapter;

    @Inject
    EntityManager em;

    @AfterEach
    public void cleanUpDatabase() {
        em.createNativeQuery("DELETE FROM purchases_debits").executeUpdate();
        em.createNativeQuery("DELETE FROM debits").executeUpdate();
        em.createNativeQuery("DELETE FROM purchases").executeUpdate();
        em.createNativeQuery("DELETE FROM users").executeUpdate();
    }

    @Test
    public void shouldCreateUser() {
        User user = new User();
        user.setUsername("user1");
        user.setFirstName("Anna");
        user.setLastName("Adler");
        databaseAdapter.createUser(user);

        Assertions.assertNotNull(user.getUserId());
    }

    @Test
    public void shouldFailIfUsernameIsTaken() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setFirstName("Anna");
        User user1_2 = new User();
        user1_2.setUsername("user1");
        user1_2.setFirstName("Bernd");
        databaseAdapter.createUser(user1);

        Assertions.assertThrows(PersistenceException.class, () -> databaseAdapter.createUser(user1_2));
    }

    @Test
    public void shouldFailIfNoUsernameIsSet() {
        User user1 = new User();
        user1.setFirstName("Anna");
        Assertions.assertThrows(PersistenceException.class, () -> databaseAdapter.createUser(user1));
    }

    @Test
    public void shouldFailIfNoFirstNameIsSet() {
        User user1 = new User();
        user1.setUsername("user1");
        Assertions.assertThrows(PersistenceException.class, () -> databaseAdapter.createUser(user1));
    }

    @Test
    public void shouldReturnNullIfUserNotFound() {
        Assertions.assertNull(databaseAdapter.getUser(42L));
    }

    @Test
    public void shouldUpdateUser() {
        User user = new User();
        user.setUsername("user1");
        user.setFirstName("Anna");
        user.setLastName("Adler");
        databaseAdapter.createUser(user);
        Assertions.assertEquals("Adler", databaseAdapter.getUser(1L).getLastName());

        user.setLastName("Antilope");
        databaseAdapter.updateUser(user);
        Assertions.assertEquals("Antilope", databaseAdapter.getUser(1L).getLastName());
    }

    @Test
    public void shouldCreatePurchase() {

    }



}
