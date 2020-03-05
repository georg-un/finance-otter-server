package at.accounting_otter.rest;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
// TODO: switch to @SuperBuilder as soon as there is IDE support
public class PaymentToGet extends RestPayment {

    private int userId;
    private int transactionId;
    private String username;
    private String firstName;
    private String lastName;
    private double sumAmount;
    private List<DebitToGet> debits;

    public Date getDate() {
        return super.getDate();
    }

    @Override
    public String getCategory() {
        return super.getCategory();
    }

    @Override
    public String getShop() {
        return super.getShop();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public String getBillId() {
        return super.getBillId();
    }

    @Override
    public void setDate(Date date) {
        super.setDate(date);
    }

    @Override
    public void setCategory(String category) {
        super.setCategory(category);
    }

    @Override
    public void setShop(String shop) {
        super.setShop(shop);
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description);
    }

    @Override
    public void setBillId(String billId) {
        super.setBillId(billId);
    }
}
