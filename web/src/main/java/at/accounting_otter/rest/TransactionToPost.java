package at.accounting_otter.rest;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
// TODO: switch to @SuperBuilder as soon as there is IDE suppport
public class TransactionToPost implements Serializable {

    private int transactionId;
    private int userId;
    private Date datetime;
    private String category;
    private String shop;
    private String description;
    private String billId;
    
}
