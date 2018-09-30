package at.accounting_otter.rest;

import lombok.Data;

@Data
public class DebitToGet {

    private int debtorId;
    private String debtorName;
    private double amount;

}
