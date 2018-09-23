package at.accounting_otter;

import at.accounting_otter.dto.Payment;

public interface PaymentService {

    Payment createPayment(Payment payment) throws ObjectNotFoundException;

    Payment getPayment(int transactionId);

    Payment updatePayment(Payment payment) throws ObjectNotFoundException;

    void deletePayment(int transactionId) throws ObjectNotFoundException;

}
