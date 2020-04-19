package at.finance_otter.service;

import at.finance_otter.persistence.DatabaseAdapter;
import at.finance_otter.persistence.entity.Receipt;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ReceiptService {

    @Inject
    DatabaseAdapter databaseAdapter;

    public Receipt createReceipt(byte[] image, String purchaseId) throws ExposableException {
        validateInput(image, purchaseId);
        databaseAdapter.deleteReceipt(purchaseId);
        Receipt receipt = new Receipt();
        receipt.setPurchase(databaseAdapter.getPurchase(purchaseId));
        receipt.setImage(image);
        return databaseAdapter.createReceipt(receipt);
    }

    public Receipt getReceipt(String purchaseId) {
        return databaseAdapter.getReceipt(purchaseId);
    }

    public Receipt updateReceipt(byte[] image, String purchaseId) throws ExposableException {
        validateInput(image, purchaseId);
        deleteReceipt(purchaseId);
        return createReceipt(image, purchaseId);
    }

    public void deleteReceipt(String purchaseId) throws ExposableException {
        if (purchaseId == null) {
            throw new ExposableException("PurchaseId must not be null.");
        }
        databaseAdapter.deleteReceipt(purchaseId);
    }

    private void validateInput(byte[] image, String purchaseId) throws ExposableException {
        if (image == null) {
            throw new ExposableException("No image was transmitted to the server.");
        }
        if (image.length > 20971520) {
            throw new ExposableException("Image must be smaller than 20 MB.");
        }
        if (databaseAdapter.getPurchase(purchaseId) == null) {
            throw new ExposableException("Could not find associated purchase");
        }
    }

}
