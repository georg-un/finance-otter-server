package at.finance_otter.web;

import at.finance_otter.service.ExposableException;
import at.finance_otter.service.UserService;
import at.finance_otter.service.dto.UserDTO;
import io.quarkus.security.Authenticated;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/fino/users")
@Authenticated
@RequestScoped
@Transactional(rollbackOn = ExposableException.class)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getActiveUsers() {
        return userService.getActiveUsers();
    }

    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean isCurrentUserActive(@Context SecurityContext securityContext) {
        return userService.isCurrentUserActive(securityContext.getUserPrincipal().getName());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO createNewUser(UserDTO user, @Context SecurityContext securityContext) throws ExposableException {
        return userService.createUser(user, securityContext.getUserPrincipal().getName());
    }

}


