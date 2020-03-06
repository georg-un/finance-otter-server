package at.finance_otter.service;

import at.finance_otter.persistence.DatabaseAdapter;
import at.finance_otter.persistence.entity.Debit;
import at.finance_otter.persistence.entity.Purchase;
import at.finance_otter.service.dto.DebitDTO;
import at.finance_otter.service.dto.PurchaseDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
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
            throw new ExposableException("Purchase ID must not be null.");
        } else if (purchaseDTO.getBuyerId() == null) {
            throw new ExposableException("Buyer ID must not be null.");
        } else if (purchaseDTO.getDate() == null) {
            throw new ExposableException("Date must not be null.");
        } else if (this.databaseAdapter.getUser(purchaseDTO.getBuyerId()) == null) {
            throw new ExposableException("Could not find any user this ID.");
        } else {
            // Create debits
            List<Debit> debits = new ArrayList<>();
            for (DebitDTO debitDTO : purchaseDTO.getDebits()) {
                debits.add(this.createDebit(debitDTO));
            }
            // Create purchase
            Purchase purchase = new Purchase();
            purchase.setPurchaseId(purchaseDTO.getPurchaseId());
            purchase.setBuyer(this.databaseAdapter.getUser(purchaseDTO.getBuyerId()));
            purchase.setDate(new Date(purchaseDTO.getDate()));
            purchase.setCategory(purchaseDTO.getCategory());
            purchase.setShop(purchaseDTO.getShop());
            purchase.setDescription(purchaseDTO.getDescription());
            purchase.setDebits(debits);
            return PurchaseDTO.fromPurchase(this.databaseAdapter.createPurchase(purchase));
        }
    }

    public void deletePurchase(String secId) throws ExposableException {
        if (secId == null) {
            throw new ExposableException("Purchase ID must not be null.");
        } else {
            this.databaseAdapter.deletePurchase(secId);
        }
    }

    public PurchaseDTO updatePurchase(PurchaseDTO purchaseDTO) throws ExposableException {
        if (purchaseDTO.getPurchaseId() == null) {
            throw new ExposableException("Purchase ID must not be null.");
        } else if (purchaseDTO.getBuyerId() == null) {
            throw new ExposableException("Buyer ID must not be null.");
        } else if (purchaseDTO.getDate() == null) {
            throw new ExposableException("Date must not be null.");
        } else if (this.databaseAdapter.getUser(purchaseDTO.getBuyerId()) == null) {
            throw new ExposableException("Could not find any user this ID.");
        } else {
            // Build updates of all associated debits
            List<Debit> debits = new ArrayList<>();
            for (DebitDTO debitDTO : purchaseDTO.getDebits()) {
                debits.add(this.buildDebitUpdate(debitDTO));
            }
            // Update purchase
            Purchase purchase = this.databaseAdapter.getPurchase(purchaseDTO.getPurchaseId());
            purchase.setBuyer(this.databaseAdapter.getUser(purchaseDTO.getBuyerId()));
            purchase.setDate(new Date(purchaseDTO.getDate()));
            purchase.setCategory(purchaseDTO.getCategory());
            purchase.setShop(purchaseDTO.getShop());
            purchase.setDescription(purchaseDTO.getDescription());
            purchase.getDebits().clear();
            purchase.getDebits().addAll(debits);
            return PurchaseDTO.fromPurchase(this.databaseAdapter.updatePurchase(purchase));
        }
    }

    private Debit createDebit(DebitDTO debitDTO) throws ExposableException {
        if (debitDTO.getDebitId() == null) {
            throw new ExposableException("Debit ID must not be null.");
        } else if (debitDTO.getDebtorId() == null) {
            throw new ExposableException("Debtor ID must not be null.");
        } else if (debitDTO.getAmount() == null) {
            throw new ExposableException("Amount must not be null.");
        } else if (this.databaseAdapter.getDebit(debitDTO.getDebitId()) != null) {
            throw new ExposableException("This Debit already exists.");
        } else if (this.databaseAdapter.getUser(debitDTO.getDebtorId()) == null) {
            throw new ExposableException("Could not find any user this ID.");
        } else {
            Debit debit = new Debit();
            debit.setDebitId(debitDTO.getDebitId());
            debit.setDebtor(this.databaseAdapter.getUser(debitDTO.getDebtorId()));
            debit.setAmount(debitDTO.getAmount());
            return this.databaseAdapter.createDebit(debit);
        }
    }

    private Debit buildDebitUpdate(DebitDTO debitDTO) {
        Debit debit = this.databaseAdapter.getDebit(debitDTO.getDebitId());
        debit.setDebtor(this.databaseAdapter.getUser(debitDTO.getDebtorId()));
        debit.setAmount(debitDTO.getAmount());
        return debit;
    }

}
