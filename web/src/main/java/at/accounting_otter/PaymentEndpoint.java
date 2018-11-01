package at.accounting_otter;

import at.accounting_otter.dto.Payment;
import at.accounting_otter.entity.User;
import at.accounting_otter.rest.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/api/v1/payments")
@RequestScoped
public class PaymentEndpoint {

    @Inject
    private PaymentService paymentService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private UserService userService;

    @Inject
    private RestObjectMapper restMapper;

    @Inject
    private SecurityUtil securityUtil;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("/{transactionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayment(@PathParam("transactionId") int transactionId,
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
    public Response createPayment(PaymentToPost paymentToPost) throws ObjectNotFoundException {
        User currentUser = userService.getUser(securityUtil.getCurrentUser(securityContext));

        if (currentUser !=null) {
            Payment payment = restMapper.postToInternalPayment(paymentToPost, currentUser.getUserId());
            payment.getTransaction().setUser(currentUser);
            return Response
                    .status(Response.Status.OK)
                    .entity(restMapper.internalToRestPayment(
                            paymentService.createPayment(payment), true))
                    .build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Current user does not exist.")
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PaymentToGet updatePayment(PaymentToPut paymentToPut) throws ObjectNotFoundException {

        return restMapper.internalToRestPayment(
                paymentService.updatePayment(
                restMapper.putToInternalPayment(paymentToPut, transactionService.getTransaction(paymentToPut.getTransactionId()).getUser().getUserId())
        ), true);

    }

    @DELETE
    @Path("/{transactionId}")
    public Response deletePayment(@PathParam("transactionId") int transactionId) throws ObjectNotFoundException {
        paymentService.deletePayment(transactionId);
        return Response
                .status(Response.Status.OK)
                .build();
    }

}
