package at.finance_otter.service.dto;

import at.finance_otter.persistence.entity.Purchase;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PurchaseDTO implements Serializable {

    private String purchaseId;
    private String buyerId;
    private Set<DebitDTO> debits;
    private Long date;
    private String category;
    private String shop;
    private String description;
    private Boolean isCompensation;

    public static PurchaseDTO fromPurchase(Purchase purchase) {
        if (purchase != null) {
            PurchaseDTO dto = new PurchaseDTO();
            dto.setPurchaseId(purchase.getPurchaseId());
            dto.setBuyerId(purchase.getBuyer().getUserId());
            dto.setDebits(
                    purchase.getDebits()
                            .stream()
                            .map(DebitDTO::fromDebit)
                            .collect(Collectors.toSet())
            );
            dto.setDate(purchase.getDate().getTime());
            dto.setCategory(purchase.getCategory());
            dto.setShop(purchase.getShop());
            dto.setDescription(purchase.getDescription());
            dto.setIsCompensation(purchase.getIsCompensation());
            return dto;
        } else {
            return null;
        }
    }

}
