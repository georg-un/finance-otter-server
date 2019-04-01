package at.accounting_otter.dto;

import lombok.Data;

import java.util.List;

@Data
public class Payment {

    private TransactionDTO transaction;
    private List<DebitDTO> debits;

    public Payment() {}

    public Payment(TransactionDTO transaction, List<DebitDTO> debits) {
        this.transaction = transaction;
        this.debits = debits;
    }

}
