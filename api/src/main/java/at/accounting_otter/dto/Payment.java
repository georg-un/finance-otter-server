package at.accounting_otter.dto;

import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.Transaction;
import lombok.Data;
import java.util.List;

@Data
public class Payment {

    private Transaction transaction;
    private List<Debit> debits;

    public Payment() {}

    public Payment(Transaction transaction, List<Debit> debits) {
        this.transaction = transaction;
        this.debits = debits;
    }

}
