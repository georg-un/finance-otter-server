package at.finance_otter.web;

import at.finance_otter.service.UserService;
import at.finance_otter.service.dto.UserDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getActiveUsers() {
        return this.userService.getActiveUsers();
    }

}


