package at.accounting_otter.rest;

import lombok.Data;

import java.io.Serializable;

@Data
// TODO: switch to @SuperBuilder as soon as there is IDE suppport
public class DebitToGet extends DebitToPost implements Serializable {

    private int transactionId;
    private String payerName;
    private String debtorName;

}
