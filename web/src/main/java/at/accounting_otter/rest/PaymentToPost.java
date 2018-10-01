package at.accounting_otter.rest;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
// TODO: switch to @SuperBuilder as soon as there is IDE support
public class PaymentToPost extends RestPayment {

    private List<DebitToPost> debits;

    @Override
    public int getUserId() {
        return super.getUserId();
    }

    @Override
    public Date getDatetime() {
        return super.getDatetime();
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
    public void setDatetime(Date datetime) {
        super.setDatetime(datetime);
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
