package at.accounting_otter.rest;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PaymentToGet {

    private int transactionId;
    private int userId;
    private String username;
    private Date datetime;
    private String category;
    private String shop;
    private String description;
    private String billId;
    private double sumAmount;
    private List<DebitToGet> debits;

}
