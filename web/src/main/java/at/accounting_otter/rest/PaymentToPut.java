package at.accounting_otter.rest;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PaymentToPut extends PaymentToPost {

    private int transactionId;


    @Override
    public List<DebitToPost> getDebits() {
        return super.getDebits();
    }

    @Override
    public void setDebits(List<DebitToPost> debits) {
        super.setDebits(debits);
    }

    @Override
    public int getUserId() {
        return super.getUserId();
    }

    @Override
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
    public void setUserId(int userId) {
        super.setUserId(userId);
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
