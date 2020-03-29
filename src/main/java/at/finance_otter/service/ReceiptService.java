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
        if (image == null) {
            throw new ExposableException("No image was transmitted to the server.");
        } else if (image.length > 20971520) {
            throw new ExposableException("Image must be smaller than 20 MB.");
        }
        databaseAdapter.deleteReceipt(purchaseId);
        Receipt receipt = new Receipt();
        receipt.setPurchase(databaseAdapter.getPurchase(purchaseId));  // TODO: what to do if purchase does not exist yet?
        receipt.setImage(image);
        return databaseAdapter.createReceipt(receipt);
    }

    public Receipt getReceipt(String purchaseId) {
        return databaseAdapter.getReceipt(purchaseId);
    }

}
