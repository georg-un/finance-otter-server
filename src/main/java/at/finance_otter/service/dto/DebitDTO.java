package at.finance_otter.service.dto;

import at.finance_otter.persistence.entity.Debit;
import lombok.Data;

@Data
public class DebitDTO {

    private String debitId;
    private Long debtorId;
    private Double amount;

    static DebitDTO fromDebit(Debit debit) {
        DebitDTO dto = new DebitDTO();
        dto.setDebitId(debit.getDebitId());
        dto.setDebtorId(debit.getDebtor().getUserId());
        dto.setAmount(debit.getAmount());
        return dto;
    }

}
