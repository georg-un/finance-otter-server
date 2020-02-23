package at.finance_otter.persistence;

import at.finance_otter.persistence.entity.Debit;
import at.finance_otter.persistence.entity.Purchase;
import at.finance_otter.persistence.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DatabaseAdapter {

    @Inject
    EntityManager em;


    // User methods

    public User createUser(User user) {
        em.persist( user );
        return user;
    }

    public User getUser(Long userId) {
        return em.find(User.class, userId);
    }

    public User getUserByUsername(String username) {
        List<User> user =  em.createQuery(
                "SELECT user FROM User user WHERE user.username = :username"
                , User.class)
                .setParameter("username", username).getResultList();
        return user.isEmpty() ? null : user.get(0);
    }

    public User updateUser(User user) {
        return em.merge(user);
    }


    // Purchase methods

    public Purchase createPurchase(Purchase purchase) {
        em.persist(purchase);
        return purchase;
    }

    public Purchase getPurchase(Long purchaseId) {
        return em.find(Purchase.class, purchaseId);
    }

    public Purchase getPurchaseBySecId(String secPurchaseId) {
        return em.createQuery(
                "SELECT purchase FROM Purchase purchase WHERE purchase.secPurchaseId = :secId", Purchase.class)
                .setParameter("secId", secPurchaseId)
                .getSingleResult();
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
        return em.merge(purchase);
    }

    public void deletePurchase(String purchaseId) {
        Purchase purchase = em.find(Purchase.class, purchaseId);
        em.remove(purchase);
    }


    // Debit metods

    public Debit createDebit(Debit debit) {
        em.persist(debit);
        return debit;
    }

    public Debit getDebit(Long debitId) {
        return em.find(Debit.class, debitId);
    }

    public Debit getDebitBySecId(String secDebitId) {
        return em.createQuery(
                "SELECT debit FROM Debit debit WHERE debit.secDebitId = :secId", Debit.class)
                .setParameter("secId", secDebitId)
                .getSingleResult();
    }

    public void deleteDebit(Long debitId) {
        Debit debit = this.getDebit(debitId);
        em.remove(debit);
    }


    // Other

    /*@Transactional
    public Map<User, Double> getBalances() {
        // TODO
    }*/




}
