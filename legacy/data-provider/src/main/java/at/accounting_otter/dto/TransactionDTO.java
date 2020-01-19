package at.accounting_otter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TransactionDTO {

    private int transactionId;
    private int userId;
    private Date date;
    private String category;
    private String shop;
    private String description;
    private String billId;
}
