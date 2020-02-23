package at.finance_otter.service.dto;

import at.finance_otter.persistence.entity.Purchase;
import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PurchaseDTO {

    private Long purchaseId;
    private String secPurchaseId;
    private Long buyerId;
    private Set<DebitDTO> debits;
    private Date date;
    private String category;
    private String shop;
    private String description;
    private String billId;

    public static PurchaseDTO fromPurchase(Purchase purchase) {
        PurchaseDTO dto = new PurchaseDTO();
        dto.setPurchaseId(purchase.getPurchaseId());
        dto.setSecPurchaseId(purchase.getSecPurchaseId());
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
    }

}
