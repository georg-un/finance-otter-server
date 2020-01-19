package at.accounting_otter.rest;

import lombok.Data;

@Data
public class DebitToGet {

    private int debitId;
    private int debtorId;
    private String debtorName;
    private String debtorFirstName;
    private String debtorLastName;
    private double amount;

}
