package at.accounting_otter.rest;

import lombok.Data;

@Data
public class DebitToPost {

    private int debtorId;
    private double amount;

}
