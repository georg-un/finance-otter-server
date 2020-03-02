package at.finance_otter.service.dto;

import at.finance_otter.persistence.entity.Purchase;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PurchaseDTO implements Serializable {

    private Long genId;
    private String purchaseId;
    private String buyerId;
    private Set<DebitDTO> debits;
    private Date date;
    private String category;
    private String shop;
    private String description;
    private String billId;

    public static PurchaseDTO fromPurchase(Purchase purchase) {
        if (purchase != null) {
            PurchaseDTO dto = new PurchaseDTO();
            dto.setGenId(purchase.getGenId());
            dto.setPurchaseId(purchase.getPurchaseId());
            dto.setBuyerId(purchase.getBuyer().getUserId());
            dto.setDebits(
                    purchase.getDebits()
                            .stream()
                            .map(DebitDTO::fromDebit)
                            .collect(Collectors.toSet())
            );
            dto.setDate(purchase.getDate());
            dto.setCategory(purchase.getCategory());
            dto.setShop(purchase.getShop());
            dto.setDescription(purchase.getDescription());
            dto.setBillId(purchase.getBillId());
            return dto;
        } else {
            return null;
        }
    }

}
