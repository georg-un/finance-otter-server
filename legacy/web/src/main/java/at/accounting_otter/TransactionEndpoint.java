package at.accounting_otter;

import at.accounting_otter.rest.RestObjectMapper;
import at.accounting_otter.rest.TransactionToGet;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


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
    public List<TransactionToGet> getAllTransactions(@DefaultValue("0") @QueryParam("start") int startIndex,
                                                     @DefaultValue("10") @QueryParam("end") int endIndex) {
        return restMapper.listInternalToListGetTransaction(
                transactionService.getTransactions(startIndex, endIndex));
    }

}
