package at.finance_otter.persistence;

import at.finance_otter.persistence.entity.Debit;
import at.finance_otter.persistence.entity.Purchase;
import at.finance_otter.persistence.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class DatabaseAdapter {

    @Inject
    EntityManager em;


    // User methods

    public User createUser(User user) {
        em.persist(user);
        return user;
    }

    public User getUser(String userId) {
        List<User> user = em.createQuery(
                "SELECT user FROM User user WHERE user.userId = :userId"
                , User.class)
                .setParameter("userId", userId).getResultList();
        return user.isEmpty() ? null : user.get(0);
    }

    public List<User> getActiveUsers() {
        return em.createQuery("SELECT user FROM User user WHERE user.deactivated IS NULL OR user.deactivated = FALSE", User.class)
                .getResultList();
    }

    public User updateUser(User user) {
        return em.merge(user);
    }


    // Purchase methods

    public Purchase createPurchase(Purchase purchase) {
        em.persist(purchase);
        return purchase;
    }

    public Purchase getPurchase(String purchaseId) {
        List<Purchase> purchases = em.createQuery(
                "SELECT purchase FROM Purchase purchase WHERE purchase.purchaseId = :purchaseId", Purchase.class)
                .setParameter("purchaseId", purchaseId)
                .getResultList();
        return purchases.isEmpty() ? null : purchases.get(0);
    }

    public List<Purchase> getPurchases(int offset, int limit) {
        return em.createQuery(
                "SELECT purchase FROM Purchase purchase ORDER BY purchase.date DESC",
                Purchase.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Purchase updatePurchase(Purchase purchase) {
        Purchase p = em.merge(purchase);
        em.flush();
        return p;
    }

    public void deletePurchase(String purchaseId) {
        Purchase purchase = this.getPurchase(purchaseId);
        if (purchase != null) {
            em.remove(purchase);
        }
    }


    // Debit methods

    public Debit createDebit(Debit debit) {
        em.persist(debit);
        return debit;
    }

    public Debit getDebit(String debitId) {
        List<Debit> debits = em.createQuery(
                "SELECT debit FROM Debit debit WHERE debit.debitId = :debitId", Debit.class)
                .setParameter("debitId", debitId)
                .getResultList();
        return debits.isEmpty() ? null : debits.get(0);
    }

}


// Other

    /*@Transactional
    public Map<User, Double> getBalances() {
        // TODO
    }*/
