package at.accounting_otter.rest;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TransactionToGet implements Serializable {

    private int transactionId;
    private int userId;
    private Date date;
    private String category;
    private String shop;
    private String description;
    private String billId;
    private String username;
    private String firstName;
    private String lastName;
    private double sumAmount;

}
