package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.rest.RestObjectMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/payments")
@RequestScoped
public class PaymentEndpoint {

    @Inject
    private PaymentService paymentService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private RestObjectMapper restMapper;

    @GET
    @Path("/{transaction_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayment(@PathParam("transaction_id") int transactionId,
                               @DefaultValue("true") @QueryParam("includeDebits") boolean includeDebits) {

        if (transactionService.getTransaction(transactionId) != null) {
            return Response
                    .status(Response.Status.OK)
                    .entity(restMapper.toRestPayment(paymentService.getPayment(transactionId), includeDebits))
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
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
