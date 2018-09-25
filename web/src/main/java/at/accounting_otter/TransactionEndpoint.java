package at.accounting_otter;

import at.accounting_otter.entity.Transaction;
import at.accounting_otter.rest.RestObjectMapper;
import at.accounting_otter.rest.TransactionToGet;
import at.accounting_otter.rest.TransactionToPost;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/api/v1/transaction")
@RequestScoped
public class TransactionEndpoint {

    @Inject
    private TransactionService transactionService;

    private RestObjectMapper restObjectMapper = new RestObjectMapper();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionToGet createTransaction(TransactionToPost transactionToPost) throws ObjectNotFoundException {
        return restObjectMapper.toRestTransaction(
                transactionService.createTransaction(
                        restObjectMapper.toInternalTransaction(transactionToPost)
                ));
    }

    @GET
    @Path("/{transaction_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionToGet getTransaction(@PathParam("transaction_id") int transactionId) {
        return restObjectMapper.toRestTransaction(
                transactionService.getTransaction(transactionId)
        );
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionToGet updateTransaction(TransactionToPost transactionToPost) throws ObjectNotFoundException {

        return restObjectMapper.toRestTransaction(
                transactionService.updateTransaction(
                        restObjectMapper.toInternalTransaction(transactionToPost)
                ));
    }

    @DELETE
    public void deleteTransaction(int transactionId) throws ObjectNotFoundException {
        transactionService.deleteTransaction(transactionId);
    }

}
