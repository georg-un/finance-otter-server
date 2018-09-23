package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import javassist.NotFoundException;

public interface PaymentService {

    Payment createPayment(Payment payment);

    Payment getPayment(int transactionId);

    Payment updatePayment(Payment payment) throws NotFoundException;

    void deletePayment(int transactionId) throws NotFoundException;

}
