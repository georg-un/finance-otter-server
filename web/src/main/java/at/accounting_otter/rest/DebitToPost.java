package at.accounting_otter.rest;

import lombok.Data;

import java.io.Serializable;

@Data
// TODO: switch to @SuperBuilder as soon as there is IDE suppport
public class DebitToPost implements Serializable {

    private int debitId;
    private int payerId;
    private int debtorId;
    private double amount;

}
