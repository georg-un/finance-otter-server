package at.finance_otter.service;

import at.finance_otter.persistence.DatabaseAdapter;
import at.finance_otter.persistence.entity.Debit;
import at.finance_otter.persistence.entity.Purchase;
import at.finance_otter.service.dto.DebitDTO;
import at.finance_otter.service.dto.PurchaseDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PurchaseService {

    @Inject
    private DatabaseAdapter databaseAdapter;

    public PurchaseDTO getPurchaseBySecId(String secId) {
        return PurchaseDTO.fromPurchase(this.databaseAdapter.getPurchase(secId));
    }

    public List<PurchaseDTO> getPurchases(int offset, int limit) {
        return databaseAdapter.getPurchases(offset, limit)
                .stream()
                .map(PurchaseDTO::fromPurchase)
                .collect(Collectors.toList());
    }

    public PurchaseDTO createPurchase(PurchaseDTO purchaseDTO) throws ExposableException {
        if (purchaseDTO.getPurchaseId() == null) {
            throw new ExposableException("PurchaseId must not be null");
        } else if (purchaseDTO.getBuyerId() == null) {
            throw new ExposableException("BuyerId must not be null");
        } else if (purchaseDTO.getDate() == null) {
            throw new ExposableException("Date must not be null");
        } else if (this.databaseAdapter.getUser(purchaseDTO.getBuyerId()) == null) {
            throw new ExposableException("User with id " + purchaseDTO.getBuyerId() + "not found.");
        } else {
            // Create debits
            for (DebitDTO debitDTO : purchaseDTO.getDebits()) {
                this.createDebit(debitDTO);
            }
            // Create purchase
            Purchase purchase = new Purchase();
            purchase.setPurchaseId(purchaseDTO.getPurchaseId());
            purchase.setBuyer(this.databaseAdapter.getUser(purchaseDTO.getBuyerId()));
            purchase.setDate(purchaseDTO.getDate());
            purchase.setCategory(purchaseDTO.getCategory());
            purchase.setShop(purchaseDTO.getShop());
            purchase.setDescription(purchaseDTO.getDescription());
            return PurchaseDTO.fromPurchase(this.databaseAdapter.createPurchase(purchase));
        }
    }

    public PurchaseDTO updatePurchase(PurchaseDTO purchaseDTO) throws ExposableException {
        if (purchaseDTO.getPurchaseId() == null) {
            throw new ExposableException("PurchaseId must not be null");
        } else if (purchaseDTO.getBuyerId() == null) {
            throw new ExposableException("BuyerId must not be null");
        } else if (purchaseDTO.getDate() == null) {
            throw new ExposableException("Date must not be null");
        } else if (this.databaseAdapter.getUser(purchaseDTO.getBuyerId()) == null) {
            throw new ExposableException("User with id " + purchaseDTO.getBuyerId() + "not found.");
        } else {
            // Delete all debits of the purchase
            for (DebitDTO debitDTO : purchaseDTO.getDebits()) {
                this.databaseAdapter.deleteDebit(debitDTO.getDebitId());
            }
            // Recreate all debits
            List<Debit> debits = new ArrayList<>();
            for (DebitDTO debitDTO : purchaseDTO.getDebits()) {
                debits.add(this.createDebit(debitDTO));
            }
            // Update purchase
            Purchase purchase = this.databaseAdapter.getPurchase(purchaseDTO.getPurchaseId());
            purchase.setBuyer(this.databaseAdapter.getUser(purchaseDTO.getBuyerId()));
            purchase.setDate(purchaseDTO.getDate());
            purchase.setCategory(purchaseDTO.getCategory());
            purchase.setShop(purchaseDTO.getShop());
            purchase.setDescription(purchaseDTO.getDescription());
            purchase.setDebits(debits);
            return PurchaseDTO.fromPurchase(this.databaseAdapter.updatePurchase(purchase));
        }
    }

    private Debit createDebit(DebitDTO debitDTO) throws ExposableException {
        if (debitDTO.getDebitId() == null) {
            throw new ExposableException("DebitId must not be null.");
        } else if (debitDTO.getDebtorId() == null) {
            throw new ExposableException("DebtorId must not be null.");
        } else if (debitDTO.getAmount() == null) {
            throw new ExposableException("Amount must not be null.");
        } else if (this.databaseAdapter.getDebit(debitDTO.getDebitId()) != null) {
            throw new ExposableException("Debit with DebitId " + debitDTO.getDebitId() + " already exists.");
        } else if (this.databaseAdapter.getUser(debitDTO.getDebtorId()) == null) {
            throw new ExposableException("User with id " + debitDTO.getDebtorId() + " not found.");
        } else {
            Debit debit = new Debit();
            debit.setDebitId(debitDTO.getDebitId());
            debit.setDebtor(this.databaseAdapter.getUser(debitDTO.getDebtorId()));
            debit.setAmount(debitDTO.getAmount());
            return this.databaseAdapter.createDebit(debit);
        }
    }

    private void deleteDebit(String debitId) throws ExposableException {
        if (debitId == null) {
            throw new ExposableException("DebitId must not be null.");
        } else {
            this.databaseAdapter.deleteDebit(debitId);
        }
    }

}
