package at.accounting_otter;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    public Response toResponse(IllegalArgumentException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }

}
