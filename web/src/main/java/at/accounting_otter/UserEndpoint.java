package at.accounting_otter;

import at.accounting_otter.entity.User;
import at.accounting_otter.rest.RestObjectMapper;
import at.accounting_otter.rest.UserToGet;
import org.apache.commons.io.IOUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("/api/v1/user")
@RequestScoped
public class UserEndpoint {

    @Inject
    private UserService userService;

    @Inject
    private RestObjectMapper restMapper;

    @GET
    @Path("/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("user_id") int userId) {
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(restMapper.internalToGetUser(userService.getUser(userId)))
                    .build();
        } catch (ObjectNotFoundException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserToGet> getAllUser() throws ObjectNotFoundException {
        return restMapper.listInternalToListGetUser(
                userService.getAllUser()
        );
    }

    @POST
    @Consumes("*/*")
    @Path("/{userId}/pic")
    public Response setUserPic(@PathParam("userId") int userId,
                               InputStream inputStream) throws IOException {
        userService.setUserPic(userId, IOUtils.toByteArray(inputStream));

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Produces("image/png")
    @Path("/{userId}/pic")
    public Response getUserPic(@PathParam("userId") int userId) {
        byte[] userPic = userService.getUserPic(userId);

        if (userPic == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(userPic)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User user) {
        return userService.createUser(user);
    }


}
