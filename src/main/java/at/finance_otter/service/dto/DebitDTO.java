package at.finance_otter.service.dto;

import at.finance_otter.persistence.entity.Debit;
import lombok.Data;

@Data
public class DebitDTO {

    private Long debitId;
    private String secDebitId;
    private Long debtorId;
    private Double amount;

    public static DebitDTO fromDebit(Debit debit) {
        DebitDTO dto = new DebitDTO();
        dto.setDebitId(debit.getDebitId());
        dto.setSecDebitId(debit.getSecDebitId());
        dto.setDebtorId(debit.getDebtor().getUserId());
        dto.setAmount(debit.getAmount());
        return dto;
    }

}
