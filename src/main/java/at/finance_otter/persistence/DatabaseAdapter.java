package at.finance_otter.persistence;

import at.finance_otter.persistence.entity.Purchase;
import at.finance_otter.persistence.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DatabaseAdapter {

    @Inject
    EntityManager em;


    // User methods

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
        List<User> user =  em.createQuery(
                "SELECT user FROM User user WHERE user.username = :username"
                , User.class)
                .setParameter("username", username).getResultList();
        return user.isEmpty() ? null : user.get(0);
    }

    @Transactional
    public User updateUser(User user) {
        return em.merge(user);
    }


    // Purchase methods

    @Transactional
    public Purchase createPurchase(Purchase purchase) {
        em.persist(purchase);
        return purchase;
    }

    @Transactional
    public Purchase getPurchase(String purchaseId) {
        return em.find(Purchase.class, purchaseId);
    }

    @Transactional
    public List<Purchase> getPurchases(int offset, int limit) {
        return em.createQuery(
                "SELECT purchase FROM Purchase purchase ORDER BY purchase.date DESC",
                Purchase.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Transactional
    public Purchase updatePurchase(Purchase purchase) {
        return em.merge(purchase);
    }

    @Transactional
    public void deletePurchase(String purchaseId) {
        Purchase purchase = em.find(Purchase.class, purchaseId);
        em.remove(purchase);
    }


    // Other

    /*@Transactional
    public Map<User, Double> getBalances() {
        // TODO
    }*/




}
