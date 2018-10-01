package at.accounting_otter;

import at.accounting_otter.rest.PaymentToGet;
import at.accounting_otter.rest.PaymentToPost;
import at.accounting_otter.rest.PaymentToPut;
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
                    .entity(restMapper.internalToRestPayment(paymentService.getPayment(transactionId), includeDebits))
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
    public PaymentToGet createPayment(PaymentToPost paymentToPost) throws ObjectNotFoundException {
        return restMapper.internalToRestPayment(
                paymentService.createPayment(
                restMapper.postToInternalPayment(paymentToPost)
        ), true);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PaymentToGet updatePayment(PaymentToPut paymentToPut) throws ObjectNotFoundException {
        return restMapper.internalToRestPayment(
                paymentService.updatePayment(
                restMapper.putToInternalPayment(paymentToPut)
        ), true);
    }

}
