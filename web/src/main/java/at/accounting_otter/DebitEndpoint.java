/*package at.accounting_otter;


import at.accounting_otter.entity.Debit;
import at.accounting_otter.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/debit")
@RequestScoped
public class DebitEndpoint {

    @Inject
    private OtterService otterService;

    @GET
    @Path("/{debit_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Debit getDebit(
            @PathParam("debit_id") int debitId) {
        return otterService.get(debitId);
    }
}
*/