package at.accounting_otter;

import at.accounting_otter.dto.Payment;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/payment")
@RequestScoped
public class PaymentEndpoint {

    @Inject
    private PaymentService paymentService;

    @GET
    @Path("/{transaction_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Payment getPayment(
            @PathParam("transaction_id") int transactionId) {
        return paymentService.getPayment(transactionId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Payment createPayment(Payment payment) throws ObjectNotFoundException {
        return paymentService.createPayment(payment);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Payment updatePayment(Payment payment) throws ObjectNotFoundException {
        return paymentService.updatePayment(payment);
    }

}
