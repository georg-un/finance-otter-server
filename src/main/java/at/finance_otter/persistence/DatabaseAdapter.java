package at.finance_otter.persistence;

import at.finance_otter.persistence.entity.Category;
import at.finance_otter.persistence.entity.Purchase;
import at.finance_otter.persistence.entity.Receipt;
import at.finance_otter.persistence.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Date;
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


    // Category methods

    public List<Category> getCategories() {
        return em.createQuery("SELECT category FROM Category category", Category.class)
                .getResultList();
    }

    public Category getCategory(Long categoryId) {
        List<Category> categories = em.createQuery(
                "SELECT category FROM Category category WHERE category.genId = :categoryId", Category.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
        return categories.isEmpty() ? null : categories.get(0);
    }


    // Receipt methods

    public Receipt createReceipt(Receipt receipt) {
        em.persist(receipt);
        return receipt;
    }

    public Receipt getReceipt(String purchaseId) {
        List<Receipt> receipts = em.createQuery(
                "SELECT receipt from Receipt receipt WHERE receipt.purchase.purchaseId = :purchaseId", Receipt.class)
                .setParameter("purchaseId", purchaseId)
                .getResultList();
        return receipts.isEmpty() ? null : receipts.get(0);
    }

    public void deleteReceipt(String purchaseId) {
        List<Receipt> receipts = em.createQuery(
                "SELECT receipt from Receipt receipt WHERE receipt.purchase.purchaseId = :purchaseId", Receipt.class)
                .setParameter("purchaseId", purchaseId)
                .getResultList();
        for (Receipt receipt: receipts) {
            em.remove(receipt);
        }
    }


    // Summary methods

    public List<Object[]> getCredits() {
        return em.createQuery(
                "SELECT p.buyer.userId, sum(d.amount) FROM Purchase p JOIN p.debits d GROUP BY p.buyer.userId",
                Object[].class
        ).getResultList();
    }

    public List<Object[]> getLiabilities() {
        return em.createQuery(
                "SELECT debit.debtor.userId, SUM(debit.amount) FROM Debit debit GROUP BY debit.debtor.userId",
                Object[].class
        ).getResultList();
    }

    public List<Object[]> getAmountByCategoryAndDate(LocalDate startDate, LocalDate endDate) {
        return em.createNativeQuery(
                "SELECT EXTRACT(YEAR FROM p.date) AS yr, EXTRACT(MONTH FROM p.date) AS mt, p.category_id, SUM(d.amount) " +
                        "FROM purchases p JOIN debits d ON p.gen_id = d.purchase_gen_id " +
                        "WHERE (p.date BETWEEN :startDate AND :endDate) " +
                        "AND (p.is_compensation IS NOT TRUE) " +
                        "GROUP BY EXTRACT(YEAR FROM p.date), EXTRACT(MONTH FROM p.date), p.category_id " +
                        "ORDER BY yr DESC, mt DESC ")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    public List<Object[]> getAmountByCategory(Date startDate, Date endDate) {
        return em.createNativeQuery(
                "SELECT p.category_id, SUM(d.amount) " +
                        "FROM purchases p JOIN debits d ON p.gen_id = d.purchase_gen_id " +
                        "WHERE (p.date BETWEEN :startDate AND :endDate) " +
                        "AND (p.is_compensation IS NOT TRUE) " +
                        "GROUP BY p.category_id")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

}
