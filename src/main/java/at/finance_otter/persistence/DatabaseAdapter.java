package at.finance_otter.persistence;

import at.finance_otter.persistence.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class DatabaseAdapter {

    @Inject
    EntityManager em;

    @Transactional
    public User createUser(User user) {
        em.persist( user );
        return user;
    }

    @Transactional
    public User getUser(Long userId) {
        return em.find(User.class, userId);
    }

    @Transactional
    public User getUserByUsername(String username) {
        List<User> user =  em.createQuery("SELECT user FROM User user WHERE user.username = :username", User.class)
                .setParameter("username", username).getResultList();
        return user.isEmpty() ? null : user.get(0);
    }

    @Transactional
    public User updateUser(User user) {
        return em.merge(user);
    }

}
