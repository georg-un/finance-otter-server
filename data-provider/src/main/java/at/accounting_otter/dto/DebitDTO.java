package at.accounting_otter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DebitDTO {

    private int debitId;
    private int transactionId;
    private int payerId;
    private int debtorId;
    private double amount;
}
