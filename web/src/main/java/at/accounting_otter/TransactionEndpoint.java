package at.accounting_otter;

import at.accounting_otter.rest.RestObjectMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/api/v1/transactions")
@RequestScoped
public class TransactionEndpoint {

    @Inject
    private TransactionService transactionService;

    @Inject
    private RestObjectMapper restMapper;


    @GET
    @Path("/{transaction_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransaction(@PathParam("transaction_id") int transactionId) {
        if (transactionService.getTransaction(transactionId) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(restMapper.internalToGetTransaction(transactionService.getTransaction(transactionId)))
                    .build();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTransactions(@QueryParam("start") int startIndex,
                                       @QueryParam("end") int endIndex) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(transactionService.getTransactions(startIndex, endIndex))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

}
