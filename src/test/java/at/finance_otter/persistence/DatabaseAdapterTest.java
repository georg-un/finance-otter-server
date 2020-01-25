package at.finance_otter.persistence;

import at.finance_otter.persistence.entity.User;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class DatabaseAdapterTest {

    @Inject
    DatabaseAdapter databaseAdapter;

    @Test
    public void shouldCreateUser() {
        User user = new User();
        user.setUsername("user1");
        user.setFirstName("Anna");
        user.setLastName("Adler");
        databaseAdapter.createUser(user);

        Assertions.assertNotNull(user.getUserId());
    }

    // @Test
    public void shouldFailIfUsernameIsTaken() {
        User user1 = new User();
        user1.setUsername("user1");
        User user1_2 = new User();
        user1_2.setUsername("user1");
        databaseAdapter.createUser(user1);

        Assertions.assertThrows(PersistenceException.class, () -> databaseAdapter.createUser(user1_2));
    }



}
