package at.finance_otter.service;

import at.finance_otter.persistence.DatabaseAdapter;
import at.finance_otter.persistence.entity.Debit;
import at.finance_otter.persistence.entity.Purchase;
import at.finance_otter.service.dto.DebitDTO;
import at.finance_otter.service.dto.PurchaseDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PurchaseService {

    @Inject
    DatabaseAdapter databaseAdapter;

    public PurchaseDTO getPurchase(String secId) {
        return PurchaseDTO.fromPurchase(this.databaseAdapter.getPurchase(secId));
    }

    public List<PurchaseDTO> getPurchases(int offset, int limit) {
        return databaseAdapter.getPurchases(offset, limit)
                .stream()
                .map(PurchaseDTO::fromPurchase)
                .collect(Collectors.toList());
    }

    public PurchaseDTO createPurchase(PurchaseDTO purchaseDTO) throws ExposableException {
        this.validatePurchase(purchaseDTO);
        // Create purchase
        Purchase purchase = new Purchase();
        purchase.setPurchaseId(purchaseDTO.getPurchaseId());
        this.setPurchaseProperties(purchase, purchaseDTO);
        return PurchaseDTO.fromPurchase(this.databaseAdapter.createPurchase(purchase));
    }

    public void deletePurchase(String purchaseId) throws ExposableException {
        if (purchaseId == null) {
            throw new ExposableException("Purchase ID must not be null.");
        } else {
            this.databaseAdapter.deleteReceipt(purchaseId);
            this.databaseAdapter.deletePurchase(purchaseId);
        }
    }

    public PurchaseDTO updatePurchase(PurchaseDTO purchaseDTO) throws ExposableException {
        this.validatePurchase(purchaseDTO);
        // Remove old debits
        Purchase purchase = this.databaseAdapter.getPurchase(purchaseDTO.getPurchaseId());
        purchase.removeAllDebits();
        purchase = this.databaseAdapter.updatePurchase(purchase);
        // Update purchase
        this.setPurchaseProperties(purchase, purchaseDTO);
        return PurchaseDTO.fromPurchase(this.databaseAdapter.updatePurchase(purchase));
    }

    private Debit generateDebit(DebitDTO debitDTO) throws ExposableException {
        if (debitDTO.getDebitId() == null) {
            throw new ExposableException("Debit ID must not be null.");
        } else if (debitDTO.getDebtorId() == null) {
            throw new ExposableException("Debtor ID must not be null.");
        } else if (debitDTO.getAmount() == null) {
            throw new ExposableException("Amount must not be null.");
        } else if (this.databaseAdapter.getUser(debitDTO.getDebtorId()) == null) {
            throw new ExposableException("Could not find any user this ID.");
        } else {
            Debit debit = new Debit();
            debit.setDebitId(debitDTO.getDebitId());
            debit.setDebtor(this.databaseAdapter.getUser(debitDTO.getDebtorId()));
            debit.setAmount(debitDTO.getAmount());
            return debit;
        }
    }

    private void validatePurchase(PurchaseDTO purchaseDTO) throws ExposableException {
        if (purchaseDTO == null) {
            throw new ExposableException("Did not receive any purchase.");
        } else if (purchaseDTO.getPurchaseId() == null) {
            throw new ExposableException("Purchase ID must not be null.");
        } else if (purchaseDTO.getBuyerId() == null) {
            throw new ExposableException("Buyer ID was null.");
        } else if (purchaseDTO.getDate() == null) {
            throw new ExposableException("Date was null.");
        } else if (this.databaseAdapter.getUser(purchaseDTO.getBuyerId()) == null) {
            throw new ExposableException("Could not find any user with ID: " + purchaseDTO.getBuyerId());
        } else if (
                purchaseDTO.getCategoryId() != null &&
                this.databaseAdapter.getCategory(purchaseDTO.getCategoryId()) == null
        ) {
            throw new ExposableException("Could not find any category with ID: " + purchaseDTO.getCategoryId().toString());
        } else if (purchaseDTO.getDebits().size() < 1) {
            throw new ExposableException("Purchase did not contain any debits.");
        } else if (
                purchaseDTO.getIsCompensation() != null &&
                purchaseDTO.getIsCompensation() &&
                purchaseDTO.getDebits().size() > 1
        ) {
            throw new ExposableException("Compensation had multiple debits.");
        }
    }

    private void setPurchaseProperties(Purchase purchase, PurchaseDTO purchaseDTO) throws ExposableException {
        purchase.setBuyer(this.databaseAdapter.getUser(purchaseDTO.getBuyerId()));
        purchase.setDate(new Date(purchaseDTO.getDate()));
        purchase.setShop(purchaseDTO.getShop());
        purchase.setDescription(purchaseDTO.getDescription());
        for (DebitDTO debitDTO : purchaseDTO.getDebits()) {
            purchase.addDebit(this.generateDebit(debitDTO));
        }
        purchase.setCategory(purchaseDTO.getCategoryId() == null
                ? null
                : this.databaseAdapter.getCategory(purchaseDTO.getCategoryId())
        );
    }

}
