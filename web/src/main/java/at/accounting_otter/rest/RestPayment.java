package at.accounting_otter.rest;

import lombok.Data;

import java.util.Date;

@Data
public abstract class RestPayment {

    private Date date;
    private String category;
    private String shop;
    private String description;
    private String billId;

}
