package at.accounting_otter;

import at.accounting_otter.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/users")
@RequestScoped
public class UserEndpoint {

    @Inject
    private OtterService otterService;

    @GET
    @Path("/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(
            @PathParam("user_id") int userId) {
        return otterService.getUser(userId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User user) {
        return otterService.createUser(user);
    }


}
