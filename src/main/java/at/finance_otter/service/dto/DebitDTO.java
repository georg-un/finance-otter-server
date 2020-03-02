package at.finance_otter.service.dto;

import at.finance_otter.persistence.entity.Debit;
import lombok.Data;

import java.io.Serializable;

@Data
public class DebitDTO implements Serializable {

    private Long genId;
    private String debitId;
    private String debtorId;
    private Double amount;

    public static DebitDTO fromDebit(Debit debit) {
        if (debit != null) {
            DebitDTO dto = new DebitDTO();
            dto.setGenId(debit.getGenId());
            dto.setDebitId(debit.getDebitId());
            dto.setDebtorId(debit.getDebtor().getUserId());
            dto.setAmount(debit.getAmount());
            return dto;
        } else {
            return null;
        }

    }

}
