package at.accounting_otter;


import at.accounting_otter.entity.Debit;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/debit")
@RequestScoped
public class DebitEndpoint {

    @Inject
    private DebitService debitService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Debit createDebit(Debit debit) throws ObjectNotFoundException {
        return debitService.createDebit(debit);
    }

    @GET
    @Path("/{debit_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Debit getDebit(
            @PathParam("debit_id") int debitId) {
        return debitService.getDebit(debitId);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Debit updateDebit(Debit debit) throws ObjectNotFoundException {
        return debitService.updateDebit(debit);
    }

    @DELETE
    @Path("/{debit_id}")
    public void deleteDebit(
            @PathParam("debit_id") int debitId) throws ObjectNotFoundException {
        debitService.deleteDebit(debitId);
    }

}
